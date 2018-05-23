package java_external.services.authentification;

import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.utils.Md5;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;




public class AuthentificationManager {

    private static AuthentificationManager instance;

    private UserDAO userDAO;

    public static AuthentificationManager getInstance(){
        if (instance == null) {
            instance = new AuthentificationManager();
        }
        return instance;
    }

    private AuthentificationManager() {
        this.userDAO = UserDAO.getInstance();
    }

    public User checkUser(String userName, String insertedPassword, HttpServletRequest request) {
        User user = getUserByLogin(userName);
        String ATTR_NAME_ERROR_MESSAGE = "errorMessage";
        if (Objects.isNull(user)) {
            request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "No user found");
            return user;
        }
        String savedPassword = user.getPassword();

        if (savedPassword.equals(Md5.md5Password(insertedPassword))) {
//            if (savedPassword.equals(insertedPassword)) {
                return user;
        } else {
            request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "Wrong password");
            return null;
        }

    }

    public User getUserByLogin(String userName){
        User user = userDAO.findByLogin(userName);
        return user;
    }


    public User getUserByPhone(String phone) {
        User user = userDAO.findByPhone(phone);
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return user;
    }

    public String checkUserIsNew(String username, String email, String phone) {
        String errorMessage = "";

        if (!Objects.nonNull(getUserByLogin(username))) {
            errorMessage = "Username already exists!";
            // add info error
        }


        if (!Objects.nonNull(getUserByEmail(email))) {
            errorMessage = "Email already exists!";
            // add info error
        }


        if (!Objects.nonNull(getUserByPhone(phone))) {
            errorMessage = "Phone already exists!";
            // add info error
        }

        return errorMessage;
    }
}
