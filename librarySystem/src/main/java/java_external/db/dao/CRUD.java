package java_external.db.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by olga on 15.05.18.
 */
public interface CRUD<T> {

    void insert(T entity);

    void update(T entity);

    void delete(int id);

    T findById(int id) throws SQLException;

    List<T> findAll();

}
