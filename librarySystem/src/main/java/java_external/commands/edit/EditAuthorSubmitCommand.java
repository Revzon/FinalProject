package java_external.commands.edit;

import java_external.commands.Command;
import java_external.commands.list.AuthorListCommand;
import java_external.db.dao.AuthorDAO;
import java_external.db.dto.Author;
import java_external.services.UserAuthService;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditAuthorSubmitCommand implements Command {
    private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_NAME_INFO_MESSAGE = "infoMessage";
    private static final String ID_ATTR = "id";
    private static final String AUTHOR_FIRST_NAME_ATTR = "firstName";
    private static final String AUTHOR_SECOND_NAME_ATTR = "secondName";
    private static final String AUTHOR_PATRONYMIC_NAME_ATTR = "patronymicName";

    @Override
    public String execute(HttpServletRequest request) {

        Author author = parseAndGetAuthor(request);
        AuthorDAO.getInstance().update(author);

        if (Objects.nonNull(author)) {

        } else {

            // ADD ERROR MSG
        }
        return new AuthorListCommand().execute(request);


    }

    // ADD COMBINE ADD AUTHOR AND EDIT AUTHOR SIMILAR METHOD!!!
    private Author parseAndGetAuthor(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        Author author = AuthorDAO.getInstance().findById(id);
        String firstName = request.getParameter(AUTHOR_FIRST_NAME_ATTR);
        String secondName = request.getParameter(AUTHOR_SECOND_NAME_ATTR);
        String patronymicName = request.getParameter(AUTHOR_PATRONYMIC_NAME_ATTR);

        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setPatronymicName(patronymicName);

        return author;
    }

}