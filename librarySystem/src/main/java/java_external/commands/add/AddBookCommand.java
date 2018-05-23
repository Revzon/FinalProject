package java_external.commands.add;

import java_external.db.dao.*;
import java_external.db.dto.*;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddBookCommand implements java_external.commands.Command {


    private final String AUTHOR_LIST_ATTR = "authorList";
    private final String PUBLISHMENT_LIST_ATTR = "publishmentList";
    private final String GENRE_LIST_ATTR = "genreList";
    private final String SHELF_LIST_ATTR = "shelfList";
    private final String KEYWORD_LIST_ATTR = "keywordList";


    @Override
    public String execute(HttpServletRequest request) {

        AuthorDAO authorDAO = AuthorDAO.getInstance();
        List<Author> authorList = authorDAO.findAll();
        request.setAttribute(AUTHOR_LIST_ATTR, authorList);

        PublishmentDAO publishmentDAO = PublishmentDAO.getInstance();
        List<Publishment> publishmentList = publishmentDAO.findAll();
        request.setAttribute(PUBLISHMENT_LIST_ATTR, publishmentList);

        GenreDAO genreDAO = GenreDAO.getInstance();
        List<Genre> genreList = genreDAO.findAll();
        request.setAttribute(GENRE_LIST_ATTR, genreList);

        ShelfDAO shelfDAO = ShelfDAO.getInstance();
        List<Shelf> shelfList = shelfDAO.findAll();
        request.setAttribute(SHELF_LIST_ATTR, shelfList);

        KeywordDAO keywordDAO = KeywordDAO.getInstance();
        List<Keyword> keywordList = keywordDAO.findAll();
        request.setAttribute(KEYWORD_LIST_ATTR, keywordList);


        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.ADD_BOOK_PAGE_PATH);

        return page;
    }
}
