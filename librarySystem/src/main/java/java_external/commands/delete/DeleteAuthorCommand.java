package java_external.commands.delete;

import java_external.commands.list.AuthorListCommand;
import java_external.commands.Command;
import java_external.db.dao.AuthorDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteAuthorCommand implements Command {
    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String authorIdString = request.getParameter("id");
        String infoMessage = "";
        if (authorIdString == "") {
            infoMessage = "Author wasn't found!";
        } else {
            int authorId = Integer.parseInt(authorIdString);
            AuthorDAO authorDAO = AuthorDAO.getInstance();
            authorDAO.delete(authorId);
            infoMessage = "Author deleted successfully";
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new AuthorListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
