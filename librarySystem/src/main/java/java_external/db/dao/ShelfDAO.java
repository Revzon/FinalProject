package java_external.db.dao;

import java_external.db.dto.Shelf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class ShelfDAO extends AbstractDAO{

    private static final String ADD_SHELF_QUERY = "INSERT INTO shelf(name, section_id) VALUES(?, ?)";
    private static final String UPDATE_SHELF_QUERY = "UPDATE shelf SET name = ? WHERE id = ?";
    private static final String DELETE_SHELF_QUERY = "DELETE shelf WHERE id = ?";
    private static final String FIND_SHELF_BY_ID = "SELECT * FROM shelf WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM shelf";
    private static final String FIND_ALL_BY_SECTION_ID = "SELECT * FROM shelf WHERE section_id = ?";

    private final static String SHELF_ID = "id";
    private final static String SHELF_NAME = "name";
    private final static String SHELF_SECTION_ID = "section_id";

    private static ShelfDAO shelfDAO;

    public static ShelfDAO getInstance() {
        if (shelfDAO == null) {
            shelfDAO = new ShelfDAO();
        }
        return shelfDAO;
    }
    public void insert(Shelf shelf) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_SHELF_QUERY);
            preparedStatement.setInt(1, shelf.getName());
            preparedStatement.setInt(2, shelf.getSection().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at shelf insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Shelf shelf) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SHELF_QUERY);
            preparedStatement.setInt(1, shelf.getName());
            preparedStatement.setInt(2, shelf.getSection().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at shelf update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SHELF_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at shelf delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public Shelf findShelfById(int id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_SHELF_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Shelf shelf = null;
        try {
            while (resultSet.next()) {
                shelf = fillInShelf(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at shelf findShelfById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return shelf;
    }

    public List<Shelf> findAll() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        List shelfList = new ArrayList<Shelf>();
        try {
            while (resultSet.next()) {

                Shelf shelf = fillInShelf(resultSet);
                if (shelf != null) {
                    shelfList.add(shelf);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at shelf findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return shelfList;
    }


    public List<Shelf> findAllBySectionId(int sectionId) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_SECTION_ID);
        preparedStatement.setInt(1, sectionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List shelfList = new ArrayList<Shelf>();
        try {
            while (resultSet.next()) {

                Shelf shelf = fillInShelf(resultSet);
                if (shelf != null) {
                    shelfList.add(shelf);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at shelf findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return shelfList;
    }


    private Shelf fillInShelf(ResultSet resultSet) {
        Shelf shelf = null;
        try {
            int id = resultSet.getInt(SHELF_ID);
            int sectionId = resultSet.getInt(SHELF_SECTION_ID);
            int name = resultSet.getInt(SHELF_NAME);
//            shelf = new Shelf(id, name, sectionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelf;
    }


}
