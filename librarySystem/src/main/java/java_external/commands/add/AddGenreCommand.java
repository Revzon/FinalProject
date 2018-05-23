package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.list.GenreListCommand;
import java_external.db.dao.GenreDAO;
import java_external.db.dto.Genre;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AddGenreCommand implements Command {
    private final static String ATTR_NAME_ERROR = "errorMessage";
    private final static String ATTR_NAME_OBJECT_NAME = "name";

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(ATTR_NAME_OBJECT_NAME);
        if (StringUtils.isNoneBlank(name)) {
            Genre genre = new Genre(name);
            GenreDAO genreDAO = GenreDAO.getInstance();
            genreDAO.insert(genre);
        } else {
            request.setAttribute(ATTR_NAME_ERROR, "Something went wrong. Failed to add genre");
        }
        return new GenreListCommand().execute(request);
    }
}
