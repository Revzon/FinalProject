package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.list.SectionListCommand;
import java_external.db.dao.SectionDAO;
import java_external.db.dto.Section;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AddSectionCommand implements Command {
    private final static String ATTR_NAME_ERROR = "errorMessage";
    private final static String ATTR_NAME_OBJECT_NAME = "name";

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(ATTR_NAME_OBJECT_NAME);
        if (StringUtils.isBlank(name)) {
            request.setAttribute(ATTR_NAME_ERROR, "Something went wrong. Failed to add section");
        } else {
            Section section = new Section(name);
            SectionDAO sectionDAO = SectionDAO.getInstance();
            sectionDAO.insert(section);
        }

        return new SectionListCommand().execute(request);
    }
}
