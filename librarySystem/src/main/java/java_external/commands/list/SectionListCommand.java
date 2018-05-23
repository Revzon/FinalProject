package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.SectionDAO;
import java_external.db.dto.Section;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class SectionListCommand implements Command {

    private static final String ATTR_NAME_SECTION_LIST = "sections";

    @Override
    public String execute(HttpServletRequest request) {

        SectionDAO sectionDAO = SectionDAO.getInstance();

        List<Section> sections;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * 10;
            sections = sectionDAO.findAll(offset);
        } else {
            currentPage = "0";
            sections = sectionDAO.findAll(0);
        }

        int sectionsCount = sectionDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)sectionsCount / 10);

        request.setAttribute(ATTR_NAME_SECTION_LIST, sections);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.SECTION_LIST_PAGE_PATH);
    }
}
