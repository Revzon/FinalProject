package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dto.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


public class KeywordDAO extends AbstractDAO implements CRUD<Keyword> {

    private static final String ADD_KEYWORD_QUERY = "INSERT INTO keyword(name) VALUES(?)";
    private static final String UPDATE_KEYWORD_QUERY = "UPDATE keyword SET name = ? WHERE id = ?";
    private static final String DELETE_KEYWORD_QUERY = "DELETE FROM keyword WHERE id = ?";
    private static final String FIND_KEYWORD_BY_ID = "SELECT * FROM keyword WHERE id = ?";
    private static final String FIND_KEYWORDS_BY_ID_ARRAY = "SELECT * FROM keyword WHERE id IN ?";
    private static final String FIND_KEYWORDS_BY_BOOK_ID = "SELECT * FROM keyword RIGHT JOIN book_keyword ON book_keyword.keyword_id = keyword.id WHERE book_keyword.book_id = ?";
    private static final String FIND_ALL = "SELECT * FROM keyword";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM keyword LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM keyword";

    private final static String KEYWORD_ID = "id";
    private final static String KEYWORD_NAME = "name";

    private static KeywordDAO keywordDAO;

    public static KeywordDAO getInstance() {
        if (keywordDAO == null) {
            keywordDAO = new KeywordDAO();
        }
        return keywordDAO;
    }

    public int getCount() {
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Keyword keyword) {
        executeQuery(ADD_KEYWORD_QUERY, preparedStatement -> {
            preparedStatement.setString(1, keyword.getName());
        });
    }

    public void update(Keyword keyword) {
        executeQuery(UPDATE_KEYWORD_QUERY, preparedStatement -> {
            preparedStatement.setString(1, keyword.getName());
            preparedStatement.setInt(2, keyword.getId());
            preparedStatement.executeUpdate();
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_KEYWORD_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
        });
    }

    public Keyword findById(int id) {
        return executeQuery(FIND_KEYWORD_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                this::getKeywordFromRs);
    }


    public List<Keyword> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE,
                preparedStatement -> preparedStatement.setInt(1, offset),
                this::getKeywordsFromRs);
    }


    public List<Keyword> findAll() {
        return executeQuery(FIND_ALL, this::getKeywordsFromRs);
    }

    public List<Keyword> findByIdArray(String[] idArray) {
        return executeQuery(FIND_KEYWORDS_BY_ID_ARRAY,
                preparedStatement -> preparedStatement.setArray(1, connection.createArrayOf("INT", idArray)),
                this::getKeywordsFromRs);
    }


    public List<Keyword> findKeywordsByBookId(int bookId) {
        return executeQuery(FIND_KEYWORDS_BY_BOOK_ID,
                preparedStatement ->  preparedStatement.setInt(1, bookId),
                this::getKeywordsFromRs);
    }

    private Keyword processRs(ResultSet resultSet) throws SQLException {
        Keyword keyword = null;
        int id = resultSet.getInt(KEYWORD_ID);
        String name = resultSet.getString(KEYWORD_NAME);
        keyword = new Keyword(name);
        keyword.setId(id);
        return keyword;
    }

    public Keyword getKeywordFromRs(ResultSet resultSet) throws SQLException {
        Keyword keyword = null;
        while (resultSet.next()) {
            keyword = processRs(resultSet);
        }
        return keyword;
    }

    public List<Keyword> getKeywordsFromRs(ResultSet resultSet) throws SQLException {
        List<Keyword> keyWordList = new ArrayList<>();
        Keyword keyword = null;
        while (resultSet.next()) {
            keyword = processRs(resultSet);
            if (keyword != null) {
                keyWordList.add(keyword);
            }
        }
        return keyWordList;
    }

}
