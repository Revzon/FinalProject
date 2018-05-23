package java_external.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;




public class BookKeywordDAO extends AbstractDAO{

    private static final String ADD_BOOK_KEYWORD = "INSERT INTO book_keyword(book_id, keyword_id) VALUES(?, ?)";
    private static final String DELETE_BOOK_KEYWORD = "DELETE FROM book_keyword WHERE book_id = ? AND keyword_id = ?";

    private final static String BOOK_KEYWORD_BOOK_ID = "book_id";
    private final static String BOOK_KEYWORD_KEYWORD_ID = "keyword_id";

    private static class Holder {
        private static final BookKeywordDAO INSTANCE = new BookKeywordDAO();
    }

    public static BookKeywordDAO getInstance() {
        return Holder.INSTANCE;
    }


    public void insert(int bookId, int keywordId) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_BOOK_KEYWORD);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, keywordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book_keyword insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        };
    }

    public void delete(int bookId, int keywordId) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BOOK_KEYWORD);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, keywordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at book_keyword delete()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
        ;
    }

}
