package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.UserAuthService;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;




public class ChangePasswordCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        if (UserAuthService.getInstance().checkRightsToEditPersonalPage(request)) {
            User currentUser = (User) request.getSession().getAttribute("user");
            int userId = Integer.parseInt(request.getParameter("id"));
            User userToEdit;

            if ((currentUser.getId() == userId)) {
                userToEdit = currentUser;
            } else {
                UserDAO userDAO = UserDAO.getInstance();
                userToEdit = userDAO.findById(userId);
            }
            request.setAttribute("user", userToEdit);
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.CHANGE_PASSWORD_PAGE_PATH);

        } else {
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);
        }
    }
}
