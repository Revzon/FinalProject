package java_external.db.dao;

import java_external.db.dto.*;
import java_external.enums.SearchMode;
import java_external.enums.SearchType;
import java_external.exceptions.DAOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


public class BookDAO extends AbstractDAO {

    private final static Logger logger = Logger.getLogger(BookDAO.class);

    private static final String ADD_BOOK_QUERY = "INSERT INTO book(title, publishment_id, genre_id, shelf_id, avilable, date_of_return, user_id" +
            ") VALUES(?, ?, ?, ?, TRUE, null, null)";
    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ?, publishment_id = ?," +
            " genre_id = ?, avilable = ?, date_of_return = ?, user_id = ?, shelf_id = ? WHERE id = ?";
    private static final String UPDATE_BOOK_NONAVIABLE = "UPDATE book SET avilable = FALSE , user_id = ?, date_of_return =  DATE_ADD(CURDATE(), INTERVAL 1 MONTH) WHERE id = ?";
    private static final String UPDATE_BOOK_AVIABLE = "UPDATE book SET avilable = TRUE , user_id = NULL , date_of_return = NULL WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book WHERE id = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_TAKEN_BOOKS = "SELECT * FROM book WHERE avilable = FALSE ORDER BY book.title";
    private static final String FIND_ALL_BY_AUTHOR_ID = "SELECT * FROM book RIGHT JOIN book_author ON  book_author.book_id = book.id WHERE book_author.author_id = ? ORDER BY book.title";
    private static final String FIND_ALL_BY_KEYWORD_ID = "SELECT * FROM book RIGHT JOIN book_keyword ON book_keyword.book_id = book.id WHERE book_keyword.keyword_id = ? ORDER BY book.title";
    private static final String FIND_ALL_BY_GENRE_ID = "SELECT * FROM book WHERE genre_id = ? ORDER BY book.title";
    private static final String FIND_ALL_BY_SHELF_ID = "SELECT * FROM book WHERE shelf_id = ? ORDER BY book.title";
    private static final String FIND_ALL_BY_PUBLISHMENT_ID = "SELECT * FROM book WHERE publishment_id = ? ORDER BY book.title";
    private static final String FIND_ALL_BY_READER_ID = "SELECT * FROM book WHERE avilable = FALSE AND user_id = ? ORDER BY book.title";

    private static final String FIND_ALL_BY_TITLE_NAMEPART = "SELECT * FROM book WHERE title LIKE ? ORDER BY book.title";
    private static final String FIND_ALL_BY_GENRE_NAMEPART = "SELECT * FROM book\n" +
            "    RIGHT JOIN genre\n" +
            "        ON genre.id = book.genre_id\n" +
            "WHERE genre.id IN\n" +
            "      (SELECT genre.id FROM genre WHERE name LIKE ?) " +
            "ORDER BY book.title";
    private static final String FIND_ALL_BY_PUBLISHMENT_NAMEPART = "SELECT * FROM book\n" +
            "    RIGHT JOIN publishment\n" +
            "        ON publishment.id = book.publishment_id\n" +
            "WHERE publishment.id IN\n" +
            "      (SELECT publishment.id FROM publishment WHERE name LIKE ?)" +
            "ORDER BY book.title";
    private static final String FIND_ALL_BY_KEYWORD_NAMEPART = "SELECT * FROM book\n" +
            "    RIGHT JOIN book_keyword\n" +
            "        ON book_keyword.book_id = book.id\n" +
            "WHERE book_keyword.keyword_id IN\n" +
            "      (SELECT keyword.id FROM keyword WHERE name LIKE ?)" +
            "ORDER BY book.title";
    private static final String FIND_ALL_BY_AUTHOR_NAMEPART = "SELECT * FROM book\n" +
            "    RIGHT JOIN book_author\n" +
            "        ON book_author.book_id = book.id\n" +
            "WHERE book_author.author_id IN\n" +
            "      (SELECT author.id FROM author WHERE first_name LIKE ?)" +
            "ORDER BY book.title";
    private static final String FIND_ALL_BY_READER_NAMEPART = "SELECT * FROM book" +
            " RIGHT JOIN user ON book.user_id = user.id WHERE login LIKE ?" +
            "ORDER BY book.title";
    private static final String FIND_ALL = "SELECT * FROM book";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM book LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM book";

    private final static String BOOK_ID = "id";
    private final static String BOOK_TITLE = "title";
    private final static String BOOK_PUBLISHMENT_ID = "publishment_id";
    private final static String BOOK_AVILABLE = "avilable";
    private final static String BOOK_SHELF_ID = "shelf_id";
    private final static String BOOK_GENRE_ID = "genre_id";
    private final static String BOOK_DATE_OF_RETURN = "date_of_return";
    private final static String BOOK_READER = "user_id";


    private static class Holder {
        private static final BookDAO INSTANCE = new BookDAO();
    }

    public static BookDAO getInstance() {
        return Holder.INSTANCE;
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

    public void insert(Book book) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_BOOK_QUERY);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPublishment().getId());
            preparedStatement.setInt(3, book.getGenre().getId());
            preparedStatement.setInt(4, book.getLocation().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }

        addBookAuthors(book);
        addBookKeywords(book);
    }

    private void addBookKeywords(Book book) {
        BookKeywordDAO bookKeywordDAO = BookKeywordDAO.getInstance();
        for (Keyword keyword: book.getKeywords()) {
            bookKeywordDAO.insert(book.getId(), keyword.getId());
        }
    }

    private void addBookAuthors(Book book) {
        BookAuthorDAO bookAuthorDAO = BookAuthorDAO.getInstance();
        for (Author author: book.getAuthors()) {
            bookAuthorDAO.insert(book.getId(), author.getId());
        }
    }

    private void deleteBookKeywords(Book book) {
        BookKeywordDAO bookKeywordDAO = BookKeywordDAO.getInstance();
        for (Keyword keyword: book.getKeywords()) {
            bookKeywordDAO.delete(book.getId(), keyword.getId());
        }
    }

    private void deleteBookAuthors(Book book) {
        BookAuthorDAO bookAuthorDAO = BookAuthorDAO.getInstance();
        for (Author author: book.getAuthors()) {
            bookAuthorDAO.delete(book.getId(), author.getId());
        }
    }

    public void update(Book book) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_BOOK_QUERY);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPublishment().getId());
            preparedStatement.setInt(3, book.getGenre().getId());
            preparedStatement.setBoolean(4, book.isAvilable());
            if (book.isAvilable()) {
                preparedStatement.setNull(6, JDBCType.INTEGER.getVendorTypeNumber());
                preparedStatement.setNull(5, JDBCType.DATE.getVendorTypeNumber());
            }
            else {
                preparedStatement.setInt(6, book.getReader().getId());
                preparedStatement.setDate(5, book.getDateOfReturn());
            }

            preparedStatement.setInt(7, book.getLocation().getId());
            preparedStatement.setInt(8, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
        deleteBookAuthors(book);
        deleteBookKeywords(book);
        addBookAuthors(book);
        addBookKeywords(book);
    }

    public void updateBookState(Book book) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            if (book.isAvilable()){
                preparedStatement = connection.prepareStatement(UPDATE_BOOK_AVIABLE);
                preparedStatement.setInt(1, book.getId());
            } else {
                preparedStatement = connection.prepareStatement(UPDATE_BOOK_NONAVIABLE);
                preparedStatement.setInt(1, book.getReader().getId());
                preparedStatement.setInt(2, book.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BOOK_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
//        deleteBookAuthors(book);
//        deleteBookKeywords(book);
    }

    public Book findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Book book = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = fillInBook(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findBookById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return book;
    }

    public List<Book> findBooksByAuthorId(int authorId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_BY_AUTHOR_ID);
            preparedStatement.setInt(1, authorId);
            resultSet = preparedStatement.executeQuery();
            bookList = new ArrayList<Book>();
            while (resultSet.next()) {
                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findBooksByAuthorId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return bookList;
    }

    public List<Book> findByAttributeNamepart(SearchType searchType, SearchMode mode, String searchString) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = new ArrayList<Book>();
        String query = "";
        if (mode == SearchMode.NON_STRICT) {
            query = getNamespaceQueryBySearchType(searchType);
        } else {
            query = getIdQueryBySearchType(searchType);
        }

        if (StringUtils.isNoneBlank(query)) {

            try {
                connection = getConnection();
                preparedStatement = connection.prepareStatement(query);

                // ADD CHANGE
                if (mode == SearchMode.NON_STRICT) {
                    preparedStatement.setString(1, searchString);
                } else {
                    preparedStatement.setInt(1, Integer.parseInt(searchString));
                }

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Book book = fillInBook(resultSet);
                    if (book != null) {
                        bookList.add(book);
                    }
                }
            } catch (SQLException e) {
//            log.warn("SQLException at book findBooksByAuthorId()", e);
            } finally {
                closeConnection(resultSet, preparedStatement);
            }
        }
        return bookList;
    }

    private String getNamespaceQueryBySearchType(SearchType searchType) {
        switch (searchType) {
            case AUTHOR:
                return FIND_ALL_BY_AUTHOR_NAMEPART;
            case GENRE:
                return FIND_ALL_BY_GENRE_NAMEPART;
            case KEYWORD:
                return FIND_ALL_BY_KEYWORD_NAMEPART;
            case PUBLISHMENT:
                return FIND_ALL_BY_PUBLISHMENT_NAMEPART;
            case TITLE:
                return FIND_ALL_BY_TITLE_NAMEPART;
            case READER:
                return FIND_ALL_BY_READER_NAMEPART;
            default:
                return "";

        }
    }

    private String getIdQueryBySearchType(SearchType searchType) {
        switch (searchType) {
            case AUTHOR:
                return FIND_ALL_BY_AUTHOR_ID;
            case GENRE:
                return FIND_ALL_BY_GENRE_ID;
            case KEYWORD:
                return FIND_ALL_BY_KEYWORD_ID;
            case PUBLISHMENT:
                return FIND_ALL_BY_PUBLISHMENT_ID;
            case LOCATION:
                return FIND_ALL_BY_SHELF_ID;
            case READER:
                return FIND_ALL_BY_READER_ID;
            default:
                return "";
        }
    }

    public List<Book> findAllTakenBooks() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_TAKEN_BOOKS);
            resultSet = preparedStatement.executeQuery();
            bookList = new ArrayList<Book>();
            while (resultSet.next()) {
                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findBooksByAuthorId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return bookList;
    }

    public List<Book> findBooksByReaderId(int readerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_BY_READER_ID);
            preparedStatement.setInt(1, readerId);
            resultSet = preparedStatement.executeQuery();
            bookList = new ArrayList<Book>();

            while (resultSet.next()) {
                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findBooksByAuthorId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return bookList;
    }

    public List<Book> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            if (offset >= 0) {
                // ADD move to constant 0
                preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
                preparedStatement.setInt(1, offset);
            } else {
                preparedStatement = connection.prepareStatement(FIND_ALL);
            }
            resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
            return bookList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
    }

    public List<Book> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
            return bookList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
    }

    private Book fillInBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            int id = resultSet.getInt(BOOK_ID);
            String title = resultSet.getString(BOOK_TITLE);
            boolean avilable = resultSet.getBoolean(BOOK_AVILABLE);
            Date dateOfReturn = resultSet.getDate(BOOK_DATE_OF_RETURN);
            book.setId(id);
            book.setTitle(title);
            book.setAuthors(getAuthors(id));
            book.setPublishment(getPublishment(resultSet.getInt(BOOK_PUBLISHMENT_ID)));
            book.setGenre(getGenre(resultSet.getInt(BOOK_GENRE_ID)));
            book.setReader(getReader(resultSet.getInt(BOOK_READER)));
            book.setLocation(getLocation(resultSet.getInt(BOOK_SHELF_ID)));
            book.setKeywords(getKeywords(id));
            book.setAvilable(avilable);
            book.setDateOfReturn(dateOfReturn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    private List<Author> getAuthors(int id) {
        AuthorDAO authorDAO = AuthorDAO.getInstance();
        List<Author> bookAuthors = authorDAO.findAuthorsByBookId(id);
        return bookAuthors;
    }

    private List<Keyword> getKeywords(int id) {
        KeywordDAO keywordDAO = KeywordDAO.getInstance();
        List<Keyword> bookKeywords = keywordDAO.findKeywordsByBookId(id);
        return bookKeywords;
    }

    private Publishment getPublishment(int publishmentId) {
        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
        Publishment publishment = publishmentDAO.findById(publishmentId);
        return publishment;
    }

    private Genre getGenre(int genreId) {
        GenreDAO genreDAO = GenreDAO.getInstance();
        Genre genre = genreDAO.findById(genreId);
        return genre;
    }

    private Shelf getLocation(int locationId) {
        ShelfDAO shelfDAO = ShelfDAO.getInstance();
        Shelf shelf = shelfDAO.findById(locationId);
        return shelf;
    }

    private User getReader(int userId) {
        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.findById(userId);
        return user;
    }


}
