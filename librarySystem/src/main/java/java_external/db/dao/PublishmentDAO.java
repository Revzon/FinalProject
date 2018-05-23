package java_external.db.dao;

import java_external.db.dto.Publishment;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;




public class PublishmentDAO extends AbstractDAO implements CRUD<Publishment> {

    private static final String ADD_PUBLISHMENT_QUERY = "INSERT INTO publishment(name) VALUES(?)";
    private static final String UPDATE_PUBLISHMENT_QUERY = "UPDATE publishment SET name = ? WHERE id = ?";
    private static final String DELETE_PUBLISHMENT_QUERY = "DELETE FROM publishment WHERE id = ?";
    private static final String FIND_PUBLISHMENT_BY_ID = "SELECT * FROM publishment WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM publishment";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM publishment LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM publishment";


    private static final String FIND_BY_NAMEPART = "SELECT * FROM publishment WHERE name LIKE ?";

    private final static String PUBLISHMENT_ID = "id";
    private final static String PUBLISHMENT_NAME = "name";


    private static PublishmentDAO publishmentDAO;

    public static PublishmentDAO getInstance() {
        if (publishmentDAO == null) {
            publishmentDAO = new PublishmentDAO();
        }
        return publishmentDAO;
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

    public void insert(Publishment publishment) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_PUBLISHMENT_QUERY);
            preparedStatement.setString(1, publishment.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
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
            preparedStatement.setInt(2, publishment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
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
            throw new DAOException(e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public Publishment findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Publishment publishment = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_PUBLISHMENT_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                publishment = fillInPublishment(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return publishment;
    }

    public List<Publishment> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Publishment> publishmentList = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
            preparedStatement.setInt(1, offset);
            resultSet = preparedStatement.executeQuery();
            publishmentList = new ArrayList<Publishment>();
            while (resultSet.next()) {

                Publishment publishment = fillInPublishment(resultSet);
                if (publishment != null) {
                    publishmentList.add(publishment);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return publishmentList;
    }


    public List<Publishment> findAll() {
        Function<ResultSet, List<Publishment>> rsFunction = resultSet -> {
            try {
                List<Publishment> publishmentList = new ArrayList<>();
                while (resultSet.next()) {
                    Publishment publishment = fillInPublishment(resultSet);
                    if (publishment != null) {
                        publishmentList.add(publishment);
                    }
                }
                return publishmentList;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        };
        return QueryManager.executeQuery(rsFunction, FIND_ALL);
    }


    public List<Publishment> findByNamepart(String namepart) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Publishment> publishments = new ArrayList<Publishment>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_NAMEPART);
            preparedStatement.setString(1, namepart);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Publishment publishment = fillInPublishment(resultSet);
                if (publishment != null) {
                    publishments.add(publishment);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return publishments;
    }


    private Publishment fillInPublishment(ResultSet resultSet) {
        Publishment publishment = null;
        try {
            int id = resultSet.getInt(PUBLISHMENT_ID);
            String name = resultSet.getString(PUBLISHMENT_NAME);
            publishment = new Publishment(name);
            publishment.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishment;
    }


}
