package java_external.db.dao;

import java_external.db.dto.Keyword;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




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


    private static final String FIND_BY_NAMEPART = "SELECT * FROM keyword " +
            "WHERE name LIKE ?" ;

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

    public void insert(Keyword keyword) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_KEYWORD_QUERY);
            preparedStatement.setString(1, keyword.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at keyword insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Keyword keyword) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_KEYWORD_QUERY);
            preparedStatement.setString(1, keyword.getName());
            preparedStatement.setInt(2, keyword.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at keyword update()", e);
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

    public Keyword findById(int id) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Keyword keyword = null;
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(FIND_KEYWORD_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                keyword = fillInKeyword(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyword findById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return keyword;
    }

    public List<Keyword> findAll(int offset) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Keyword keyword = null;
        List keyWordList = new ArrayList<Keyword>();
        try {

            connection = getConnection();
               // ADD move to constant 0
                preparedStatement = connection.prepareStatement(FIND_ALL_PAGINATE);
                preparedStatement.setInt(1, offset);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                keyword = fillInKeyword(resultSet);
                if (keyword != null) {
                    keyWordList.add(keyword);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyWord findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return keyWordList;
    }

    public List<Keyword> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Keyword keyword = null;
        List keyWordList = new ArrayList<Keyword>();
        try {

            connection = getConnection();
                preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                keyword = fillInKeyword(resultSet);
                if (keyword != null) {
                    keyWordList.add(keyword);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyWord findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return keyWordList;
    }

    public List<Keyword> findByIdArray(String[] idArray) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Keyword> keywordList = new ArrayList<Keyword>();
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(FIND_KEYWORDS_BY_ID_ARRAY);
            preparedStatement.setArray(1, connection.createArrayOf("INT", idArray));
            resultSet = preparedStatement.executeQuery();
            keywordList = new ArrayList<Keyword>();
            while (resultSet.next()) {
                Keyword keyword = fillInKeyword(resultSet);
                if (keyword != null) {
                    keywordList.add(keyword);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyword findKeywordsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return keywordList;
    }


    private Keyword fillInKeyword(ResultSet resultSet) {
        Keyword keyword = null;
        try {
            int id = resultSet.getInt(KEYWORD_ID);
            String name = resultSet.getString(KEYWORD_NAME);
            keyword = new Keyword(name);
            keyword.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyword;
    }

    public List<Keyword> findKeywordsByBookId(int bookId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Keyword> keywordList = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_KEYWORDS_BY_BOOK_ID);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            keywordList = new ArrayList<Keyword>();
            while (resultSet.next()) {
                Keyword keyword = fillInKeyword(resultSet);
                if (keyword != null) {
                    keywordList.add(keyword);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at keyword findKeywordsByBookId()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return keywordList;
    }


}
