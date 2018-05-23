package java_external.commands.edit;

import java_external.commands.list.GenreListCommand;
import java_external.db.dao.GenreDAO;
import java_external.db.dto.Genre;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;




public class EditGenreCommand implements java_external.commands.Command {

    private final static String ATTR_NAME_ID = "id";
    private final static String ATTR_NAME_GENRE_TO_EDIT = "genreToEdit";


    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ATTR_NAME_ID));
        GenreDAO genreDAO = GenreDAO.getInstance();
        Genre genre = genreDAO.findById(id);
        request.setAttribute(ATTR_NAME_GENRE_TO_EDIT, genre);
        return new GenreListCommand().execute(request);
    }
}
