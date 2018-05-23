package java_external.commands.add;

import java_external.commands.Command;
import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.*;
import java_external.db.dto.Book;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddBookSubmitCommand implements Command {

    private static final String BOOK_TITLE_ATTR = "title";
    private static final String BOOK_AUTHORS_ATTR = "authors";
    private static final String BOOK_PUBLISHMENT_ATTR = "publishment";
    private static final String BOOK_GENRE_ATTR = "genre";
    private static final String BOOK_KEYWORDS_ATTR = "keywords";
    private static final String BOOK_LOCATION_ATTR = "location";

    @Override
    public String execute(HttpServletRequest request) {
        Book book = parseAndGetBook(request);
        String page = "";
        if (Objects.nonNull(book)) {
            BookDAO bookDAO = BookDAO.getInstance();
            bookDAO.insert(book);
            page = new AddBookCommand().execute(request);
        } else {
            request.setAttribute("errorMessage", "Something went wrong. Failed to add book");
            page = new SearchpageCommand().execute(request);
        }
        return page;
    }

    private Book parseAndGetBook(HttpServletRequest request) {
        String title = request.getParameter(BOOK_TITLE_ATTR);
        String[] authorsIdString = request.getParameterValues(BOOK_AUTHORS_ATTR);
        int publishmentId = Integer.parseInt(request.getParameter(BOOK_PUBLISHMENT_ATTR));
        int genreId = Integer.parseInt(request.getParameter(BOOK_GENRE_ATTR));
        String[] keywordsIdString = request.getParameterValues(BOOK_KEYWORDS_ATTR);
        int locationId = Integer.parseInt(request.getParameter(BOOK_LOCATION_ATTR));

        Book newBook = new Book();
        newBook.setTitle(title);

        newBook.setGenre(GenreDAO.getInstance().findById(genreId));
        newBook.setLocation(ShelfDAO.getInstance().findById(locationId));
        newBook.setAuthors(AuthorDAO.getInstance().findByIdArray(authorsIdString));
        newBook.setPublishment(PublishmentDAO.getInstance().findById(publishmentId));
        newBook.setKeywords(KeywordDAO.getInstance().findByIdArray(keywordsIdString));
        return newBook;
    }


}
