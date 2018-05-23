package java_external.commands.edit;

import java_external.commands.list.SectionListCommand;
import java_external.db.dao.SectionDAO;
import java_external.db.dto.Section;

import javax.servlet.http.HttpServletRequest;




public class EditSectionCommand implements java_external.commands.Command {

    private final static String ATTR_NAME_ID = "id";
    private final static String ATTR_NAME_SECTION_TO_EDIT = "sectionToEdit";


    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ATTR_NAME_ID));
        SectionDAO sectionDAO = SectionDAO.getInstance();
        Section section = sectionDAO.findById(id);
        request.setAttribute(ATTR_NAME_SECTION_TO_EDIT, section);
        return new SectionListCommand().execute(request);
    }
}
