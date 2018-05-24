package java_external.commands.add;

import java_external.commands.Command;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.REGISTRATION_PAGE_PATH);
    }
}


