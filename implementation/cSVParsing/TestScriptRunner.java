package cSVParsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

//import com.mysql.jdbc.Connection;

public class TestScriptRunner {

	public void createDatabases(Connection conn) throws FileNotFoundException, IOException, SQLException {
		//Connection mConnection = null;
		
		 final String DB_URL = "jdbc:mysql://localhost/";

		 //  Database credentials
		 final String USER = "";	//insert whatever your username is here
		 final String PASS = "";	//insert whatever your password is here
		   
		   
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
		    System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
		    System.err.println("Unable to connect to server: " + e);
		}
		ScriptRunner runner = new ScriptRunner(conn, false, false);
		String file = "";	//insert the location of the sql file here until we input that into this method
		runner.runScript(new BufferedReader(new FileReader(file)));
	}
}