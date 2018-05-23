package java_external.commands.edit;

import java_external.db.dao.AuthorDAO;
import java_external.db.dto.Author;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;




public class EditAuthorCommand implements java_external.commands.Command {
    @Override
    public String execute(HttpServletRequest request) {
           int id = Integer.parseInt(request.getParameter("id"));
            Author author;
                AuthorDAO authorDAO = AuthorDAO.getInstance();
                author = authorDAO.findById(id);
            request.setAttribute("author", author);
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.EDIT_AUTHOR_PAGE_PATH);
    }
}
