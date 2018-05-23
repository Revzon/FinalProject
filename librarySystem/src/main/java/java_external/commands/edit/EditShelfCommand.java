package java_external.commands.edit;

import java_external.commands.list.ShelfListCommand;
import java_external.db.dao.ShelfDAO;
import java_external.db.dto.Shelf;

import javax.servlet.http.HttpServletRequest;




public class EditShelfCommand implements java_external.commands.Command {

    private final static String ATTR_NAME_ID = "id";
    private final static String ATTR_NAME_SHELF_TO_EDIT = "shelfToEdit";


    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(ATTR_NAME_ID));
        ShelfDAO shelfDAO = ShelfDAO.getInstance();
        Shelf shelf = shelfDAO.findById(id);
        request.setAttribute(ATTR_NAME_SHELF_TO_EDIT, shelf);
        return new ShelfListCommand().execute(request);
    }
}
