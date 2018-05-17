package java_external.server;

import java_external.commands.Command;
import java_external.commands.manager.CommandManager;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "BookServlet", urlPatterns = {"/"})
public class BookServlet extends HttpServlet {

    private  static CommandManager commandManager = CommandManager.getInstance();
    private String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PAGE_PATH);

    public void init(ServletConfig config) throws ServletException {
//        ServletContext sc = config.getServletContext();
        super.init(config);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        HttpSession session = request.getSession();
//        if (session.isNew()) {
//            session.setAttribute("session_commands", null);
//        }

        Command command = commandManager.getCommand(request);

        String pageToOpen = command.execute(request);
        if (!"ref".equals(pageToOpen)) {
            page = pageToOpen;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
