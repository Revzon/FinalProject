package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.FunctionRT;
import java_external.db.dto.User;
import java_external.enums.Role;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class UserDAO extends AbstractDAO implements CRUD<User> {

    private static final String ADD_USER_QUERY = "INSERT INTO user(first_name, second_name, patronymic_name, login, email, phone, " +
            "password, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET first_name = ?, second_name = ?, patronymic_name = ?, " +
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
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(User user) {
        executeQuery(ADD_USER_QUERY, preparedStatement -> {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassword());
        });
    }

    public void update(User user) {
        executeQuery(UPDATE_USER_QUERY, preparedStatement -> {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getRole().getId());
            preparedStatement.setInt(9, user.getId());
        });
    }

    public void updateRole(int id, int roleId) {
        executeQuery(UPDATE_USER_ROLE_QUERY, preparedStatement -> {
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(1, roleId);
        });
    }

    public void updatePassword(User user) {

        executeQuery(UPDATE_USER_PASSWORD_QUERY, preparedStatement -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
        });
    }

    public void updateChangableData(User user) {
        executeQuery(UPDATE_USER_CHANGABLE_DATA_QUERY, preparedStatement -> {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setString(3, user.getPatronymicName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setInt(6, user.getId());
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_USER_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        });
    }

    public User findById(int id) {
        return executeQuery(
                FIND_USER_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                getUserHandler());
    }

    public User findByEmail(String email) {
        return executeQuery(
                FIND_USER_BY_EMAIL,
                preparedStatement -> preparedStatement.setString(1, email),
                getUserHandler());
    }

    public User findByPhone(String phone) {
        return executeQuery(
                FIND_USER_BY_PHONE,
                preparedStatement -> preparedStatement.setString(1, phone),
                getUserHandler());
    }

    public User findByLogin(String login) {
        return executeQuery(
                FIND_USER_BY_LOGIN,
                preparedStatement -> preparedStatement.setString(1, login),
                getUserHandler());
    }

    public List<User> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE, preparedStatement -> preparedStatement.setInt(1, offset), getUserResultSetHandler());
    }

    public List<User> findAll() {
        return executeQuery(FIND_ALL, getUserResultSetHandler());
    }

    private FunctionRT<ResultSet, List<User>> getUserResultSetHandler() {
        return (resultSet) -> {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = processRs(resultSet);
                if (user != null) {
                    userList.add(user);
                }
            }
            return userList;
        };
    }

    private FunctionRT<ResultSet, User> getUserHandler() {
        return (resultSet) -> {
            if (resultSet.next()) {
                return processRs(resultSet);
            }
            return null;
        };
    }

    private User processRs(ResultSet resultSet) throws SQLException {
        User user = new User();
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

        return user;
    }

}
