package java_external.db.dao;

import java_external.db.dto.Section;
import java_external.db.dto.Shelf;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;




public class ShelfDAO extends AbstractDAO implements CRUD<Shelf> {

    private static final String ADD_SHELF_QUERY = "INSERT INTO shelf(name, section_id) VALUES(?, ?)";
    private static final String UPDATE_SHELF_QUERY = "UPDATE shelf SET name = ?, section_id = ? WHERE id = ?";
    private static final String DELETE_SHELF_QUERY = "DELETE FROM shelf WHERE id = ?";
    private static final String FIND_SHELF_BY_ID = "SELECT * FROM shelf WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM shelf";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM shelf LIMIT 10 OFFSET ?";
    private static final String FIND_ALL_BY_SECTION_ID = "SELECT * FROM shelf WHERE section_id = ?";
    private static final String FIND_BY_NAMEPART = "SELECT * FROM shelf WHERE name LIKE ?";
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

    public void insert(Shelf shelf) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_SHELF_QUERY);
            preparedStatement.setString(1, shelf.getName());
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
            preparedStatement.setString(1, shelf.getName());
            preparedStatement.setInt(2, shelf.getSection().getId());
            preparedStatement.setInt(3, shelf.getId());

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

    public Shelf findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Shelf shelf = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_SHELF_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shelf = processRs(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at shelf findById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return shelf;
    }

    public List<Shelf> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Shelf shelf = null;
        List<Shelf> shelfList = new ArrayList<Shelf>();

        try {

            connection = getConnection();
            if (offset < 0) {
                // ADD move to constant 0
                preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
                preparedStatement.setInt(1, offset);
            } else {
                preparedStatement = connection.prepareStatement(FIND_ALL);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                shelf = processRs(resultSet);
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

    public List<Shelf> findAll() {
        Function<ResultSet, List<Shelf>> shelResultSetHandler = (resultSet -> {
            try {
                List<Shelf> shelfList = new ArrayList<Shelf>();
                while (resultSet.next()) {
                    Shelf shelf = processRs(resultSet);
                    if (shelf != null) {
                        shelfList.add(shelf);
                    }
                }
                return shelfList;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        });
        return QueryManager.executeQuery(shelResultSetHandler, FIND_ALL);
    }


    public List<Shelf> findAllBySectionId(int sectionId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Shelf shelf = null;
        List<Shelf> shelfList = new ArrayList<Shelf>();

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(FIND_ALL_BY_SECTION_ID);
            preparedStatement.setInt(1, sectionId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                shelf = processRs(resultSet);
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

    public List<Shelf> findByNamepart(String namepart) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Shelf> shelfs = new ArrayList<Shelf>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_NAMEPART);
            preparedStatement.setString(1, namepart);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Shelf shelf = processRs(resultSet);
                if (shelf != null) {
                    shelfs.add(shelf);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return shelfs;
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
