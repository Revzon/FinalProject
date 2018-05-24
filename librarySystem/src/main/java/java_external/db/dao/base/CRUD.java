package java_external.db.dao.base;

import java.util.List;

public interface CRUD<T> {

    void insert(T entity);

    void update(T entity);

    void delete(int id);

    T findById(int id);

    List<T> findAll(int offset);

}
