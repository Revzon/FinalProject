package java_external.db.dao;

import java_external.db.dao.base.CRUD;
import java_external.db.dao.base.FunctionRT;
import java_external.db.dto.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java_external.db.dao.base.QueryManager.executeQuery;


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
        return executeQuery(GET_COUNT, this::getCountFromRS);
    }

    public void insert(Genre genre) {
        executeQuery(ADD_GENRE_QUERY, preparedStatement -> {
            preparedStatement.setString(1, genre.getName());
        });
    }

    public void update(Genre genre) {
        executeQuery(UPDATE_GENRE_QUERY, preparedStatement -> {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setInt(2, genre.getId());
        });
    }

    public void delete(int id) {
        executeQuery(DELETE_GENRE_QUERY, preparedStatement -> {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        });
    }

    public Genre findById(int id) {
        return executeQuery(
                FIND_GENRE_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                getGenreHandler());
    }

    public List<Genre> findAll(int offset) {
        return executeQuery(FIND_ALL_PAGINATE, preparedStatement -> preparedStatement.setInt(1, offset), getGenreResultSetHandler());
    }

    public List<Genre> findAll() {
        return executeQuery(FIND_ALL, getGenreResultSetHandler());
    }

    private FunctionRT<ResultSet, List<Genre>> getGenreResultSetHandler() {
        return (resultSet) -> {
            List<Genre> genreList = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = processRs(resultSet);
                if (genre != null) {
                    genreList.add(genre);
                }
            }
            return genreList;
        };
    }

    private FunctionRT<ResultSet, Genre> getGenreHandler() {
        return (resultSet) -> {
            if (resultSet.next()) {
                return processRs(resultSet);
            }
            return null;
        };
    }

    private Genre processRs(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(GENRE_ID);
        String name = resultSet.getString(GENRE_NAME);
        Genre genre = new Genre(name);
        genre.setId(id);

        return genre;
    }


}
