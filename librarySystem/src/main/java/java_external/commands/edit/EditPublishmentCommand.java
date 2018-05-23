package java_external.commands.edit;

import java_external.commands.list.PublishmentListCommand;
import java_external.db.dao.PublishmentDAO;
import java_external.db.dto.Publishment;
import javax.servlet.http.HttpServletRequest;

public class EditPublishmentCommand implements java_external.commands.Command {

    private final static String ATTR_NAME_ID = "id";
    private final static String ATTR_NAME_PUBLISHMENT_TO_EDIT = "publishmentToEdit";


    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ATTR_NAME_ID));
        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
        Publishment publishment = publishmentDAO.findById(id);
        request.setAttribute(ATTR_NAME_PUBLISHMENT_TO_EDIT, publishment);
        return new PublishmentListCommand().execute(request);
    }
}
