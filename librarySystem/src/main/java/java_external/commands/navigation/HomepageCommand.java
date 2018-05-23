package java_external.commands.navigation;

import java_external.commands.Command;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class HomepageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.INDEX_PAGE_PATH);
        return page;
    }
}
