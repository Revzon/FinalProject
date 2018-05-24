package java_external.commands.navigation;

import java_external.commands.Command;
import java_external.db.dao.BookDAO;
import java_external.db.dao.UserDAO;
import java_external.db.dto.Book;
import java_external.db.dto.User;
import java_external.enums.SearchMode;
import java_external.enums.SearchProperty;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;




public class UserpageCommand implements Command {
    private final static String ATTR_NAME_INFO = "info";
    private static final String ID_ATTR = "id";

    @Override
    public String execute(HttpServletRequest request) {
//        String idString = request.getParameter(ID_ATTR);
        int id = (int) request.getAttribute(ID_ATTR);
//        if (StringUtils.isBlank(idString)){
//            return ConfigurationManager.getInstance().getProperty(
//                    ConfigurationManager.LOGIN_PAGE_PATH);
//        }

//        int id = Integer.parseInt(idString);

        User user = UserDAO.getInstance().findById(id);
        String page;

        if (Objects.isNull(user)) {
            // add error message
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.LOGIN_PAGE_PATH);
        } else {
            List<Book> borrowedBooks = getBorrowedBooksList(id);
            request.setAttribute("books", borrowedBooks);
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.USER_PAGE_PATH);
        }
        // CHECK THE ALGORITHM! ADD LOGGS
    }

    private List<Book> getBorrowedBooksList(int id) {
        List<Book> borrowedBooks;
        BookDAO bookDAO = BookDAO.getInstance();
        borrowedBooks = bookDAO.findByAttributeNamepart(SearchProperty.READER, SearchMode.ID, String.valueOf(id));
        return borrowedBooks;
    }
}
