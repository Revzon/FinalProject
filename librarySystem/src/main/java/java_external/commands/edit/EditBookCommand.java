package java_external.commands.edit;

import java_external.commands.Command;
import java_external.db.dao.*;
import java_external.db.dto.*;
import java_external.services.manager.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class EditBookCommand implements Command {


    private final String BOOK_ATTR = "book";
    private final String AUTHOR_LIST_ATTR = "authorList";
    private final String PUBLISHMENT_LIST_ATTR = "publishmentList";
    private final String USER_LIST_ATTR = "userList";
    private final String GENRE_LIST_ATTR = "genreList";
    private final String SHELF_LIST_ATTR = "shelfList";


    private final String ID_ATTR = "id";

    @Override
    public String execute(HttpServletRequest request) {
        String page = "";

        // ADD ERROR NESSAGE
        String idString = request.getParameter(ID_ATTR);
        if (StringUtils.isBlank(idString)) {
            page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);

        } else {

            int bookId= Integer.parseInt(idString);
            BookDAO bookDAO = BookDAO.getInstance();
            Book book = bookDAO.findById(bookId);

            if (Objects.nonNull(book)) {
                request.setAttribute(BOOK_ATTR, book);

                page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.EDIT_BOOK_PAGE_PATH);

                AuthorDAO authorDAO = AuthorDAO.getInstance();
                List<Author> authorList = authorDAO.findAll();
                request.setAttribute(AUTHOR_LIST_ATTR, authorList);

                PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
                List<Publishment> publishmentList = publishmentDAO.findAll();
                request.setAttribute(PUBLISHMENT_LIST_ATTR, publishmentList);

                UserDAO userDAO = UserDAO.getInstance();
                List<User> userList = userDAO.findAll();
                request.setAttribute(USER_LIST_ATTR, userList);

                GenreDAO genreDAO = GenreDAO.getInstance();
                List<Genre> genreList = genreDAO.findAll();
                request.setAttribute(GENRE_LIST_ATTR, genreList);

                ShelfDAO shelfDAO = ShelfDAO.getInstance();
                List<Shelf> shelfList = shelfDAO.findAll();
                request.setAttribute(SHELF_LIST_ATTR, shelfList);

            } else {
                page = ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.SEARCH_PAGE_PATH);

            }
        }
        return page;
    }
}
