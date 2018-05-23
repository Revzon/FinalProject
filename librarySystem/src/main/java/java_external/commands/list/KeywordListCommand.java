package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.KeywordDAO;
import java_external.db.dto.Keyword;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class KeywordListCommand implements Command {

    private static final String ATTR_NAME_KEYWORD_LIST = "keywords";

    @Override
    public String execute(HttpServletRequest request) {

        KeywordDAO keywordDAO = KeywordDAO.getInstance();

        List<Keyword> keywords;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * 10;
            keywords = keywordDAO.findAll(offset);
        } else {
            currentPage = "0";
            keywords = keywordDAO.findAll(0);
        }

        int keywordsCount = keywordDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)keywordsCount / 10);

        request.setAttribute(ATTR_NAME_KEYWORD_LIST, keywords);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.KEYWORD_LIST_PAGE_PATH);
    }
}
