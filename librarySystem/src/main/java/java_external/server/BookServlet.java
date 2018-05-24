package java_external.server;

import java_external.commands.Command;
import java_external.commands.manager.CommandManager;
import java_external.services.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class BookServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BookServlet.class);

    private  static CommandManager commandManager = CommandManager.getInstance();
    private String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PAGE_PATH);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public BookServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Command command = commandManager.getCommand(request);

        String pageToOpen = command.execute(request);

        logger.info(pageToOpen);
        if (!"ref".equals(pageToOpen)) {
            page = pageToOpen;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e);
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

