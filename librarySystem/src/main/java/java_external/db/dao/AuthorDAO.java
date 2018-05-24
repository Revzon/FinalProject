package java_external.db.dao;

import java_external.db.connection.ConnectionPool;
import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.FunctionRT;
import java_external.db.dto.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class AuthorDAO extends AbstractDAO implements CRUD<Author> {

    private static final String ADD_AUTHOR_QUERY = "INSERT INTO author(first_name, second_name, patronymic_name) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_AUTHOR_QUERY = "UPDATE author SET first_name = ?, second_name = ?, patronymic_name = ? WHERE id = ?";
    private static final String DELETE_AUTHOR_QUERY = "DELETE FROM author WHERE id = ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM author";

    private static final String FIND_AUTHOR_BY_ID = "SELECT * FROM author WHERE id = ?";
    private static final String FIND_AUTHORS_BY_ID_ARRAY = "SELECT * FROM author WHERE id IN ?";
    private static final String FIND_AUTHOR_BY_BOOK_ID = "SELECT * FROM author RIGHT JOIN book_author ON book_author.author_id = author.id WHERE book_author.book_id = ?";
    private static final String FIND_ALL = "SELECT * FROM author";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM author LIMIT 10 OFFSET ?";

    private final static String AUTHOR_ID = "id";
    private final static String AUTHOR_FIRST_NAME = "first_name";
    private final static String AUTHOR_SECOND_NAME = "second_name";
    private final static String AUTHOR_PATRONIMYC_NAME = "patronymic_name";


    private static AuthorDAO authorDAO;

    public static AuthorDAO getInstance() {
        if (authorDAO == null) {
            authorDAO = new AuthorDAO();
        }
        return authorDAO;
    }

    public int getCount() {
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }


    public void insert(Author author) {
        executeQuery(ADD_AUTHOR_QUERY, preparedStatement -> {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getSecondName());
            preparedStatement.setString(3, author.getPatronymicName());
        });
    }

    public void update(Author author) {
        executeQuery(UPDATE_AUTHOR_QUERY, preparedStatement -> {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getSecondName());
            preparedStatement.setString(3, author.getPatronymicName());
            preparedStatement.setInt(4, author.getId());
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_AUTHOR_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
        });
    }


    public List<Author> findByIdArray(String[] idArray) {
        return executeQuery(
                FIND_AUTHORS_BY_ID_ARRAY,

                preparedStatement -> {
                    Connection connection = ConnectionPool.getInstance().getConnection();
                    preparedStatement.setArray(1, connection.createArrayOf("INT", idArray));
                    ConnectionPool.getInstance().closeConnection(connection);
                },
                getAuthorResultSetHandler());
    }

    public Author findById(int id) {
        return executeQuery(
                FIND_AUTHOR_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                getAuthorHandler());
    }

    public List<Author> findAuthorsByBookId(int bookId) {
        return executeQuery(
                FIND_AUTHOR_BY_BOOK_ID,
                preparedStatement -> preparedStatement.setInt(1, bookId),
                getAuthorResultSetHandler());
    }

    public List<Author> findAll(int offset) {
        return executeQuery(
                FIND_ALL_PAGINATE,
                preparedStatement -> preparedStatement.setInt(1, offset),
                getAuthorResultSetHandler());
    }

    public List<Author> findAll() {
        return executeQuery(FIND_ALL, getAuthorResultSetHandler());
    }


    private Author processRs(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        int id = resultSet.getInt(AUTHOR_ID);
        String firstName = resultSet.getString(AUTHOR_FIRST_NAME);
        String secondName = resultSet.getString(AUTHOR_SECOND_NAME);
        String patronymicName = resultSet.getString(AUTHOR_PATRONIMYC_NAME);

        author.setId(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setPatronymicName(patronymicName);
        return author;
    }

    private FunctionRT<ResultSet, List<Author>> getAuthorResultSetHandler() {
        return (resultSet) -> {
            List<Author> authorList = new ArrayList<>();
            while (resultSet.next()) {
                Author author = processRs(resultSet);
                if (author != null) {
                    authorList.add(author);
                }
            }
            return authorList;
        };
    }

    private FunctionRT<ResultSet, Author> getAuthorHandler() {
        return (resultSet) -> {
            if (resultSet.next()) {
                return processRs(resultSet);
            }
            return null;
        };
    }

}
