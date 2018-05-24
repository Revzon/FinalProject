package java_external.db.dao.base;

import java_external.exceptions.DAOException;

import java.util.function.Function;


@FunctionalInterface
public interface FunctionRT<T, R> extends Function<T, R> {

    @Override
    default R apply(final T elem) {
        try {
            return applyThrows(elem);
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }

    R applyThrows(T elem) throws Exception;

}
