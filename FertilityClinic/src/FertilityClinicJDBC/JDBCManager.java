package FertilityClinicJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager {
	
	private Connection c = null; 
	
	public JDBCManager() {
		try {
			Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:DDBB_Grupo9.db");
		    c.createStatement().execute("PRAGMA foreign_keys=ON");
		    
		    System.out.println("Database connection opened.");
		    
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			System.out.println("Libraries not loaded.");
		}
	}
	
	public Connection getConnection(){
		return c; 
	}
	public void disconnect() {
		try {
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
