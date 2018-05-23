package java_external.commands.edit;

import java_external.commands.list.KeywordListCommand;
import java_external.db.dao.KeywordDAO;
import java_external.db.dto.Keyword;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditKeywordSubmitCommand implements java_external.commands.Command {

    private final String ID_ATTR = "id";
    private final String NAME_ATTR = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD ERROR NESSAGE
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        String name = request.getParameter(NAME_ATTR);
        KeywordDAO keywordDAO = KeywordDAO.getInstance();
        Keyword keyword = keywordDAO.findById(id);

        if (Objects.nonNull(keyword)) {
            if (StringUtils.isNoneBlank(name)) {
                keyword.setName(name);
                keywordDAO.update(keyword);
            }
        } else {

        }
        return new KeywordListCommand().execute(request);
    }
}

