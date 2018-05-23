package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.navigation.SearchpageCommand;
import java_external.db.dao.BookDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteBookCommand implements Command {
    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String bookIdString = request.getParameter("id");
        String infoMessage = "";
        if (bookIdString == "") {
            infoMessage = "Book wasn't found!";
        } else {
            int bookId = Integer.parseInt(bookIdString);
            BookDAO bookDAO = BookDAO.getInstance();
            bookDAO.delete(bookId);
            infoMessage = "Book deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new SearchpageCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
