package java_external.db.dao;

import java_external.db.connection.ConnectionPool;
import java_external.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.function.Function;



public class QueryManager {
    private static final Logger logger = Logger.getLogger(QueryManager.class);


    public static <T> T executeQuery(Function<ResultSet, T> wrappedFunction, String query) {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            T result = wrappedFunction.apply(resultSet);
            return result;
        } catch (Exception sqlException) {
            logger.error("Error:", sqlException);
            throw new DAOException(sqlException);
        } finally {
            try {
                safeClose(new AutoCloseable[]{connection, resultSet});
                closeConnection(connectionPool, connection);
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public static <T> T executeQuery(Function<ResultSet, T> resultSetFunction, Consumer<PreparedStatement> paramConsumer, String query) {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            paramConsumer.accept(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            T result = resultSetFunction.apply(resultSet);
            return result;
        } catch (Exception sqlException) {
            logger.error("Error:", sqlException);
            throw new DAOException(sqlException);
        } finally {
            try {
                safeClose(new AutoCloseable[]{resultSet, preparedStatement});
                closeConnection(connectionPool, connection);
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    private static void safeClose(AutoCloseable[] closeables) throws Exception {
        for (AutoCloseable closable : closeables) {
            if (closable != null) {
                closable.close();
            }
        }
    }

    private static void closeConnection(ConnectionPool connectionPool, Connection connection){
        if (connectionPool != null) {
            connectionPool.closeConnection(connection);
        }
    }

}
