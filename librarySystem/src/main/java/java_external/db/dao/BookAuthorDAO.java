package java_external.db.dao;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class BookAuthorDAO extends AbstractDAO {

    private static final String ADD_BOOK_AUTHOR = "INSERT INTO book_author(book_id, author_id) VALUES(?, ?)";
    private static final String DELETE_BOOK_AUTHOR = "DELETE FROM book_author WHERE book_id = ? AND author_id = ?";

    private final static String BOOK_AUTHOR_BOOK_ID = "book_id";
    private final static String BOOK_AUTHOR_AUTHOR_ID = "author_id";

    private static class Holder {
        private static final BookAuthorDAO INSTANCE = new BookAuthorDAO();
    }

    public static BookAuthorDAO getInstance() {
        return Holder.INSTANCE;
    }


    public void insert(int bookId, int authorId) {
        executeQuery(ADD_BOOK_AUTHOR, preparedStatement -> {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
        });
    }

    public void delete(int bookId, int authorId) {
        executeQuery(DELETE_BOOK_AUTHOR, preparedStatement -> {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();
        });
    }

}
