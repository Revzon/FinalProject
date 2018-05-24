package java_external.commands.auth;

import java_external.commands.navigation.SearchpageCommand;
import java_external.services.authentification.AuthentificationManager;
import java_external.commands.Command;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


public class LoginSubmitCommand implements Command {

    private static final String USERNAME_HEADER = "username";
    private static final String PASSWORD_HEADER = "password";
    private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        AuthentificationManager authentificationManager = AuthentificationManager.getInstance();
        String page;

        String username = request.getParameter(USERNAME_HEADER);
        String passwordRaw = request.getParameter(PASSWORD_HEADER);

        User user = authentificationManager.checkUser(username, passwordRaw, request);

        if (Objects.nonNull(user)) {

            request.getSession().setAttribute("user", user);
            page = new SearchpageCommand().execute(request);
            request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "");

        } else {

            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.LOGIN_PAGE_PATH);
        }

        return page;
    }
}
