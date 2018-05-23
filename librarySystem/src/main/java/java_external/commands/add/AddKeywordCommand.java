package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.list.KeywordListCommand;
import java_external.db.dao.KeywordDAO;
import java_external.db.dto.Keyword;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AddKeywordCommand implements Command {
    private final static String ATTR_NAME_ERROR = "errorMessage";
    private final static String ATTR_NAME_OBJECT_NAME = "name";

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(ATTR_NAME_OBJECT_NAME);
        if (StringUtils.isBlank(name)) {
            request.setAttribute(ATTR_NAME_ERROR, "Something went wrong. Failed to add keyword");
        } else {
            Keyword keyword = new Keyword(name);
            KeywordDAO keywordDAO = KeywordDAO.getInstance();
            keywordDAO.insert(keyword);
        }

        return new KeywordListCommand().execute(request);
    }
}
