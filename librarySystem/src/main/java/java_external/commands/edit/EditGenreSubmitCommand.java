package java_external.commands.edit;

import java_external.commands.list.GenreListCommand;
import java_external.db.dao.GenreDAO;
import java_external.db.dto.Genre;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditGenreSubmitCommand implements java_external.commands.Command {

    private final String ID_ATTR = "id";
    private final String NAME_ATTR = "name";

    @Override
    public String execute(HttpServletRequest request) {

        // ADD ERROR NESSAGE
        int id = Integer.parseInt(request.getParameter(ID_ATTR));
        String name = request.getParameter(NAME_ATTR);
        GenreDAO genreDAO = GenreDAO.getInstance();
        Genre genre = genreDAO.findById(id);

        if (Objects.nonNull(genre)) {
            if (StringUtils.isNoneBlank(name)) {
                genre.setName(name);
                genreDAO.update(genre);
            }
        } else {

        }
        return new GenreListCommand().execute(request);
    }
}

