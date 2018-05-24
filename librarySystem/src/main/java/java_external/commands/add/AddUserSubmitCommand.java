package java_external.commands.add;

import java_external.commands.Command;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;
import java_external.services.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddUserSubmitCommand implements Command {
    private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {

        UserAuthService userAuthService = UserAuthService.getInstance();
        String page;

        User user = userAuthService.registrateUser(request);

        if (Objects.nonNull(user)) {

            request.getSession().setAttribute("user", user);
            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.SEARCH_PAGE_PATH);

        } else {

            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.REGISTRATION_PAGE_PATH);
        }

        return page;

    }
}
