package java_external.commands.edit;

import java_external.commands.list.PublishmentListCommand;
import java_external.db.dao.PublishmentDAO;
import java_external.db.dto.Publishment;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditPublishmentSubmitCommand implements java_external.commands.Command {

    private final String ID_ATTR = "id";
    private final String NAME_ATTR = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD ERROR NESSAGE
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        String name = request.getParameter(NAME_ATTR);
        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
        Publishment publishment = publishmentDAO.findById(id);

        if (Objects.nonNull(publishment)) {
            if (StringUtils.isNoneBlank(name)) {
                publishment.setName(name);
                publishmentDAO.update(publishment);
            }
        } else {

        }
        return new PublishmentListCommand().execute(request);
    }
}

