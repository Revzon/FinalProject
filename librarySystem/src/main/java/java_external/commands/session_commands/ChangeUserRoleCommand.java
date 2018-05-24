package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.commands.list.UserListCommand;
import java_external.db.dao.UserDAO;
import java_external.enums.Role;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class ChangeUserRoleCommand implements Command {

    private static final String ATTR_NAME_USER_LIST = "users";

    @Override
    public String execute(HttpServletRequest request) {
        String page ="";
        int userId = Integer.parseInt(request.getParameter("id"));
        int roleId = Integer.parseInt(request.getParameter("role"));
        if (Objects.nonNull(Role.getRoleByd(roleId))) {
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.updateRole(userId, roleId);
            page = new UserListCommand().execute(request);
        } else {
            //ADD EXCEPTION IF ROLE NOT FOUND
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }

       return page;
    }
}
