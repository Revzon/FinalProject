package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.ShelfDAO;
import java_external.db.dto.Shelf;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ShelfListCommand implements Command {

    private static final String ATTR_NAME_SHELF_LIST = "shelfs";

    @Override
    public String execute(HttpServletRequest request) {

        ShelfDAO shelfDAO = ShelfDAO.getInstance();

        List<Shelf> shelfs;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * itemsOnPage;
            shelfs = shelfDAO.findAll(offset);
        } else {
            currentPage = "0";
            shelfs = shelfDAO.findAll(0);
        }

        int shelfsCount = shelfDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)shelfsCount / itemsOnPage);

        request.setAttribute(ATTR_NAME_SHELF_LIST, shelfs);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.SHELF_LIST_PAGE_PATH);
    }
}
