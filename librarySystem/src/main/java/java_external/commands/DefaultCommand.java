package java_external.commands;

import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.ERROR_PAGE_PATH);
        return page;
    }
}
