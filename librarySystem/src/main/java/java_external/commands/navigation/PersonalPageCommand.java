package java_external.commands.navigation;

import java_external.commands.Command;
import java_external.db.dao.BookDAO;
import java_external.db.dto.Book;
import java_external.db.dto.User;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PersonalPageCommand implements Command {
    private static final String ID_ATTR = "id";

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        if (Objects.isNull(user)) {
            return  ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
        }

        request.setAttribute(ID_ATTR, user.getId());
        return new UserpageCommand().execute(request);
    }

}
