package java_external.db.dao;

import java_external.db.dto.Genre;
import java_external.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;




public class GenreDAO extends AbstractDAO implements CRUD<Genre> {

    private static final String ADD_GENRE_QUERY = "INSERT INTO genre(name) VALUES(?)";
    private static final String UPDATE_GENRE_QUERY = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String DELETE_GENRE_QUERY = "DELETE FROM genre WHERE id = ?";
    private static final String FIND_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM genre";
    private static final String FIND_ALL_PAGINATE = "SELECT * FROM genre LIMIT 10 OFFSET ?";
    private static final String GET_COUNT = "SELECT COUNT(id) as count FROM genre";
    
    private final static String GENRE_ID = "id";
    private final static String GENRE_NAME = "name";

    private static GenreDAO genreDAO;

    public static GenreDAO getInstance() {
        if (genreDAO == null) {
            genreDAO = new GenreDAO();
        }
        return genreDAO;
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

    public void insert(Genre genre) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_GENRE_QUERY);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //            log.warn("SQLException at genre insert()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void update(Genre genre) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_GENRE_QUERY);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setInt(2, genre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at genre update()", e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_GENRE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.warn("SQLException at genre delete()", e);
        } finally {
            closeConnection(null, preparedStatement);

        }
    }

    public Genre findById(int id) {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        Genre genre = null;
//
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(FIND_GENRE_BY_ID);
//            preparedStatement.setInt(1, id);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                genre = processRs(resultSet);
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        } finally {
//            closeConnection(resultSet, preparedStatement);
//        }
//        return genre;
        Consumer<PreparedStatement> preparedStatementConsumer = preparedStatement -> {
            try {
                preparedStatement.setInt(1, id);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        };
        return QueryManager.executeQuery(getGenreHandler(), preparedStatementConsumer, FIND_GENRE_BY_ID);
    }

    public List<Genre> findAll(int offset) {
        Consumer<PreparedStatement> preparedStatementConsumer = preparedStatement -> {
            try {
                preparedStatement.setInt(1, offset);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        };
        return QueryManager.executeQuery(getGenreResultSetHandler(), preparedStatementConsumer, FIND_ALL_PAGINATE);
    }
    
    public List<Genre> findAll() {
        return QueryManager.executeQuery(getGenreResultSetHandler(), FIND_ALL);
    }

    private Function<ResultSet, List<Genre>> getGenreResultSetHandler() {
        return (resultSet) -> {
            try {
                List<Genre> genreList = new ArrayList<>();
                while (resultSet.next()) {
                    Genre genre = processRs(resultSet);
                    if (genre != null) {
                        genreList.add(genre);
                    }
                }
                return genreList;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        };
    }

    private Function<ResultSet, Genre> getGenreHandler() {
        return (resultSet) -> {
            try {
                Genre genre = null;
                while (resultSet.next()) {
                    genre = processRs(resultSet);
                }
                return genre;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        };
    }
    
    private Genre processRs(ResultSet resultSet) {
        Genre genre = null;
        try {
            int id = resultSet.getInt(GENRE_ID);
            String name = resultSet.getString(GENRE_NAME);
            genre = new Genre(name);
            genre.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }


}
