package java_external.commands;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olga on 16.05.18.
 */
public interface Command {

    public String execute(HttpServletRequest request);


}
