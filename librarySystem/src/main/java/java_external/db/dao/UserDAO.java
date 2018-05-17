package java_external.db.dao;

import java_external.db.dto.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class UserDAO extends AbstractDAO {

    private static final String ADD_USER_QUERY = "INSERT INTO user(first_name, second_name, patronimyc_name, login, email, phone, " +
            "password, birth_date, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, NULL, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET first_name = ? second_name = ? patronimyc_name = ? " +
            "email =?  phone = ? birth_date = ? login = ? role_id = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE user WHERE id = ?";

    private static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String FIND_USER_BY_PHONE = "SELECT * FROM user WHERE phohe = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String FIND_ALL = "SELECT * FROM user";
    private static final String FIND_ALL_READERS = "SELECT * FROM user WHERE role_id = 1";
    private static final String FIND_ALL_USERS_BY_ROLE_ID = "SELECT * FROM user WHERE role_id =?";

    private final static String USER_ID = "id";
    private final static String USER_FIRST_NAME = "first_name";
    private final static String USER_SECOND_NAME = "second_name";
    private final static String USER_PATRONIMYC_NAME = "patronimyc_name";
    private final static String USER_EMAIL = "email";
    private final static String USER_LOGIN = "login";
    private final static String USER_PHONE = "phone";
    private final static String USER_PASSWORD = "password";
    private final static String USER_ROLE_ID = "role_id";
    private final static String USER_BIRTH_DATE = "birth_date";


    private static UserDAO userDAO;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public void insert(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronimycName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassword());
//            preparedStatement.setDate(4, new java.sql.Date(session_commands.getBirthDate()));
            preparedStatement.setInt(9, user.getRole().getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

//            log.warn("SQLException at session_commands insert()", e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null) {
                    cp.closeConnection(connection);
                }
            } catch (SQLException e) {
//                log.warn("SQLException at session_commands insert()", e);
            } catch (Exception e) {
//                log.warn("Exception at session_commands insert()", e);
            }

        }
    }

    public void update(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronimycName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassword());
//            preparedStatement.setDate(8, new java.sql.Date(session_commands.getBirthDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at author update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

//            log.warn("SQLException at session_commands delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public User findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = fillInUser(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return user;
    }

    public User findByEmail(String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = fillInUser(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findByEmail()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return user;
    }

    public User findByPhone(String phone) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                user = fillInUser(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findByPhone()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return user;
    }

    public User findByLogin(String login) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                user = fillInUser(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findByLogin()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return user;
    }

    public List<User> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> userList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();

            userList = new ArrayList<User>();

            while (resultSet.next()) {

                user = fillInUser(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at book findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return userList;
    }

    public List<User> findAllReaders() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> readersList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_READERS);
            resultSet = preparedStatement.executeQuery();

            readersList = new ArrayList<User>();

            while (resultSet.next()) {

                user = fillInUser(resultSet);
                if (user != null) {
                    readersList.add(user);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at session_commands findAllReaders()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return readersList;
    }


    public List<User> findAllByRoleId(int roleId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> readersList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS_BY_ROLE_ID);
            preparedStatement.setInt(1, roleId);
            resultSet = preparedStatement.executeQuery();

            readersList = new ArrayList<User>();

            while (resultSet.next()) {

                user = fillInUser(resultSet);
                if (user != null) {
                    readersList.add(user);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at session_commands findAllReaders()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return readersList;
    }

    private User fillInUser(ResultSet resultSet) {
        User user = new User();

        try {

            int id = resultSet.getInt(USER_ID);

            String firstName = resultSet.getString(USER_FIRST_NAME);
            String secondName = resultSet.getString(USER_SECOND_NAME);
            String patronimycName = resultSet.getString(USER_PATRONIMYC_NAME);
            String login = resultSet.getString(USER_LOGIN);
            String email = resultSet.getString(USER_EMAIL);
            String phone = resultSet.getString(USER_PHONE);
            String password = resultSet.getString(USER_PASSWORD);
            int roleId = resultSet.getInt(USER_ROLE_ID);
            Date birthday = resultSet.getDate(USER_BIRTH_DATE);

            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setPatronimycName(patronimycName);
            user.setLogin(login);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setBirthDate(birthday);
//            session_commands.setRole(roleId);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
