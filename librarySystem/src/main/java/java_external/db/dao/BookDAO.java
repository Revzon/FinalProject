package java_external.db.dao;

import java_external.db.dto.*;
import java_external.enums.SearchMode;
import java_external.enums.SearchProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


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
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Book book) {
        executeQuery(ADD_BOOK_QUERY, preparedStatement -> {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPublishment().getId());
            preparedStatement.setInt(3, book.getGenre().getId());
            preparedStatement.setInt(4, book.getLocation().getId());
        });

        addBookAuthors(book);
        addBookKeywords(book);
    }

    private void addBookKeywords(Book book) {
        BookKeywordDAO bookKeywordDAO = BookKeywordDAO.getInstance();
        for (Keyword keyword : book.getKeywords()) {
            bookKeywordDAO.insert(book.getId(), keyword.getId());
        }
    }

    private void addBookAuthors(Book book) {
        BookAuthorDAO bookAuthorDAO = BookAuthorDAO.getInstance();
        for (Author author : book.getAuthors()) {
            bookAuthorDAO.insert(book.getId(), author.getId());
        }
    }

    private void deleteBookKeywords(Book book) {
        BookKeywordDAO bookKeywordDAO = BookKeywordDAO.getInstance();
        for (Keyword keyword : book.getKeywords()) {
            bookKeywordDAO.delete(book.getId(), keyword.getId());
        }
    }

    private void deleteBookAuthors(Book book) {
        BookAuthorDAO bookAuthorDAO = BookAuthorDAO.getInstance();
        for (Author author : book.getAuthors()) {
            bookAuthorDAO.delete(book.getId(), author.getId());
        }
    }

    public void update(Book book) {
        executeQuery(UPDATE_BOOK_QUERY, preparedStatement -> {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPublishment().getId());
            preparedStatement.setInt(3, book.getGenre().getId());
            preparedStatement.setBoolean(4, book.isAvilable());
            if (book.isAvilable()) {
                preparedStatement.setNull(6, JDBCType.INTEGER.getVendorTypeNumber());
                preparedStatement.setNull(5, JDBCType.DATE.getVendorTypeNumber());
            } else {
                preparedStatement.setInt(6, book.getReader().getId());
                preparedStatement.setDate(5, book.getDateOfReturn());
            }
            preparedStatement.setInt(7, book.getLocation().getId());
            preparedStatement.setInt(8, book.getId());
            preparedStatement.executeUpdate();
        });

        deleteBookAuthors(book);
        deleteBookKeywords(book);
        addBookAuthors(book);
        addBookKeywords(book);
    }

    public void updateBookState(Book book) {
        if (book.isAvilable()) {
            executeQuery(UPDATE_BOOK_AVIABLE, preparedStatement -> {
                preparedStatement.setInt(1, book.getId());
            });
        } else {
            executeQuery(UPDATE_BOOK_NONAVIABLE, preparedStatement -> {
                preparedStatement.setInt(1, book.getReader().getId());
                preparedStatement.setInt(2, book.getId());
            });
        }
    }

    public void delete(int id) {
        executeQuery(DELETE_BOOK_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        });
//        deleteBookAuthors(book);
//        deleteBookKeywords(book);
    }

    public Book findById(int id) {
        return executeQuery(FIND_BOOK_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                resultSet -> {
                    if (resultSet.next()) {
                        return getBookFromRs(resultSet);
                    }
                    return null;
                });
    }

    public List<Book> findBooksByAuthorId(int authorId) {
        return executeQuery(FIND_ALL_BY_AUTHOR_ID,
                preparedStatement -> preparedStatement.setInt(1, authorId),
                resultSet -> {
                    ArrayList<Book> bookList = new ArrayList<>();
                    while (resultSet.next()) {
                        Book book = getBookFromRs(resultSet);
                        if (book != null) {
                            bookList.add(book);
                        }
                    }
                    return bookList;
                });
    }

    public List<Book> findByAttributeNamepart(SearchProperty searchProperty, SearchMode mode, String searchString) {
        String query;
        if (mode == SearchMode.NON_STRICT) {
            query = getNamespaceQueryBySearchType(searchProperty);
        } else {
            query = getIdQueryBySearchType(searchProperty);
        }
        if (StringUtils.isNoneBlank(query)) {
            if (mode == SearchMode.NON_STRICT) {
                return executeQuery(query,
                        preparedStatement -> preparedStatement.setString(1, searchString),
                        this::getBooksFromRS);
            } else {
                return executeQuery(query,
                        preparedStatement -> preparedStatement.setInt(1, Integer.parseInt(searchString)),
                        this::getBooksFromRS);
            }
        }
        return new ArrayList<>();
    }

    private List<Book> getBooksFromRS(ResultSet resultSet) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        while (resultSet.next()) {
            Book book = getBookFromRs(resultSet);
            if (book != null) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    private String getNamespaceQueryBySearchType(SearchProperty searchProperty) {
        switch (searchProperty) {
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

    private String getIdQueryBySearchType(SearchProperty searchProperty) {
        switch (searchProperty) {
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
        return executeQuery(FIND_ALL_TAKEN_BOOKS, this::getBooksFromRS);
    }


    public List<Book> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE,
                preparedStatement -> preparedStatement.setInt(1, offset),
                this::getBooksFromRS);
    }

    public List<Book> findAll() {
        return executeQuery(FIND_ALL, this::getBooksFromRS);
    }

    private Book getBookFromRs(ResultSet resultSet) throws SQLException {
        Book book = new Book();
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
        return book;
    }

    private List<Author> getAuthors(int id) {
        AuthorDAO authorDAO = AuthorDAO.getInstance();
        return authorDAO.findAuthorsByBookId(id);
    }

    private List<Keyword> getKeywords(int id) {
        KeywordDAO keywordDAO = KeywordDAO.getInstance();
        return keywordDAO.findKeywordsByBookId(id);
    }

    private Publishment getPublishment(int publishmentId) {
        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
        return publishmentDAO.findById(publishmentId);
    }

    private Genre getGenre(int genreId) {
        GenreDAO genreDAO = GenreDAO.getInstance();
        return genreDAO.findById(genreId);
    }

    private Shelf getLocation(int locationId) {
        ShelfDAO shelfDAO = ShelfDAO.getInstance();
        return shelfDAO.findById(locationId);
    }

    private User getReader(int userId) {
        UserDAO userDAO = UserDAO.getInstance();
        return userDAO.findById(userId);
    }

}
