package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.PublishmentDAO;
import java_external.db.dto.Publishment;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class PublishmentListCommand implements Command {

    private static final String ATTR_NAME_PUBLISHMENT_LIST = "publishments";

    @Override
    public String execute(HttpServletRequest request) {

        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();

        List<Publishment> publishments;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * 10;
            publishments = publishmentDAO.findAll(offset);
        } else {
            currentPage = "0";
            publishments = publishmentDAO.findAll(0);
        }

        int publishmentsCount = publishmentDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)publishmentsCount / 10);

        request.setAttribute(ATTR_NAME_PUBLISHMENT_LIST, publishments);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.PUBLISHMENT_LIST_PAGE_PATH);
    }
}
