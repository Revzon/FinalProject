package java_external.db.dao;

import java_external.db.dao.base.QueryManager;


public class BookKeywordDAO extends AbstractDAO {

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
        QueryManager.executeQuery(ADD_BOOK_KEYWORD, preparedStatement -> {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, keywordId);
        });
    }

    public void delete(int bookId, int keywordId) {
        QueryManager.executeQuery(DELETE_BOOK_KEYWORD, preparedStatement -> {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, keywordId);
        });

    }

}
