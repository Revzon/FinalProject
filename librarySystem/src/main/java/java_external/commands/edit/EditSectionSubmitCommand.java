package java_external.commands.edit;

import java_external.commands.list.SectionListCommand;
import java_external.db.dao.SectionDAO;
import java_external.db.dto.Section;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditSectionSubmitCommand implements java_external.commands.Command {

    private final String ID_ATTR = "id";
    private final String NAME_ATTR = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD ERROR NESSAGE
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        String name = request.getParameter(NAME_ATTR);
        SectionDAO sectionDAO = SectionDAO.getInstance();
        Section section = sectionDAO.findById(id);

        if (Objects.nonNull(section)) {
            if (StringUtils.isNoneBlank(name)) {
                section.setName(name);
                sectionDAO.update(section);
            }
        } else {

        }
        return new SectionListCommand().execute(request);
    }
}

