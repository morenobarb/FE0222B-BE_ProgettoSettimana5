package servizi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {

	private static final String URL = "jdbc:postgresql://localhost:5432/multedb";
	private static final String USER = "postgres";
	private static final String PASS = "password";
	private static Logger Log = LoggerFactory.getLogger(ConnectionFactory.class);

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Log.info("CONNESSIONE COL DB AVVENUTA");
		} catch (SQLException e) {
			Log.info("CONNESSIONE NON RIUSCITA");
		}
		return conn;
	}

}
