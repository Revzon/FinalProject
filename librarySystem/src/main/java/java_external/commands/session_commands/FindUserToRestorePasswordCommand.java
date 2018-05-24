package java_external.commands.session_commands;

import java_external.services.authentification.AuthentificationManager;
import java_external.commands.Command;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




    public class FindUserToRestorePasswordCommand implements Command {

        private static final String USERNAME_HEADER = "username";
        private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";

        @Override
        public String execute(HttpServletRequest request) {
            AuthentificationManager authentificationManager = AuthentificationManager.getInstance();
            String page;

            String username = request.getParameter(USERNAME_HEADER);

            User user = authentificationManager.getUserByLogin(username);
            String ATTR_NAME_ERROR_MESSAGE = "errorMessage";
            if (Objects.isNull(user)) {
                request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "No user found");
                page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.RESTORE_PASSWORD_CONTROL_PAGE_PATH);
            } else{

                request.setAttribute("id", user.getId());
                page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.CHANGE_PASSWORD_PAGE_PATH);
                // ADD control question control step!!!
//                page = ConfigurationManager.getInstance().getProperty(
//                        ConfigurationManager.CONTROL_QUESTION_PAGE_PATH);
                request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "");

            }

            return page;
        }
    }

