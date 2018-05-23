package java_external.commands.list;

import java_external.commands.Command;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserListCommand implements Command {

    private static final String ATTR_NAME_USER_LIST = "users";

    @Override
    public String execute(HttpServletRequest request) {


        
        UserDAO userDAO = UserDAO.getInstance();
        List<User> users;

      String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * 10;
            users = userDAO.findAll(offset);
        } else {
            currentPage = "0";
            users = userDAO.findAll(0);
        }

        int usersCount = userDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)usersCount / 10);


        request.setAttribute(ATTR_NAME_USER_LIST, users);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);
        request.getSession().setAttribute(ATTR_NAME_USER_LIST, users);

        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.USER_LIST_PAGE_PATH);
        return page;
    }
}
