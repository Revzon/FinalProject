package java_external.db.dao;

import java_external.db.dto.KeyWord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class KeywordDAO extends AbstractDAO{

    private static final String ADD_KEYWORD_QUERY = "INSERT INTO keyword(name) VALUES(?)";
    private static final String UPDATE_KEYWORD_QUERY = "UPDATE keyword SET name = ? WHERE id = ?";
    private static final String DELETE_KEYWORD_QUERY = "DELETE keyword WHERE id = ?";
    private static final String FIND_KEYWORD_BY_ID = "SELECT * FROM keyword WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM keyword";

    private final static String KEYWORD_ID = "id";
    private final static String KEYWORD_NAME = "name";

    private static KeywordDAO keywordDAO;

    public static KeywordDAO getInstance() {
        if (keywordDAO == null) {
            keywordDAO = new KeywordDAO();
        }
        return keywordDAO;
    }

    public void insert(KeyWord keyWord) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_KEYWORD_QUERY);
            preparedStatement.setString(1, keyWord.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at keyWord insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(KeyWord keyWord) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_KEYWORD_QUERY);
            preparedStatement.setString(1, keyWord.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at keyWord update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_KEYWORD_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at keyWord delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public KeyWord findKeyWordById(int id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_KEYWORD_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        KeyWord keyWord = null;
        try {
            while (resultSet.next()) {
                keyWord = fillInKeyWord(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyWord findKeyWordById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return keyWord;
    }

    public List<KeyWord> findAll() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        List keyWordList = new ArrayList<KeyWord>();
        try {
            while (resultSet.next()) {

                KeyWord keyWord = fillInKeyWord(resultSet);
                if (keyWord != null) {
                    keyWordList.add(keyWord);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyWord findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return keyWordList;
    }


    private KeyWord fillInKeyWord(ResultSet resultSet) {
        KeyWord keyWord = null;
        try {
            int id = resultSet.getInt(KEYWORD_ID);
            String name = resultSet.getString(KEYWORD_NAME);
            keyWord = new KeyWord(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyWord;
    }



}
