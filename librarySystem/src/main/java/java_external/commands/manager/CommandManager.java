package java_external.commands.manager;

import java_external.commands.*;
import java_external.commands.menu.OpenSearchCommand;
import java_external.commands.session_commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by olga on 16.05.18.
 */
public class CommandManager {

    private static HashMap<String, Command> commands;
    private static CommandManager instance = null;
    private static final String PARAM_NAME_ACTION = "action";
    private static final String ATTR_NAME_ID = "numparam";

    private CommandManager(){
        commands.put("homepage", new HomepageCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("authentificate", new LoginSubmitCommand());
        commands.put("register-submit", new RegistrationSubmitCommand());
        commands.put("search", new OpenSearchCommand());
        commands.put("findBook", new FindBookCommand());
        commands.put("searchMenu", new OpenSearchCommand());
        commands.put("ref", new RefCommand());

    }

    public static CommandManager getInstance(){
        if (Objects.isNull(instance)) {
            instance = new CommandManager();
        }
        return instance;
    }


    public Command getCommand(HttpServletRequest request){
        Command command = null;
        command = searchCommand(request);
        if (Objects.isNull(command)) {
            command = new DefaultCommand();
        }
        return command;
    }

//    private Command searchCommand(HttpServletRequest request) {
//
//        String action = request.getParameter(PARAM_NAME_ACTION);
//        if (Objects.isNull(action)) {
//            return null;
//        }
//        Command command = commands.get(action);
//        return command;
//    }


    private Command searchCommand(HttpServletRequest request) {
        Enumeration<?> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = (String) params.nextElement();
            if (param.startsWith(PARAM_NAME_ACTION)) {
                String action = request.getParameter(param);
                Command command = commands.get(action);
                String sub = getSubParam(param);
                if (Objects.nonNull(sub)) {
                    request.setAttribute(ATTR_NAME_ID, Integer.parseInt(sub));
                }
                return command;
            }
        }
        return null;
    }

    private String getSubParam(String param) {
        String id = param.substring(PARAM_NAME_ACTION.length());
        return (id.length() > 0) ? id : null;
    }


}
