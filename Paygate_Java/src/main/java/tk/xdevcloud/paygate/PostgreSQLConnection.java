package tk.xdevcloud.paygate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

/**
 * 
 * PostgreSQL DB Connection Class
 * 
 * @author kwesidev
 */
public class PostgreSQLConnection {

	/**
	 * establish postgreSQL connection instance that can be used through the
	 * application
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() throws IOException, SQLException {

		Connection conn = null;
		String user, password, database;
		Integer port;
		// Load the database config

		user = (String) Config.getValue("pgsql.username");
		password = (String) Config.getValue("pgsql.password");
		database = (String) Config.getValue("pgsql.database");
		port = Integer.valueOf((String)Config.getValue("pgsql.port"));

		try {
			// Load postgresql driver
			Class.forName("org.postgresql.Driver");
			// Connect to pgsql
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + String.valueOf(port) + "/" + database,
					user, password);

		} catch (ClassNotFoundException exception) {

			exception.printStackTrace();
		}

		return conn;
	}
}
