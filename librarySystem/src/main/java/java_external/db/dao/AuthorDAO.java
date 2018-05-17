package java_external.db.dao;

import java_external.db.dto.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by olga on 13.05.18.
 */
public class AuthorDAO extends AbstractDAO implements CRUD<Author> {

    private static final String ADD_AUTHOR_QUERY = "INSERT INTO author(first_name, second_name, patronimyc_name, birth_date) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_AUTHOR_QUERY = "UPDATE author SET first_name = ? second_name = ? patronimyc_name = ? birth_date = ? WHERE id = ?";
    private static final String DELETE_AUTHOR_QUERY = "DELETE author WHERE id = ?";
    private static final String FIND_AUTHOR_BY_ID = "SELECT * FROM author WHERE id = ?";
    private static final String FIND_AUTHOR_BY_BOOK_ID = "SELECT * FROM author RIGHT JOIN ON book_author book_author.book_id = book.id WHERE book_author.book_id = ?";
    private static final String FIND_ALL = "SELECT * FROM author";

    private final static String AUTHOR_ID = "id";
    private final static String AUTHOR_FIRST_NAME = "first_name";
    private final static String AUTHOR_SECOND_NAME = "second_name";
    private final static String AUTHOR_PATRONIMYC_NAME = "patronimyc_name";
    private final static String AUTHOR_BIRTH_DATE = "birth_date";


    private static AuthorDAO authorDAO;

    public static AuthorDAO getInstance() {
        if (authorDAO == null) {
            authorDAO = new AuthorDAO();
        }
        return authorDAO;
    }


    public void insert(Author author) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_AUTHOR_QUERY);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getSecondName());
            preparedStatement.setString(3, author.getPatronimycName());
            preparedStatement.setDate(4, java.sql.Date.valueOf(String.valueOf(author.getBirthDate())));
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
            preparedStatement.setString(3, author.getPatronimycName());
            preparedStatement.setDate(4, java.sql.Date.valueOf(String.valueOf(author.getBirthDate())));
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

    public List<Author> findAuthorsByAuthorId(int bookId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List authorList = null;
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
//            log.warn("SQLException at author findAuthorsByAuthorId()", e);
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
//            log.warn("SQLException at author findAuthorsByAuthorId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return authorList;
    }


    private Author fillInAuthor(ResultSet resultSet) {
        Author book = new Author();

        try {
            int id = resultSet.getInt(AUTHOR_ID);
            String firstName = resultSet.getString(AUTHOR_FIRST_NAME);
            String secondName = resultSet.getString(AUTHOR_SECOND_NAME);
            String patronimycName = resultSet.getString(AUTHOR_PATRONIMYC_NAME);
            Date birthDate = resultSet.getDate(AUTHOR_BIRTH_DATE);

            book.setId(id);
            book.setFirstName(firstName);
            book.setSecondName(secondName);
            book.setPatronimycName(patronimycName);
            book.setBirthDate(birthDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
