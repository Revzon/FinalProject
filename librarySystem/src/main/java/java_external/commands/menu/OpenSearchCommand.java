package java_external.commands.menu;

import java_external.commands.Command;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olga on 16.05.18.
 */
public class OpenSearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.SEARCH_PAGE_PATH);
        return page;
    }
}
