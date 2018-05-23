package java_external.commands.list;


import java_external.commands.Command;
import java_external.db.dao.GenreDAO;
import java_external.db.dao.GenreDAO;
import java_external.db.dto.Genre;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class GenreListCommand implements Command {

    private static final String ATTR_NAME_GENRE_LIST = "genres";

    @Override
    public String execute(HttpServletRequest request) {

        GenreDAO genreDAO = GenreDAO.getInstance();

        List<Genre> genres;
        String currentPage = request.getParameter("page");
        int itemsOnPage = 10;
        if (StringUtils.isNoneBlank(currentPage)) {
            int page = Integer.parseInt(currentPage);
            int offset = (page - 1) * itemsOnPage;
            genres = genreDAO.findAll(offset);
        } else {
            currentPage = "0";
            genres = genreDAO.findAll(0);
        }

        int genresCount = genreDAO.getCount();
        int numberOfPages =  (int) Math.ceil((double)genresCount / itemsOnPage);

        request.setAttribute(ATTR_NAME_GENRE_LIST, genres);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsOnPage", itemsOnPage);


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.GENRE_LIST_PAGE_PATH);
    }
}
