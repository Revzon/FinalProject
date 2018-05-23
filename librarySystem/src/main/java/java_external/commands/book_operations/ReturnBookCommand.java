package java_external.commands.book_operations;

import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ReturnBookCommand implements java_external.commands.Command {


    @Override
    public String execute(HttpServletRequest request) {

        BookDAO bookDAO = BookDAO.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(id);

        if (Objects.nonNull(book)) {
            book.setReader(null);
            book.setDateOfReturn(null);
            book.setAvilable(true);
            bookDAO.updateBookState(book);
        }

        return new SearchpageCommand().execute(request);
    }
}
