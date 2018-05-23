package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.AuthorDAO;
import java_external.db.dto.Author;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class AuthorListCommand implements Command {

    private static final String ATTR_NAME_AUTHOR_LIST = "authors";

    @Override
    public String execute(HttpServletRequest request) {

        AuthorDAO authorDAO = AuthorDAO.getInstance();
        List<Author> authors;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * 10;
            authors = authorDAO.findAll(offset);
        } else {
            currentPage = "0";
            authors = authorDAO.findAll(0);
        }

        int authorsCount = authorDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)authorsCount / 10);

        request.setAttribute(ATTR_NAME_AUTHOR_LIST, authors);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.AUTHOR_LIST_PAGE_PATH);
    }
}
