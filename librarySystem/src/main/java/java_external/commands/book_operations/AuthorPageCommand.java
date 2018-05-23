package java_external.commands.book_operations;

import java_external.db.dao.AuthorDAO;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Author;
import java_external.db.dto.Book;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AuthorPageCommand implements java_external.commands.Command {

    @Override
    public String execute(HttpServletRequest request) {

        String authorIdString = request.getParameter("id");
        int authorId = Integer.parseInt(authorIdString);

        AuthorDAO authorDAO = AuthorDAO.getInstance();
        Author author = authorDAO.findById(authorId);
        request.setAttribute("author", author);


        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> books = bookDAO.findBooksByAuthorId(authorId);
        request.setAttribute("books", books);


        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.AUTHOR_PAGE_PATH);
        return page;
    }

}
