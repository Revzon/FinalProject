package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;
import java_external.utils.Md5;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;




public class ChangePasswordSubmitCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        // ADD MODIFY LATER!!! NOW TEMPORARY VARIANT
        String page = "";
        String password = request.getParameter("password");


        // ADD IS A TEMPORARY DECISION!!! NEEDS CHANGE


        String idString = request.getParameter("id");
        if (StringUtils.isBlank(idString)) {
            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);
        } else {
            UserDAO userDAO = UserDAO.getInstance();

            int id = Integer.parseInt(idString);
            User user = userDAO.findById(id);

            user.setPassword(Md5.md5Password(password));
            userDAO.updatePassword(user);
            page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.LOGIN_PAGE_PATH);
//            }
        }
        return page;
    }

    private void updatePassword(User user, String password) {
        UserDAO userDAO = UserDAO.getInstance();
        user.setPassword(Md5.md5Password(password));
        userDAO.updatePassword(user);
    }
}
