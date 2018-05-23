package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.list.ShelfListCommand;
import java_external.db.dao.SectionDAO;
import java_external.db.dao.ShelfDAO;
import java_external.db.dto.Section;
import java_external.db.dto.Shelf;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AddShelfCommand implements Command {
    private final static String ATTR_NAME_ERROR = "errorMessage";
    private final static String ATTR_NAME_OBJECT_NAME = "name";
    private final static String ATTR_NAME_SECTION_ID = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD CHECK, LOGS, EXCEPTIONS!
        String name = request.getParameter(ATTR_NAME_OBJECT_NAME);
        int sectionId = Integer.parseInt(request.getParameter(ATTR_NAME_SECTION_ID));
        Section section = SectionDAO.getInstance().findById(sectionId);
        if (StringUtils.isBlank(name)) {
            request.setAttribute(ATTR_NAME_ERROR, "Something went wrong. Failed to add shelf");
        } else {
            Shelf shelf = new Shelf(name, section);
            ShelfDAO shelfDAO = ShelfDAO.getInstance();
            shelfDAO.insert(shelf);
        }

        return new ShelfListCommand().execute(request);
    }
}
