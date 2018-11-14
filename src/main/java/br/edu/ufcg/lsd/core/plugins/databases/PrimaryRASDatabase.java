package br.edu.ufcg.lsd.core.plugins.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ufcg.lsd.core.utils.ProbeConstants;

public class PrimaryRASDatabase implements RASDatabase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryRASDatabase.class);
	
	private static final String TOTAL_VARIABLE = "total";
	private static final String ORDER_TABLE_NAME = "order_table";
	private static final String ORDER_STATE_NAME = "order_state";
	
	private String databaseURL;
	private String password;
	private String user;

	public PrimaryRASDatabase(Properties properties) throws Exception {
		checkDatabaseDriver(properties);
		
		configureDatabase(properties);
	}
		
	private static final String COUNT_ORDER_SQL = "SELECT COUNT(*) AS " + TOTAL_VARIABLE + 
													" FROM " + ORDER_TABLE_NAME + 
													" WHERE " + ORDER_STATE_NAME + " = ?";
	
	@Override
	public int getCountOrder(String state) {
		PreparedStatement countOrderStmt = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			countOrderStmt = connection.prepareStatement(COUNT_ORDER_SQL);
			countOrderStmt.setString(1, state);
			ResultSet executeQuery = countOrderStmt.executeQuery();		
			executeQuery.next();
			int total = executeQuery.getInt(1);
			
			connection.commit();
			
			return total;
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
			}
		} finally {
			close(countOrderStmt, connection);
		}
		return 0;
	}
	
	protected Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(this.databaseURL, this.user, this.password);				
		} catch (SQLException e) {
			LOGGER.error("Is not possible get database connection", e);
			throw e;
		}
	}		
	
	protected void checkDatabaseDriver(Properties properties) throws Exception {
		String databaseDriver = properties.getProperty(ProbeConstants.Properties.DATABASE_DRIVER);		
		try {
			Class.forName(databaseDriver);
		} catch (ClassNotFoundException e) {
			String errorMsg = String.format("Does not exists the driver releated with: %s", databaseDriver);
			LOGGER.error(errorMsg, e);
			throw new Exception(errorMsg);
		}
		LOGGER.debug("DatastoreDriver: " + databaseDriver);
	}	
	
	protected void configureDatabase(Properties properties) throws Exception {
		this.databaseURL = properties.getProperty(ProbeConstants.Properties.DATABASE_URL);
		if (this.databaseURL == null || this.databaseURL.isEmpty()) {
			throw new Exception("Database url is empty. This properties is required.");
		}
		LOGGER.debug("DatabaseURL: " + this.databaseURL);
		
		this.user = properties.getProperty(ProbeConstants.Properties.USER_DATABASE);
		this.databaseURL = properties.getProperty(ProbeConstants.Properties.DATABASE_URL);
	}	
	
	private void close(Statement statement, Connection conn) {
		if (statement != null) {
			try {
				if (!statement.isClosed()) {
					statement.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Couldn't close statement");
			}
		}

		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Couldn't close connection");
			}
		}
	}	

}
