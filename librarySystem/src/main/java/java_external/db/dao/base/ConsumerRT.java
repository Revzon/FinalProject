package java_external.db.dao.base;

import java_external.exceptions.DAOException;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerRT<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;

}
