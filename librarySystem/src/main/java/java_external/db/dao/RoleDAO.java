//package java_external.db.dao;
//
//import java_external.enums.Role;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by olga on 14.05.18.
// */
//public class RoleDAO extends AbstractDAO{
//
//    private static final String ADD_ROLE_QUERY = "INSERT INTO role(name) VALUES(?)";
//    private static final String UPDATE_ROLE_QUERY = "UPDATE role SET word = ? WHERE id = ?";
//    private static final String DELETE_ROLE_QUERY = "DELETE role WHERE id = ?";
//    private static final String FIND_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
//    private static final String FIND_ALL = "SELECT * FROM role";
//
//    private final static String ROLE_ID = "id";
//    private final static String ROLE_NAME = "name";
//
//
//    private static RoleDAO roleDAO;
//
//    public static RoleDAO getInstance() {
//        if (roleDAO == null) {
//            roleDAO = new RoleDAO();
//        }
//        return roleDAO;
//    }
//
//    public void insert(Role role) {
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(ADD_ROLE_QUERY);
//            preparedStatement.setString(1, role.getName());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            //            log.warn("SQLException at role insert()", e);
//        } finally {
//            closeConnection(null, preparedStatement);
//        }
//    }
//
//    public void update(Role role) {
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(UPDATE_ROLE_QUERY);
//            preparedStatement.setString(1, role.getName());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
////            log.warn("SQLException at role update()", e);
//        } finally {
//            closeConnection(null, preparedStatement);
//        }
//    }
//
//    public void delete(int id) {
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(DELETE_ROLE_QUERY);
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
////            log.warn("SQLException at role delete()", e);
//        } finally {
//            closeConnection(null, preparedStatement);
//
//        }
//    }
//
//    public Role findRoleById(int id) throws SQLException {
//        connection = getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROLE_BY_ID);
//        preparedStatement.setInt(1, id);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        Role role = null;
//        try {
//            while (resultSet.next()) {
//                role = fillInRole(resultSet);
//            }
//        } catch (SQLException e) {
////            log.warn("SQLException at role findRoleById()", e);
//        } finally {
//            closeConnection(resultSet, preparedStatement);
//        }
//        return role;
//    }
//
//    public List<Role> findAll() throws SQLException {
//        connection = getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List roleList = new ArrayList<Role>();
//        try {
//            while (resultSet.next()) {
//
//                Role role = fillInRole(resultSet);
//                if (role != null) {
//                    roleList.add(role);
//                }
//            }
//        } catch (SQLException e) {
////            log.warn("SQLException at role findAll()", e);
//        } finally {
//            closeConnection(resultSet, preparedStatement);
//
//        }
//        return roleList;
//    }
//
//
//    private Role fillInRole(ResultSet resultSet) {
//        Role role = null;
//        try {
//            int id = resultSet.getInt(ROLE_ID);
//            String name = resultSet.getString(ROLE_NAME);
//            role = new Role(id, name);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return role;
//    }
//
//
//
//}
