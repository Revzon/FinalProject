package java_external.commands;

import javax.servlet.http.HttpServletRequest;


public interface Command {

    String execute(HttpServletRequest request);
}
