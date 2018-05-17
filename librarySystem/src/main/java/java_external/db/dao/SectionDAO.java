package java_external.db.dao;

import java_external.db.dto.Section;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class SectionDAO extends AbstractDAO{

    private static final String ADD_SECTION_QUERY = "INSERT INTO section(name) VALUES(?)";
    private static final String UPDATE_SECTION_QUERY = "UPDATE section SET word = ? WHERE id = ?";
    private static final String DELETE_SECTION_QUERY = "DELETE section WHERE id = ?";
    private static final String FIND_SECTION_BY_ID = "SELECT * FROM section WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM section";

    private final static String SECTION_ID = "id";
    private final static String SECTION_NAME = "name";


    private static SectionDAO sectionDAO;

    public static SectionDAO getInstance() {
        if (sectionDAO == null) {
            sectionDAO = new SectionDAO();
        }
        return sectionDAO;
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

    public Section findSectionById(int id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_SECTION_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Section section = null;
        try {
            while (resultSet.next()) {
                section = fillInSection(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at section findSectionById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return section;
    }

    public List<Section> findAll() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        List sectionList = new ArrayList<Section>();
        try {
            while (resultSet.next()) {

                Section section = fillInSection(resultSet);
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
            section = new Section(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }



}
