package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.list.PublishmentListCommand;
import java_external.db.dao.PublishmentDAO;
import java_external.db.dto.Publishment;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AddPublishmentCommand implements Command {
    private final static String ATTR_NAME_ERROR = "errorMessage";
    private final static String ATTR_NAME_OBJECT_NAME = "name";

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(ATTR_NAME_OBJECT_NAME);
        if (StringUtils.isBlank(name)) {
            request.setAttribute(ATTR_NAME_ERROR, "Something went wrong. Failed to add publishment");
        } else {
            Publishment publishment = new Publishment(name);
            PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
            publishmentDAO.insert(publishment);
        }

        return new PublishmentListCommand().execute(request);
    }
}
