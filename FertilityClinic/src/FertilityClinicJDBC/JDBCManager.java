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
			c = DriverManager.getConnection("jdbc:sqlite:./db/FertilityClinic2.db");
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
		try {//he cambiado las tablas
			
			Statement stmt = c.createStatement();
			
			String sql = "CREATE TABLE doctors ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    email TEXT NOT NULL,"
					+ "    phone INTEGER NOT NULL,"
					+ "    name TEXT NOT NULL,\n"
					+ "    speciality_id INTEGER NOT NULL,"
					+ "    licensePDF BLOB,"
					+ "    FOREIGN KEY (speciality_id) REFERENCES Specialities(id)"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE patients ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    name TEXT NOT NULL,"
					+ "    dob DATE NOT NULL,"
					+ "    email TEXT NOT NULL,"
					+ "    phone INTEGER NOT NULL,"
					+ "    height REAL NOT NULL,"
					+ "    weight REAL NOT NULL,"
					+ "    bloodType TEXT NOT NULL,"
					+ "    gender TEXT NOT NULL, "
					+ "    treatment_id INTEGER REFERENCES treatments(id)"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE appointments ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    patient_id INTEGER NOT NULL,"
					+ "    description TEXT NOT NULL,"
					+ "    time TIME NOT NULL,"
					+ "    date DATE NOT NULL,"
					+ "    doctor_id INTEGER NOT NULL,"
					+ "    FOREIGN KEY (patient_id) REFERENCES Patients(id),"
					+ "    FOREIGN KEY (doctor_id) REFERENCES Doctors(id)"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE specialities ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    name TEXT UNIQUE NOT NULL"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql ="CREATE TABLE treatments ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    name TEXT NOT NULL,"
					+ "    description TEXT NOT NULL,"
					+ "    durationInDays INTEGER NOT NULL"
					+ ");"
					+ "";
			
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE stock ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    productName TEXT NOT NULL,"
					+ "    category TEXT NOT NULL,"
					+ "    quantity INTEGER NOT NULL,"
					+ "    expiryDate DATE"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE managers ("
					+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "    email TEXT NOT NULL,"
					+ "    phone INTEGER NOT NULL,"
					+ "    name TEXT NOT NULL,"
					+ ");"
					+ "";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE treats (doctor_id INTEGER REFERENCES doctors(id),"
					+ "patient_id REFERENCES patients(id), PRIMARY KEY(doctor_id,patient_id) );";
			stmt.executeUpdate(sql);
			
		}catch(SQLException e) {
			if(!e.getMessage().contains("already exists")){
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
  