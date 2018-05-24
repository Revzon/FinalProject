package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.UserAuthService;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;




public class RestorePasswordControlCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.RESTORE_PASSWORD_CONTROL_PAGE_PATH);
        return page;
    }
}
