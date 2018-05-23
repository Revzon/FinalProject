package java_external.db.dao;

import java_external.db.dto.Author;
import java_external.exceptions.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




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
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getCountFromRS(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }


    public void insert(Author author) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_AUTHOR_QUERY);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getSecondName());
            preparedStatement.setString(3, author.getPatronymicName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at author insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Author author) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getSecondName());
            preparedStatement.setString(3, author.getPatronymicName());
            preparedStatement.setInt(4, author.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at author update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at author delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }


    public List<Author> findByIdArray(String[] idArray) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Author> authorList = new ArrayList<Author>();
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(FIND_AUTHORS_BY_ID_ARRAY);
            preparedStatement.setArray(1, connection.createArrayOf("INT", idArray));
            resultSet = preparedStatement.executeQuery();
            authorList = new ArrayList<Author>();
            while (resultSet.next()) {
                Author author = fillInAuthor(resultSet);
                if (author != null) {
                    authorList.add(author);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return authorList;
    }

    public Author findById(int id) {
        Author author = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                author = fillInAuthor(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return author;
    }

    public List<Author> findAuthorsByBookId(int bookId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Author> authorList = new ArrayList<Author>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_BOOK_ID);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            authorList = new ArrayList<Author>();
            while (resultSet.next()) {
                Author author = fillInAuthor(resultSet);
                if (author != null) {
                    authorList.add(author);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return authorList;
    }

    public List<Author> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List authorList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
            preparedStatement.setInt(1, offset);
            resultSet = preparedStatement.executeQuery();
            authorList = new ArrayList<Author>();
            while (resultSet.next()) {
                Author author = fillInAuthor(resultSet);
                if (author != null) {
                    authorList.add(author);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return authorList;
    }

    public List<Author> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List authorList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            authorList = new ArrayList<Author>();
            while (resultSet.next()) {
                Author author = fillInAuthor(resultSet);
                if (author != null) {
                    authorList.add(author);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return authorList;
    }


    private Author fillInAuthor(ResultSet resultSet) {
        Author author = new Author();

        try {
            int id = resultSet.getInt(AUTHOR_ID);
            String firstName = resultSet.getString(AUTHOR_FIRST_NAME);
            String secondName = resultSet.getString(AUTHOR_SECOND_NAME);
            String patronymicName = resultSet.getString(AUTHOR_PATRONIMYC_NAME);;

            author.setId(id);
            author.setFirstName(firstName);
            author.setSecondName(secondName);
            author.setPatronymicName(patronymicName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }
}
