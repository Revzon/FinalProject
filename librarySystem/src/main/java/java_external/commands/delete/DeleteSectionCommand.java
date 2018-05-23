package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.SectionListCommand;
import java_external.db.dao.SectionDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteSectionCommand implements Command {
    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String sectionIdString = request.getParameter("id");
        String infoMessage = "";
        if (sectionIdString == "") {
            infoMessage = "Section wasn't found!";
        } else {
            int sectionId = Integer.parseInt(sectionIdString);
            SectionDAO sectionDAO = SectionDAO.getInstance();
            sectionDAO.delete(sectionId);
            infoMessage = "Section deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new SectionListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
