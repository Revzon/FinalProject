package java_external.commands.session_commands;

import java_external.services.authentification.AuthentificationManager;
import java_external.commands.Command;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by olga on 16.05.18.
 */
public class LoginSubmitCommand implements Command {

    private static final String USERNAME_HEADER = "username";
    private static final String PASSWORD_HEADER = "password";

    @Override
    public String execute(HttpServletRequest request) {
        AuthentificationManager authentificationManager = AuthentificationManager.getInstance();
        String page;

        String username = request.getParameter(USERNAME_HEADER);
        String passwordRaw = request.getParameter(PASSWORD_HEADER);

        User user = authentificationManager.checkUser(username, passwordRaw);

        if (Objects.nonNull(user)) {

            request.getSession().setAttribute("user", user);
            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.SEARCH_PAGE_PATH);
//            request.setAttribute(ATTR_NAME_ERROR_MESSAGE,
//                    null);

        } else {
//            request.setAttribute(ATTR_NAME_ERROR_MESSAGE,
//                    "login, password is incorrect");

            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.LOGIN_PAGE_PATH);
        }

        return page;
    }
}
