package java_external.commands.book_operations;

import java_external.commands.Command;
import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;
import java_external.enums.SearchMode;
import java_external.enums.SearchProperty;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindBookCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        String page = "";
        String searchString = request.getParameter("searchparameter");
        String searchPropertyString = request.getParameter("searchProperty");
        String searchModeString = request.getParameter("searchMode");
        if (StringUtils.isBlank(searchString)) {
            return new SearchpageCommand().execute(request);
        }
        if (StringUtils.isBlank(searchPropertyString)
                || StringUtils.isBlank(searchModeString)) {
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);
        } else {
            chooseSearchStrategy(searchPropertyString, searchString, searchModeString);
            request.setAttribute("books", chooseSearchStrategy(searchPropertyString, searchString, searchModeString));
            return new SearchpageCommand().execute(request);
        }
    }


    private List<Book> chooseSearchStrategy(String searchPropertyString, String searchString, String searchModeString) {
        SearchProperty searchProperty = SearchProperty.valueOf(searchPropertyString);
        SearchMode mode = SearchMode.valueOf(searchModeString);
        BookDAO bookDAO = BookDAO.getInstance();
        if (mode == SearchMode.NON_STRICT) {
            searchString = "%" + searchString + "%";
        }
        return bookDAO.findByAttributeNamepart(searchProperty, mode, searchString);
    }

}
