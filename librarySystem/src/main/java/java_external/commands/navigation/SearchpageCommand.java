package java_external.commands.navigation;

import java_external.commands.Command;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


public class SearchpageCommand implements Command {

    private static final String ATTR_NAME_BOOK_LIST = "books";

    @Override
    public String execute(HttpServletRequest request) {
        BookDAO bookdao = BookDAO.getInstance();

        if (Objects.isNull(request.getAttribute("books"))) {
            List<Book> books;
            String currentPage = request.getParameter("page");
            int itemsOnPage = 10;
            if (StringUtils.isNoneBlank(currentPage)) {
                int page = Integer.parseInt(currentPage);
                int offset = (page - 1) * 10;
                books = bookdao.findAll(offset);
            } else {
                currentPage = "0";
                books = bookdao.findAll(0);
            }

            int booksCount = bookdao.getCount();
            int numberOfPages = (int) Math.ceil((double) booksCount / 10);

            request.setAttribute(ATTR_NAME_BOOK_LIST, books);
            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("itemsOnPage", itemsOnPage);
        }

//        request = addBookListToSearch(request);
        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.SEARCH_PAGE_PATH);
        return page;
    }


    public HttpServletRequest addBookListToSearch(HttpServletRequest request) {


        return request;
    }
}
