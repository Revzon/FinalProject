package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;
import java_external.services.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by olga on 16.05.18.
 */
public class RegistrationSubmitCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

        RegistrationService registrationService = RegistrationService.getInstance();
        String page;

        User user = registrationService.registrateUser(request);

        if (Objects.nonNull(user)) {

            request.getSession().setAttribute("user", user);
            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.SEARCH_PAGE_PATH);
//            request.setAttribute(ATTR_NAME_ERROR_MESSAGE,
//                    null);

        } else {
//            request.setAttribute(ATTR_NAME_ERROR_MESSAGE,
//                    "login, email or phone already exist");

            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.REGISTRATION_PAGE_PATH);
        }

        return page;

    }
}
