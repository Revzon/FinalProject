package java_external.db.dao;

import java_external.db.dto.Author;
import java_external.db.dto.Section;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class SectionDAO extends AbstractDAO implements CRUD<Section> {

    private static final String ADD_SECTION_QUERY = "INSERT INTO section(name) VALUES(?)";
    private static final String UPDATE_SECTION_QUERY = "UPDATE section SET name = ? WHERE id = ?";
    private static final String DELETE_SECTION_QUERY = "DELETE FROM section WHERE id = ?";
    private static final String FIND_SECTION_BY_ID = "SELECT * FROM section WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM section";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM section LIMIT 10 OFFSET ?";
    private static final String FIND_BY_NAMEPART = "SELECT * FROM section WHERE name LIKE ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM section";


    private final static String SECTION_ID = "id";
    private final static String SECTION_NAME = "name";


    private static SectionDAO sectionDAO;

    public static SectionDAO getInstance() {
        if (sectionDAO == null) {
            sectionDAO = new SectionDAO();
        }
        return sectionDAO;
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

    public void insert(Section section) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_SECTION_QUERY);
            preparedStatement.setString(1, section.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at section insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Section section) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SECTION_QUERY);
            preparedStatement.setString(1, section.getName());
            preparedStatement.setInt(2, section.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at section update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SECTION_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at section delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }


    public Section findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Section section = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_SECTION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                section = fillInSection(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at section findById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return section;
    }

    public List<Section> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Section section = null;
        List<Section> sectionList = new ArrayList<Section>();

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

                section = fillInSection(resultSet);
                if (section != null) {
                    sectionList.add(section);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at section findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return sectionList;
    }


    private Section fillInSection(ResultSet resultSet) {
        Section section = null;
        try {
            int id = resultSet.getInt(SECTION_ID);
            String name = resultSet.getString(SECTION_NAME);
            section = new Section(name);
            section.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }


    public List<Section> findByNamepart(String namepart) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Section> sections = new ArrayList<Section>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_NAMEPART);
            preparedStatement.setString(1, namepart);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Section section = fillInSection(resultSet);
                if (section != null) {
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at author findAuthorsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return sections;
    }
}