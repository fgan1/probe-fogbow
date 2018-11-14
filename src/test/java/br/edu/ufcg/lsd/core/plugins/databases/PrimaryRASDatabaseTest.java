package br.edu.ufcg.lsd.core.plugins.databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.edu.ufcg.lsd.core.utils.ProbeConstants;

public class PrimaryRASDatabaseTest {

	private final String DBCP2_DRIVER = "org.apache.commons.dbcp2.PoolingDriver";

	private static final String SRC_TEST_RESOURCE_BD_BASE_SQL = "src/test/resource/bd/base.sql";
	private static final String JDBC_HSQLDB_PASSWORD = "pass";
	private static final String JDBC_HSQLDB_USERNAME = "sa";
	private static final String JDBC_HSQLDB_URL = "jdbc:hsqldb:mem:appservice";
	
	private PrimaryRASDatabase primaryRASDatabase;
	
	@Before
	public void setUp() throws Exception {
		Properties properties = new Properties();
		properties.put(ProbeConstants.Properties.USER_DATABASE, JDBC_HSQLDB_USERNAME);
		properties.put(ProbeConstants.Properties.DATABASE_URL, JDBC_HSQLDB_URL);
		properties.put(ProbeConstants.Properties.PASSWORD_DATABASE, JDBC_HSQLDB_PASSWORD);
		properties.put(ProbeConstants.Properties.DATABASE_DRIVER, DBCP2_DRIVER);
		this.primaryRASDatabase = new PrimaryRASDatabase(properties);
		createSchema(this.primaryRASDatabase.getConnection());
	}
	
	// test case : Count fulfilled order
	@Ignore
	@Test
	public void testCountFulfilled() {
		int countOrderFulfilled = this.primaryRASDatabase.getCountOrder(RASDatabase.OrderState.FULFILLED.toString());
		
		// verify
		Assert.assertEquals(3, countOrderFulfilled);
	}
	
	@Test
	@Ignore
	public void testCountFailed() {
		int countOrderFulfilled = this.primaryRASDatabase.getCountOrder(RASDatabase.OrderState.FULFILLED.toString());
		
		// verify
		Assert.assertEquals(2, countOrderFulfilled);
	}	
	
	private void createSchema(final Connection conn) throws SQLException, IOException {
		File file = new File(SRC_TEST_RESOURCE_BD_BASE_SQL);
		runScript(conn, new InputStreamReader(new FileInputStream(file)));
	}
	
	private void runScript(final Connection conn, final Reader script)
			throws SQLException, IOException {
		ScriptRunner runner = new ScriptRunner(conn);
		runner.setLogWriter(null);
		runner.runScript(script);
	}	
	
}
