package java_external.db.dao;

import java_external.db.dto.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 14.05.18.
 */
public class GenreDAO extends AbstractDAO{

    private static final String ADD_GENRE_QUERY = "INSERT INTO genre(name) VALUES(?)";
    private static final String UPDATE_GENRE_QUERY = "UPDATE genre SET word = ? WHERE id = ?";
    private static final String DELETE_GENRE_QUERY = "DELETE genre WHERE id = ?";
    private static final String FIND_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM genre";

    private final static String GENRE_ID = "id";
    private final static String GENRE_NAME = "name";

    private static GenreDAO genreDAO;

    public static GenreDAO getInstance() {
        if (genreDAO == null) {
            genreDAO = new GenreDAO();
        }
        return genreDAO;
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

    public Genre findGenreById(int id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_GENRE_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Genre genre = null;
        try {
            while (resultSet.next()) {
                genre = fillInGenre(resultSet);
            }
        } catch (SQLException e) {
//            log.warn("SQLException at genre findGenreById()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }
        return genre;
    }

    public List<Genre> findAll() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        List genreList = new ArrayList<Genre>();
        try {
            while (resultSet.next()) {

                Genre genre = fillInGenre(resultSet);
                if (genre != null) {
                    genreList.add(genre);
                }
            }
        } catch (SQLException e) {
//            log.warn("SQLException at genre findAll()", e);
        } finally {
            closeConnection(resultSet, preparedStatement);

        }
        return genreList;
    }


    private Genre fillInGenre(ResultSet resultSet) {
        Genre genre = null;
        try {
            int id = resultSet.getInt(GENRE_ID);
            String name = resultSet.getString(GENRE_NAME);
            genre = new Genre(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }


}
