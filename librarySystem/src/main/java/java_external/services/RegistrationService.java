package java_external.services;

import java_external.services.authentification.AuthentificationManager;
import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.utils.Md5;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olga on 16.05.18.
 */
public class RegistrationService {
    private static final String USERNAME_HEADER = "username";
    private static final String FIRSTNAME_HEADER = "firstName";
    private static final String SECONDNAME_HEADER = "secondName";
    private static final String PATRONIMYCNAME_HEADER = "patronimycName";
    private static final String EMAIL_HEADER = "email";
    private static final String PHONE_HEADER = "phone";
    private static final String PASSWORD_HEADER = "password";
    private static final String BIRTHDAY_HEADER = "birthday";

    private static RegistrationService instance;

    public static RegistrationService getInstance() {
        if (instance == null) {
            instance = new RegistrationService();
        }
        return instance;
    }


    public User registrateUser(HttpServletRequest request) {
        AuthentificationManager authentificationManager = AuthentificationManager.getInstance();
        String username = request.getParameter(USERNAME_HEADER);
        String email = request.getParameter(EMAIL_HEADER);
        String phone = request.getParameter(PHONE_HEADER);
        User user = null;
        if (authentificationManager.checkUserIsNew(username, email, phone)) {
            UserDAO userDAO = UserDAO.getInstance();
            user = fillUser(request);
            userDAO.insert(user);
        }
        return user;
    }





    private User fillUser(HttpServletRequest request) {

        String username = request.getParameter(USERNAME_HEADER);
        String email = request.getParameter(EMAIL_HEADER);
        String phone = request.getParameter(PHONE_HEADER);
        String firstName = request.getParameter(FIRSTNAME_HEADER);
        String secondName = request.getParameter(SECONDNAME_HEADER);
        String patronimycName = request.getParameter(PATRONIMYCNAME_HEADER);
        String birthday = request.getParameter(BIRTHDAY_HEADER);
        String passwordRaw = request.getParameter(PASSWORD_HEADER);
        String passwordHashed = Md5.md5Password(passwordRaw);

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setSecondName(secondName);
        newUser.setPatronimycName(patronimycName);
        newUser.setLogin(username);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(passwordHashed);
//        newUser.setBirthDate(birthday);

        return newUser;
    }
}
