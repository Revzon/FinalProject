package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.KeywordListCommand;
import java_external.db.dao.KeywordDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteKeywordCommand implements Command {
    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String keywordIdString = request.getParameter("id");
        String infoMessage = "";
        if (keywordIdString == "") {
            infoMessage = "Keyword wasn't found!";
        } else {
            int keywordId = Integer.parseInt(keywordIdString);
            KeywordDAO keywordDAO = KeywordDAO.getInstance();
            keywordDAO.delete(keywordId);
            infoMessage = "Keyword deleted successfully";
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new KeywordListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
