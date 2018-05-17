package java_external.db.dao;

import java_external.db.dto.Book;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class BookDAO extends AbstractDAO implements CRUD<Book> {

//    private static final String ADD_BOOK_QUERY = "INSERT INTO book(title, publishment_id, aviable, user_id, shelf_id, " +
//            "year_of_publishing, reading_room_only, deposit, genre_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ? publishment_id = ?" +
//            " aviable = ? user_id = ? shelf_id = ? year_of_publishing = ? reading_room_only = ? " +
//            "deposit = ? genre_id = ? WHERE id = ?";

    private static final String ADD_BOOK_QUERY = "INSERT INTO book(title, date_of_return, aviable, user_id, shelf_id" +
            ") VALUES(?, ?, TRUE, ?, ?)";

    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ? publishment_id = ?" +
            " aviable = ? user_id = ? shelf_id = ? year_of_publishing = ? reading_room_only = ? " +
            "deposit = ? genre_id = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE book WHERE id = ?";

    private static final String FIND_BOOK_BY_ID = "SELECT * FROM book WHERE author_id = ?";
    private static final String FIND_ALL_TAKEN_BOOKS = "SELECT * FROM book WHERE aviable = FALSE";

    private static final String FIND_BOOK_BY_AUTHOR_ID = "SELECT * FROM book RIGHT JOIN ON book_author  book_author.book_id = book.id WHERE book_author.author_id = ?";
    private static final String FIND_BOOK_BY_KEYWORD_ID = "SELECT * FROM book RIGHT JOIN ON book_keyword  book_keyword.book_id = book.id WHERE book_author.keyword_id = ?";
    private static final String FIND_BOOK_BY_GENRE_ID = "SELECT * FROM book WHERE genre_id = ?";
    private static final String FIND_BOOK_BY_PUBLISHMENT_ID = "SELECT * FROM book WHERE publishment_id = ?";
    private static final String FIND_ALL_BOOKS_BY_READER_ID = "SELECT * FROM book WHERE aviable = FALSE AND user_id = ?";
    private static final String FIND_ALL_OVERDUE_BOOKS_BY_READER_ID = "SELECT * FROM book WHERE aviable = FALSE AND user_id = ? AND date_of_return >= CURDATE()?";
    private static final String FIND_ALL_OVERDUE_BOOKS = "SELECT * FROM book WHERE aviable = FALSE AND date_of_return >= CURDATE()?";

    private static final String FIND_BOOK_BY_YEAR_OF_PUBLISHING = "SELECT * FROM book WHERE year_of_publishing = ?";
//    private static final String FIND_BOOK_FRESHER_BY_YEAR_OF_PUBLISHING = "SELECT * FROM book WHERE year_of_publishing >= ?";
//    private static final String FIND_BOOK_BY_YEAR_OF_PUBLISHING_INTERVAL = "SELECT * FROM book WHERE year_of_publishing BETWEEN ? AND ?";

    private static final String FIND_BOOK_BY_TITLE_PART = "SELECT * FROM book WHERE title LIKE ?";

    private static final String FIND_ALL = "SELECT * FROM book";

    private final static String BOOK_ID = "id";
    private final static String BOOK_TITLE = "title";
    private final static String BOOK_PUBLISHMENT_ID = "publishment_id";
    private final static String BOOK_AVIABLE = "aviable";

    private final static String BOOK_SHELF_ID = "shelf_id";
    private final static String BOOK_GENRE_ID = "genre_id";
    private final static String BOOK_DEPOSIT = "deposit";

    private final static String BOOK_READING_ROOM_ONLY = "reading_room_only";
    private final static String BOOK_YEAR_OF_PUBLISHING = "year_of_publishing";
    private final static String BOOK_DATE_OF_RETURN = "date_of_return";

    private final static String BOOK_READER = "user_id";


    private static BookDAO bookDAO;

    public static BookDAO getInstance() {
        if (bookDAO == null) {
            bookDAO = new BookDAO();
        }
        return bookDAO;
    }


    public void insert(Book book) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_BOOK_QUERY);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setNull(2, JDBCType.DATE.getVendorTypeNumber());
            preparedStatement.setNull(3, JDBCType.INTEGER.getVendorTypeNumber());
//            preparedStatement.setInt(4, book.getLocation().getId());
            preparedStatement.setInt(4, 0);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Book book) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_BOOK_QUERY);
//            preparedStatement.setString(1, book.getTitle());
////            preparedStatement.setDate();
////            preparedStatement.setNull(2, JDBCType.DATE.getVendorTypeNumber());
//            preparedStatement.setInt(3, JDBCType.INTEGER.getVendorTypeNumber());
////            preparedStatement.setInt(4, book.getLocation().getId());
//            preparedStatement.setInt(4, 0);
//
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
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_AUTHOR_ID);
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

    public List<Book> findBooksByGenreId(int genreId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_GENRE_ID);
            preparedStatement.setInt(1, genreId);
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
            preparedStatement = connection.prepareStatement(FIND_ALL_BOOKS_BY_READER_ID);
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

    public List<Book> findBooksByKeywordId(int keywordId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_KEYWORD_ID);
            preparedStatement.setInt(1, keywordId);
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

    public List<Book> findBooksByPublishmentId(int publishmentId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_PUBLISHMENT_ID);
            preparedStatement.setInt(1, publishmentId);
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

    public List<Book> findOverdueBooksByReaderId(int readerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_OVERDUE_BOOKS_BY_READER_ID);
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

    public List<Book> findOverdueBooks() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_OVERDUE_BOOKS);
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

    public List<Book> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();

            bookList = new ArrayList<Book>();

            while (resultSet.next()) {

                Book book = fillInBook(resultSet);
                if (book != null) {
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findAll()", e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    cp.closeConnection(connection);
            } catch (SQLException e) {
//                log.warn("SQLException at book findAll()", e);
            } catch (Exception e) {
//                log.warn("Exception at book findAll()", e);
            }

        }
        return bookList;
    }

    public List<Book> findByYearOfPublishing(Date year) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_YEAR_OF_PUBLISHING);
//        preparedStatement.setDate(1, year);
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

    public List<Book> findByTitlePart(String titlePart) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List bookList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE_PART);
            preparedStatement.setString(1, titlePart);
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


    private Book fillInBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            int id = resultSet.getInt(BOOK_ID);

//            int publishmentId = resultSet.getInt(BOOK_PUBLISHMENT_ID);
//            int shelfId = resultSet.getInt(BOOK_SHELF_ID);
//            int user_Id = resultSet.getInt(BOOK_READER);
//            int genre_Id = resultSet.getInt(BOOK_GENRE_ID);
            String title = resultSet.getString(BOOK_TITLE);

            boolean aviable = resultSet.getBoolean(BOOK_AVIABLE);
//            boolean readingRoomOnly = resultSet.getBoolean(BOOK_READING_ROOM_ONLY);

//            Date yearOfPublishing = resultSet.getDate(BOOK_YEAR_OF_PUBLISHING);
            Date dateOfReturn = resultSet.getDate(BOOK_DATE_OF_RETURN);

//            int deposit = resultSet.getInt(BOOK_DEPOSIT);

            book.setId(id);
            book.setTitle(title);

            book.setAuthors(null);

//                book.setPublishment(null);
            book.setReader(null);
//                book.setGenre(null);
            book.setLocation(null);
            book.setKeyWords(null);

            book.setAviable(aviable);
//                book.setReadingRoomOnly(readingRoomOnly);
//                book.setYearOfPublishing(yearOfPublishing);
//                book.setDeposit(deposit);
            book.setDateOfReturn(dateOfReturn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }


}
