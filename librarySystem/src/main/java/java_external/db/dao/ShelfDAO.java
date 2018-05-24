package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.FunctionRT;
import java_external.db.dao.base.QueryManager;
import java_external.db.dto.Section;
import java_external.db.dto.Shelf;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class ShelfDAO extends AbstractDAO implements CRUD<Shelf> {

    private static final String ADD_SHELF_QUERY = "INSERT INTO shelf(name, section_id) VALUES(?, ?)";
    private static final String UPDATE_SHELF_QUERY = "UPDATE shelf SET name = ?, section_id = ? WHERE id = ?";
    private static final String DELETE_SHELF_QUERY = "DELETE FROM shelf WHERE id = ?";
    private static final String FIND_SHELF_BY_ID = "SELECT * FROM shelf WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM shelf";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM shelf LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM shelf";

    private final static String SHELF_ID = "id";
    private final static String SHELF_NAME = "name";
    private final static String SHELF_SECTION_ID = "section_id";


    private static class Holder {
        private static final ShelfDAO INSTANCE = new ShelfDAO();
    }

    public static ShelfDAO getInstance() {
        return Holder.INSTANCE;
    }

    public int getCount() {
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Shelf shelf) {
        executeQuery(ADD_SHELF_QUERY, preparedStatement -> {
            preparedStatement.setString(1, shelf.getName());
            preparedStatement.setInt(2, shelf.getSection().getId());
        });
    }

    public void update(Shelf shelf) {
        executeQuery(UPDATE_SHELF_QUERY, preparedStatement -> {
            preparedStatement.setString(1, shelf.getName());
            preparedStatement.setInt(2, shelf.getSection().getId());
            preparedStatement.setInt(3, shelf.getId());
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_SHELF_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        });
    }

    public Shelf findById(int id) {
        return executeQuery(
                FIND_SHELF_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                getShelfHandler());
    }

    public List<Shelf> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE, preparedStatement -> preparedStatement.setInt(1, offset),
                getShelfResultSetHandler());
    }

    public List<Shelf> findAll() {
        return executeQuery(FIND_ALL, getShelfResultSetHandler());
    }

    private FunctionRT<ResultSet, Shelf> getShelfHandler() {
        return (resultSet) -> {
            if (resultSet.next()) {
                return processRs(resultSet);
            }
            return null;
        };
    }

     private FunctionRT<ResultSet, List<Shelf>> getShelfResultSetHandler() {
        return (resultSet) -> {
            List<Shelf> shelfList = new ArrayList<>();
            while (resultSet.next()) {
                Shelf shelf = processRs(resultSet);
                if (shelf != null) {
                    shelfList.add(shelf);
                }
            }
            return shelfList;
        };
    }

    private Shelf processRs(ResultSet resultSet) {
        Shelf shelf = null;
        try {
            int id = resultSet.getInt(SHELF_ID);
            int sectionId = resultSet.getInt(SHELF_SECTION_ID);
            String name = resultSet.getString(SHELF_NAME);
            Section section = getSection(sectionId);
            shelf = new Shelf(name, section);
            shelf.setId(id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return shelf;
    }

    private Section getSection(int sectionId) {
        SectionDAO sectionDAO = SectionDAO.getInstance();
        Section section = sectionDAO.findById(sectionId);
        return section;
    }

}
