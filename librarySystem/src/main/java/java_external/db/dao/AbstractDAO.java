package java_external.db.dao;

import java_external.db.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO{
	protected Connection connection = null;
	protected ConnectionPool cp = null;

	public AbstractDAO() {
		super();
		ConnectionPool.init();
		cp = ConnectionPool.getInstance();
	}

	protected Connection getConnection() throws SQLException {
		return cp.getConnection();
	}

//	public PreparedStatement executeQuery(String sql){
//		PreparedStatement preparedStatement = null;
//		try {
//			connection = getConnection();
//			preparedStatement = connection.prepareStatement(sql);
//
//			return preparedStatement;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			//TODO
//		} finally {
//			closeConnection(null, preparedStatement);
//		}
//	}

	protected void closeConnection(ResultSet resultSet, PreparedStatement pStmnt) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (pStmnt != null)
				pStmnt.close();
			if (connection != null)
				cp.closeConnection(connection);
		} catch (SQLException e) {
//                log.warn("SQLException at book findAll()", e);
		} catch (Exception e) {
//                log.warn("Exception at book findAll()", e);
		}
	}


}
