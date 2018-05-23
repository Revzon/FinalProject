package java_external.db.dao;

import java_external.db.dto.User;
import java_external.enums.Role;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO extends AbstractDAO implements CRUD<User> {

    private static final String ADD_USER_QUERY = "INSERT INTO user(first_name, second_name, patronymic_name, login, email, phone, " +
            "password, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_USER_ALL_DATA_QUERY = "UPDATE user SET first_name = ?, second_name = ?, patronymic_name = ?, " +
            "email =?,  phone = ?, login = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String UPDATE_USER_CHANGABLE_DATA_QUERY = "UPDATE user SET first_name = ?, second_name = ?, patronymic_name = ?, " +
            "email =?,  phone = ? WHERE id = ?";
    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE user SET role_id = ? WHERE id = ?";
    private static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE user SET password = ? WHERE id = ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM user";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String FIND_USER_BY_PHONE = "SELECT * FROM user WHERE phohe = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String FIND_ALL = "SELECT * FROM user ORDER BY user.first_name";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM user LIMIT 10 OFFSET ?";

    private final static String USER_ID = "id";
    private final static String USER_FIRST_NAME = "first_name";
    private final static String USER_SECOND_NAME = "second_name";
    private final static String USER_PATRONIMYC_NAME = "patronymic_name";
    private final static String USER_EMAIL = "email";
    private final static String USER_LOGIN = "login";
    private final static String USER_PHONE = "phone";
    private final static String USER_PASSWORD = "password";
    private final static String USER_ROLE_ID = "role_id";


    private static UserDAO userDAO;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public int getCount() {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getCountFromRS(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void insert(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassword());

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
            preparedStatement = connection.prepareStatement(UPDATE_USER_ALL_DATA_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getRole().getId());
            preparedStatement.setInt(9, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at user update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updateChangableData(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_CHANGABLE_DATA_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at user update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updateRole(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE_QUERY);
            preparedStatement.setInt(1, user.getRole().getId());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at user updateRole()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updateRole(int id, int roleId) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE_QUERY);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(1, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at user update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updatePassword(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD_QUERY);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at user updatePassword()", e);
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
            throw new DAOException(e);
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
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return user;
    }

    public List<User> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> userList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
            preparedStatement.setInt(1, offset);
            resultSet = preparedStatement.executeQuery();
            userList = new ArrayList<User>();
            while (resultSet.next()) {
                user = fillInUser(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return userList;
    }

    public List<User> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<User>();
            while (resultSet.next()) {
                user = fillInUser(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
            return userList;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
    }

    private User fillInUser(ResultSet resultSet) {
        User user = new User();

        try {

            int id = resultSet.getInt(USER_ID);
            String firstName = resultSet.getString(USER_FIRST_NAME);
            String secondName = resultSet.getString(USER_SECOND_NAME);
            String patronymicName = resultSet.getString(USER_PATRONIMYC_NAME);
            String login = resultSet.getString(USER_LOGIN);
            String email = resultSet.getString(USER_EMAIL);
            String phone = resultSet.getString(USER_PHONE);
            String password = resultSet.getString(USER_PASSWORD);
            Role role = Role.getRoleByd(resultSet.getInt(USER_ROLE_ID));

            user.setId(id);
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setPatronymicName(patronymicName);
            user.setLogin(login);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setRole(role);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
