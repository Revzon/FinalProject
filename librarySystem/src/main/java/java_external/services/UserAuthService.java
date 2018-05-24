package java_external.services;

import java_external.enums.Role;
import java_external.services.authentification.AuthentificationManager;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.utils.Md5;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class UserAuthService {
    private static final String USERNAME_HEADER = "username";
    private static final String FIRSTNAME_HEADER = "firstName";
    private static final String SECONDNAME_HEADER = "secondName";
    private static final String PATRONIMYCNAME_HEADER = "patronymicName";
    private static final String EMAIL_HEADER = "email";
    private static final String PHONE_HEADER = "phone";
    private static final String PASSWORD_HEADER = "password";
    private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";

    private static UserAuthService instance;

    public static UserAuthService getInstance() {
        if (instance == null) {
            instance = new UserAuthService();
        }
        return instance;
    }


    public User registrateUser(HttpServletRequest request) {
        AuthentificationManager authentificationManager = AuthentificationManager.getInstance();
        String username = request.getParameter(USERNAME_HEADER);
        String email = request.getParameter(EMAIL_HEADER);
        String phone = request.getParameter(PHONE_HEADER);
        User user = null;
        String errorMessage = "";
        errorMessage = authentificationManager.checkUserIsNew(username, email, phone);
        request.setAttribute(ATTR_NAME_ERROR_MESSAGE, errorMessage);

        if (errorMessage != "") {
            UserDAO userDAO = UserDAO.getInstance();
            user = fillUser(request);
            userDAO.insert(user);
        }
        return user;
    }



    public boolean checkRightsToEditPersonalPage(HttpServletRequest request){
        User currentUser = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(currentUser)) {
            return false;
        }
        String idString = request.getParameter("id");
        if (StringUtils.isBlank(idString)) {
            return false;
        }
        else {
            int userId = Integer.parseInt(idString);
            if (currentUser.getRole().equals(Role.ADMIN) || (currentUser.getId() == userId)) {
                return true;
            } else {
                return false;
            }
        }
    }


    public User fillUser(HttpServletRequest request) {
        User newUser = new User();

        String username = request.getParameter(USERNAME_HEADER);
        String email = request.getParameter(EMAIL_HEADER);
        String phone = request.getParameter(PHONE_HEADER);
        String firstName = request.getParameter(FIRSTNAME_HEADER);
        String secondName = request.getParameter(SECONDNAME_HEADER);
        String patronymicName = request.getParameter(PATRONIMYCNAME_HEADER);
        String passwordRaw = request.getParameter(PASSWORD_HEADER);
        if (StringUtils.isNoneBlank(passwordRaw)) {
            String passwordHashed = Md5.md5Password(passwordRaw);
            newUser.setPassword(passwordHashed);
        }
        newUser.setFirstName(firstName);
        newUser.setSecondName(secondName);
        newUser.setPatronymicName(patronymicName);
        newUser.setLogin(username);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setRole(Role.READER);
        return newUser;
    }
}
