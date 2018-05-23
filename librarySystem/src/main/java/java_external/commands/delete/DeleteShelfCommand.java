package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.ShelfListCommand;
import java_external.db.dao.ShelfDAO;

import javax.servlet.http.HttpServletRequest;




public class DeleteShelfCommand implements Command {

    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String shelfIdString = request.getParameter("id");
        String infoMessage = "";
        if (shelfIdString == "") {
            infoMessage = "Shelf wasn't found!";
        } else {
            int shelfId = Integer.parseInt(shelfIdString);
            ShelfDAO shelfDAO = ShelfDAO.getInstance();
            shelfDAO.delete(shelfId);
            infoMessage = "Shelf deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new ShelfListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
