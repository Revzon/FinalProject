package java_external.db.connection;

import java_external.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private Logger logger = Logger.getLogger(ConnectionPool.class);


    public static final String PROPERTIES_FILE = "properties/pool";
    public static final int DEFAULT_POOL_SIZE = 100;
    private static ConnectionPool instance = null;

    private BlockingQueue<Connection> connectionQueue;

    private ConnectionPool(String url, int poolSize)
            throws SQLException {

        connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url);
            connectionQueue.offer(connection);
        }
    }

    public static void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (instance == null) {
            ResourceBundle resourceBundle = null;
            try {
                resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE);

                String url = resourceBundle.getString("db.url");
                String poolSizeStr = resourceBundle.getString("db.poolsize");

                int poolSize = (poolSizeStr != null) ? Integer.parseInt(poolSizeStr) : DEFAULT_POOL_SIZE;
                instance = new ConnectionPool(url, 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void dispose() throws SQLException {
        if (instance != null) {
            instance.clearConnectionQueue();
            instance = null;
        }
    }

    public static ConnectionPool getInstance() {
        if(instance == null){
            ConnectionPool.init();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new DAOException(e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                if (!connectionQueue.offer(connection)) {
                }
            } else {
           }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void clearConnectionQueue() throws SQLException {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }
}
