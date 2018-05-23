package java_external.commands.edit;

import java_external.commands.list.ShelfListCommand;
import java_external.db.dao.ShelfDAO;
import java_external.db.dto.Shelf;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditShelfSubmitCommand implements java_external.commands.Command {

    private final String ID_ATTR = "id";
    private final String NAME_ATTR = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD ERROR NESSAGE
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        String name = request.getParameter(NAME_ATTR);
        ShelfDAO shelfDAO = ShelfDAO.getInstance();
        Shelf shelf = shelfDAO.findById(id);

        if (Objects.nonNull(shelf)) {
            if (StringUtils.isNoneBlank(name)) {
                shelf.setName(name);
                shelfDAO.update(shelf);
            }
        } else {

        }
        return new ShelfListCommand().execute(request);
    }
}

