package java_external.commands.session_commands;

import java_external.commands.Command;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olga on 16.05.18.
 */
public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String page = null;
        request.getSession().setAttribute("user", null);
        page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.INDEX_PAGE_PATH);
        return page;
    }
}

