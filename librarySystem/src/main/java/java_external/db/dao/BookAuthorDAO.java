package java_external.db.dao;

/**
 * Created by olga on 14.05.18.
 */
public class BookAuthorDAO {

    private static BookAuthorDAO bookAuthorDAO;

    public static BookAuthorDAO getInstance() {
        if (bookAuthorDAO == null) {
            bookAuthorDAO = new BookAuthorDAO();
        }
        return bookAuthorDAO;
    }





}
