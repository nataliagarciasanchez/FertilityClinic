package FertilityClinicJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
	
private Connection c = null;
	
	public JDBCManager() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/Fertility.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			
			System.out.print("Database Connection opened.");
			this.createTables();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			System.out.print("Libraries not loaded");
		}
	}
	
	
	private void createTables() {
		try {
			
			Statement stmt = c.createStatement();
			
			String sql = "CREATE TABLE Doctor ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL, type INTEGER, "
					+ "phone number INTEGER,email TEXT NOT NULL,"
					+ " patients_id INTEGER REFERENCES Patient(id));";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Manager ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL, phone number INTEGER, "
					+ "email TEXT,);";
			stmt.executeUpdate(sql);
			
			 sql = "CREATE TABLE Patient ("
						+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "name TEXT NOT NULL, age INTEGER, "
						+ "height NUMERIC, weight NUMERIC,"
						+ "bloodType TEXT NOT NULL, phoneNumber INTEGER,"
						+ "email TEXT NOT NULL,BankAccountNumber INTEGER,"
						+ "Gender TEXT NOT NULL, );";
				stmt.executeUpdate(sql);
				
			
			
			
			
		}catch(SQLException e) {
			if(!e.getMessage().contains("already exists")) 
			{
				e.printStackTrace();
			}			
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
  