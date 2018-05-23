package java_external.commands.add;

import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class AddAuthorCommand implements java_external.commands.Command {
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.ADD_AUTHOR_PAGE_PATH);
    }
}
