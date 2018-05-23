package java_external.commands.delete;

import java_external.commands.Command;
import java_external.commands.list.UserListCommand;
import java_external.db.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;




public class DeleteUserCommand implements Command {

    private final static String ATTR_NAME_INFO = "infoMessage";
    @Override
    public String execute(HttpServletRequest request) {
        String userIdString = request.getParameter("id");
        String infoMessage = "";
        if (userIdString == "") {
            infoMessage = "User wasn't found!";
        } else {
            int userId = Integer.parseInt(userIdString);
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.delete(userId);
            infoMessage = "User deleted successfully";
//            ;
        }
        request.getSession().setAttribute(ATTR_NAME_INFO, infoMessage);
        String page = new UserListCommand().execute(request);
        return page;
        // CHECK THE ALGORITHM! ADD LOGGS
    }
}
