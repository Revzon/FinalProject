package java_external.services.authentification;

import java_external.db.dao.UserDAO;
import java_external.db.dto.User;
import java_external.services.utils.Md5;

import java.util.Objects;

/**
 * Created by olga on 13.05.18.
 */
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

    public User checkUser(String userName, String insertedPassword) {
        User user = getUserByLogin(userName);
        if (Objects.isNull(user)) {
            return user;
        }
        String savedPassword = user.getPassword();
        if (insertedPassword == Md5.md5Password(savedPassword)) {
            return user;
        } else {
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

    public boolean checkUserIsNew(String username, String email, String phone) {
        if (!Objects.isNull(getUserByLogin(username))) {
            return false;
            // add info error
        }


        if (!Objects.isNull(getUserByEmail(email))) {
            return false;
            // add info error
        }


        if (!Objects.isNull(getUserByPhone(phone))) {
            return false;
            // add info error
        }

        return true;
    }
}
