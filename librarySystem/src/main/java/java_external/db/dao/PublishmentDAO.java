package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.QueryManager;
import java_external.db.dto.Publishment;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class PublishmentDAO extends AbstractDAO implements CRUD<Publishment> {

    private static final String ADD_PUBLISHMENT_QUERY = "INSERT INTO publishment(name) VALUES(?)";
    private static final String UPDATE_PUBLISHMENT_QUERY = "UPDATE publishment SET name = ? WHERE id = ?";
    private static final String DELETE_PUBLISHMENT_QUERY = "DELETE FROM publishment WHERE id = ?";
    private static final String FIND_PUBLISHMENT_BY_ID = "SELECT * FROM publishment WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM publishment";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM publishment LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM publishment";

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
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Publishment publishment) {
        executeQuery(ADD_PUBLISHMENT_QUERY, preparedStatement -> {
            preparedStatement.setString(1, publishment.getName());
        });
    }

    public void update(Publishment publishment) {
        executeQuery(UPDATE_PUBLISHMENT_QUERY, preparedStatement -> {
            preparedStatement.setString(1, publishment.getName());
            preparedStatement.setInt(2, publishment.getId());
            preparedStatement.executeUpdate();
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_PUBLISHMENT_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
        });
    }

    public Publishment findById(int id) {
        return executeQuery(FIND_PUBLISHMENT_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                this::getPublishmentFromRs);
    }


    public List<Publishment> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE,
                preparedStatement -> preparedStatement.setInt(1, offset),
                this::getPublishmentsFromRs);
    }


    public List<Publishment> findAll() {
        return executeQuery(FIND_ALL, this::getPublishmentsFromRs);
    }

    private Publishment processRs(ResultSet resultSet) throws SQLException {
        Publishment publishment = null;
        int id = resultSet.getInt(PUBLISHMENT_ID);
        String name = resultSet.getString(PUBLISHMENT_NAME);
        publishment = new Publishment(name);
        publishment.setId(id);
        return publishment;
    }

    public Publishment getPublishmentFromRs(ResultSet resultSet) throws SQLException {
        Publishment publishment = null;
        while (resultSet.next()) {
            publishment = processRs(resultSet);
        }
        return publishment;
    }

    public List<Publishment> getPublishmentsFromRs(ResultSet resultSet) throws SQLException {
        List<Publishment> keyWordList = new ArrayList<>();
        Publishment publishment = null;
        while (resultSet.next()) {
            publishment = processRs(resultSet);
            if (publishment != null) {
                keyWordList.add(publishment);
            }
        }
        return keyWordList;
    }

}
