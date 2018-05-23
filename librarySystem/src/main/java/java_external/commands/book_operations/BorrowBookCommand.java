package java_external.commands.book_operations;

import java_external.commands.Command;
import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;
import java_external.db.dto.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

//import java_external.commands.navigation.BookListCommand;

public class BorrowBookCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

        String idString = request.getParameter("id");
        String infoMessage = "";
        if (StringUtils.isBlank(idString)) {
            infoMessage = "Book wasn't found!";
        } else {
            int bookId = Integer.parseInt(idString);
            // ADD EXCEPTIONS MESSAGES IF BOOK ISN'T IN DB OR TAKEN
            BookDAO bookDAO = BookDAO.getInstance();
            Book book = bookDAO.findById(bookId);
            if (!book.isAvilable()) {

            } else {
                book.setAvilable(false);
                User currentUser = (User) request.getSession().getAttribute("user");
                book.setReader(currentUser);
                bookDAO.updateBookState(book);
                infoMessage = "Book borrowed successfully";
            }
        }
        return new SearchpageCommand().execute(request);
    }
}
