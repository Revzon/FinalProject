package java_external.db.dao;

import java_external.db.connection.ConnectionPool;
import java_external.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {
    protected Connection connection = null;
    protected ConnectionPool cp = null;

    public AbstractDAO() {
        super();
        cp = ConnectionPool.getInstance();
    }

    protected Connection getConnection() throws SQLException {
        return cp.getConnection();
    }

    protected void closeConnection(ResultSet resultSet, PreparedStatement pStmnt) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (pStmnt != null)
                pStmnt.close();
            if (connection != null)
                cp.closeConnection(connection);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    protected int getCountFromRS(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            return resultSet.getInt("count");
        }
        return 0;
    }


}
