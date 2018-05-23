package java_external.commands.edit;

import java_external.commands.Command;
import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.*;
import java_external.db.dto.Book;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class EditBookSubmitCommand implements Command {

    private static final String ID = "id";
    private static final String BOOK_TITLE_ATTR = "title";
    private static final String BOOK_AUTHORS_ATTR = "authors";
    private static final String BOOK_PUBLISHMENT_ATTR = "publishment";
    private static final String BOOK_GENRE_ATTR = "genre";
    private static final String BOOK_KEYWORDS_ATTR = "keywords";
    private static final String BOOK_LOCATION_ATTR = "location";
    private static final String BOOK_AVILABLE_ATTR = "avilable";
    private static final String BOOK_READER_ATTR = "reader";
    private static final String BOOK_DATE_OF_RETURN_ATTR = "dateOfReturn";


    private final static String ATTR_NAME_INFO = "infoMessage";

    @Override
    public String execute(HttpServletRequest request) {
        Book book = parseAndGetBook(request);
        if (Objects.nonNull(book)) {
            BookDAO.getInstance().update(book);
        } else {
            request.setAttribute("errorMessage", "Something went wrong. Failed to add book");
        }
//        }
//        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new SearchpageCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }


    // ADD COMBINE ADD BOOK AND EDIT BOOK SIMILAR METHOD!!!
    private Book parseAndGetBook(HttpServletRequest request) {
                int id = Integer.parseInt(request.getParameter(ID));
                Book book = BookDAO.getInstance().findById(id);
        String title = request.getParameter(BOOK_TITLE_ATTR);
        String[] authorsIdString = request.getParameterValues(BOOK_AUTHORS_ATTR);
        int publishmentId = Integer.parseInt(request.getParameter(BOOK_PUBLISHMENT_ATTR));
        int genreId = Integer.parseInt(request.getParameter(BOOK_GENRE_ATTR));
        String[] keywordsIdString = request.getParameterValues(BOOK_KEYWORDS_ATTR);
        int locationId = Integer.parseInt(request.getParameter(BOOK_LOCATION_ATTR));
//        Date dateOfReturn = request.getParameter(BOOK_DATE_OF_RETURN_ATTR);
        boolean avilable = Boolean.getBoolean(request.getParameter(BOOK_AVILABLE_ATTR));
        int readerId = Integer.parseInt(request.getParameter(BOOK_READER_ATTR));


        book.setTitle(title);

        book.setGenre(GenreDAO.getInstance().findById(genreId));
        book.setLocation(ShelfDAO.getInstance().findById(locationId));
        book.setAuthors(AuthorDAO.getInstance().findByIdArray(authorsIdString));
        book.setPublishment(PublishmentDAO.getInstance().findById(publishmentId));
        book.setKeywords(KeywordDAO.getInstance().findByIdArray(keywordsIdString));
        book.setAvilable(avilable);
//        book.setDateOfReturn(dateOfReturn);

        book.setReader(UserDAO.getInstance().findById(readerId));
        return book;
    }

}
