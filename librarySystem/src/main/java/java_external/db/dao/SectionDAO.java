package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.QueryManager;
import java_external.db.dto.Section;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;

public class SectionDAO extends AbstractDAO implements CRUD<Section> {

    private static final String ADD_SECTION_QUERY = "INSERT INTO section(name) VALUES(?)";
    private static final String UPDATE_SECTION_QUERY = "UPDATE section SET name = ? WHERE id = ?";
    private static final String DELETE_SECTION_QUERY = "DELETE FROM section WHERE id = ?";
    private static final String FIND_SECTION_BY_ID = "SELECT * FROM section WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM section";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM section LIMIT 10 OFFSET ?";
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
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Section section) {
        executeQuery(ADD_SECTION_QUERY, preparedStatement -> {
            preparedStatement.setString(1, section.getName());
        });
    }

    public void update(Section section) {
        executeQuery(UPDATE_SECTION_QUERY, preparedStatement -> {
            preparedStatement.setString(1, section.getName());
            preparedStatement.setInt(2, section.getId());
            preparedStatement.executeUpdate();
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_SECTION_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
        });
    }

    public Section findById(int id) {
        return executeQuery(FIND_SECTION_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                this::getSectionFromRs);
    }


    public List<Section> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE,
                preparedStatement -> preparedStatement.setInt(1, offset),
                this::getSectionsFromRs);
    }


    public List<Section> findAll() {
        return executeQuery(FIND_ALL, this::getSectionsFromRs);
    }

    private Section processRs(ResultSet resultSet) throws SQLException {
        Section section = null;
        int id = resultSet.getInt(SECTION_ID);
        String name = resultSet.getString(SECTION_NAME);
        section = new Section(name);
        section.setId(id);
        return section;
    }

    public Section getSectionFromRs(ResultSet resultSet) throws SQLException {
        Section section = null;
        while (resultSet.next()) {
            section = processRs(resultSet);
        }
        return section;
    }

    public List<Section> getSectionsFromRs(ResultSet resultSet) throws SQLException {
        List<Section> keyWordList = new ArrayList<>();
        Section section = null;
        while (resultSet.next()) {
            section = processRs(resultSet);
            if (section != null) {
                keyWordList.add(section);
            }
        }
        return keyWordList;
    }

}
