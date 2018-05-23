package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.GenreListCommand;
import java_external.db.dao.GenreDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteGenreCommand implements Command {
    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String genreIdString = request.getParameter("id");
        String infoMessage = "";
        if (genreIdString == "") {
            infoMessage = "Genre wasn't found!";
        } else {
            int genreId = Integer.parseInt(genreIdString);
            GenreDAO genreDAO = GenreDAO.getInstance();
            genreDAO.delete(genreId);
            infoMessage = "Genre deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new GenreListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
