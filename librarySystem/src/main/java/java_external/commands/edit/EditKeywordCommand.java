package java_external.commands.edit;

import java_external.commands.list.KeywordListCommand;
import java_external.db.dao.KeywordDAO;
import java_external.db.dto.Keyword;

import javax.servlet.http.HttpServletRequest;




public class EditKeywordCommand implements java_external.commands.Command {

    private final static String ATTR_NAME_ID = "id";
    private final static String ATTR_NAME_KEYWORD_TO_EDIT = "keywordToEdit";


    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ATTR_NAME_ID));
        KeywordDAO keywordDAO = KeywordDAO.getInstance();
        Keyword keyword = keywordDAO.findById(id);
        request.setAttribute(ATTR_NAME_KEYWORD_TO_EDIT, keyword);
        return new KeywordListCommand().execute(request);
    }
}
