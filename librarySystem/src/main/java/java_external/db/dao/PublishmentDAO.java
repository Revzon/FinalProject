package java_external.db.dao;

import java_external.db.dto.Publishment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class PublishmentDAO extends AbstractDAO{

    private static final String ADD_PUBLISHMENT_QUERY = "INSERT INTO publishment(name) VALUES(?)";
    private static final String UPDATE_PUBLISHMENT_QUERY = "UPDATE publishment SET name = ? WHERE id = ?";
    private static final String DELETE_PUBLISHMENT_QUERY = "DELETE publishment WHERE id = ?";
    private static final String FIND_PUBLISHMENT_BY_ID = "SELECT * FROM publishment WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM publishment";

    private final static String PUBLISHMENT_ID = "id";
    private final static String PUBLISHMENT_NAME = "name";


    private static PublishmentDAO publishmentDAO;

    public static PublishmentDAO getInstance() {
        if (publishmentDAO == null) {
            publishmentDAO = new PublishmentDAO();
        }
        return publishmentDAO;
    }

    public void insert(Publishment publishment) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_PUBLISHMENT_QUERY);
            preparedStatement.setString(1, publishment.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at publishment insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Publishment publishment) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PUBLISHMENT_QUERY);
            preparedStatement.setString(1, publishment.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at publishment update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_PUBLISHMENT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at publishment delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public Publishment findPublishmentById(int id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_PUBLISHMENT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Publishment publishment = null;
        try {
            while (resultSet.next()) {
                publishment = fillInPublishment(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at publishment findPublishmentById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return publishment;
    }

    public List<Publishment> findAll() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        List publishmentList = new ArrayList<Publishment>();
        try {
            while (resultSet.next()) {

                Publishment publishment = fillInPublishment(resultSet);
                if (publishment != null) {
                    publishmentList.add(publishment);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at publishment findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return publishmentList;
    }


    private Publishment fillInPublishment(ResultSet resultSet) {
        Publishment publishment = null;
        try {
            int id = resultSet.getInt(PUBLISHMENT_ID);
            String name = resultSet.getString(PUBLISHMENT_NAME);
            publishment = new Publishment(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishment;
    }



}
