package java_external.commands.edit;

import java_external.commands.Command;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;




public class EditUserSubmitCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.ERROR_PAGE_PATH);
        return page;
    }
}
