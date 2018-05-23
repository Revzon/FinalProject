package java_external.commands.list;

import java_external.commands.Command;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


    public class OverduedBooksCommand implements Command {
        private static final String ATTR_NAME_BOOK_LIST = "books";

        @Override
        public String execute(HttpServletRequest request) {

            BookDAO bookdao = new BookDAO();
            List<Book> books = bookdao.findAllTakenBooks();

            if (books.isEmpty()) {
    //            resBookTable = new BookTable();
            }

            request.getSession().setAttribute(ATTR_NAME_BOOK_LIST, books);

               String page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.TAKEN_BOOKS_PAGE_PATH);

            return page;
        }
    }