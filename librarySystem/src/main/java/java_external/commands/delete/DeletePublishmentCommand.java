package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.PublishmentListCommand;
import java_external.db.dao.PublishmentDAO;

import javax.servlet.http.HttpServletRequest;

public class DeletePublishmentCommand implements Command {

    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String authorIdString = request.getParameter("id");
        String infoMessage = "";
        if (authorIdString == "") {
            infoMessage = "Publishment wasn't found!";
        } else {
            int authorId = Integer.parseInt(authorIdString);
            PublishmentDAO authorDAO = PublishmentDAO.getInstance();
            authorDAO.delete(authorId);
            infoMessage = "Publishment deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new PublishmentListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
