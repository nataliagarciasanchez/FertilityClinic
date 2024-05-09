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
			c = DriverManager.getConnection("jdbc:sqlite:./FertilityClinic/db/Fertility.db");
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
			//esto hay que cambiarlo
			String sql = "CREATE TABLE patients ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL, phone INTEGER, "
					+ "bankaccount INTEGER, email TEXT NOT NULL,"
					+ "dob LOCALDATE, height DOUBLE, weight DOUBLE, "
					+ "bloodType BLOODTYPE, );";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE doctors ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL, cured BOOLEAN, "
					+ "typeOfAnimal TEXT CHECK (typeOfAnimal in (dog, cat, bird, hamster)),"
					+ "coat TEXT, dob DATE NOT NULL, onwer_id INTEGER REFERENCES owners(id));";
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
  