package java_external.commands.edit;

import com.mysql.jdbc.StringUtils;
import java_external.commands.Command;
import java_external.commands.navigation.UserpageCommand;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.UserAuthService;
import java_external.services.authentification.AuthentificationManager;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


public class EditUserSubmitCommand implements Command {
    private final static String ATTR_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request) {

        String idString = request.getParameter(ATTR_NAME_ID);
        if (StringUtils.isNullOrEmpty(idString)) {
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);
        }
        int id = Integer.parseInt(idString);
        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.findById(id);

        if (Objects.isNull(user)) {
            return ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.ERROR_PAGE_PATH);
        }

        UserAuthService manager = UserAuthService.getInstance();
        User editedUser = manager.fillUser(request);

        if (!user.equals(editedUser)) {
            editedUser.setId(id);
            userDAO.updateChangableData(editedUser);
            User currentUser = (User) request.getSession().getAttribute("user");
            if (currentUser.getId() == id){
                request.getSession().setAttribute("user", userDAO.findById(id));
            }

        }
        request.setAttribute(ATTR_NAME_ID, id);

        return new UserpageCommand().execute(request);
    }
}
