package FertilityClinicJDBC;

import FertilityClinicPOJOs.Patient;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JDBCPatientManager {	
  
	private JDBCManager manager;
	
	public JDBCPatientManager (JDBCManager m) {
		this.manager = m;
	}
	
	public void addPatient(String email, Integer phoneN, String name, double height, double weight, String bloodType, String gender, Integer age) {
		
		try {
			String sql= "INSERT INTO Patient (name, age, height, weight, bloodType, "
					+ "phoneNumber, email, Gender )"
					+ "VALUES (?,?,?,?,?;?,?,?)";
		
			PreparedStatement p = manager.getConnection().prepareStatement(sql);
		    p.setString(1, email);
		    p.setInt(2, phoneN);
		    p.setString(3, name);
		    p.setDouble(4, height);
		    p.setDouble(5, weight);
		    p.setString(6, bloodType);
		    p.setString(7, gender);
		    p.setInt(8, age);
			p.executeUpdate();
			p.close();
			
		}catch(SQLException e ) {
		e.printStackTrace();
		
		}

	
	}
					
	public List<Patient>  getListOfPatients() {
		
		List<Patient> patients= new ArrayList<Patient>();
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM Patient";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Integer id = rs.getInt("ID");
				Date dob = rs.getDate("dob");//esto o lo quitamos o lo añadimos a sql
				String email = rs.getString("email");
				Integer phoneN = rs.getInt("phoneNumber");
				String name = rs.getString("name");
				Double height = rs.getDouble("height");
				Double weight= rs.getDouble("weight");
				String bloodType = rs.getString("bloodType"); 
				String gender = rs.getString("Gender");
				Integer age = rs.getInt("age");
				
				
				Patient p = new Patient (id,dob,email,phoneN,name,height,weight,bloodType,gender,age);
				patients.add(p);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		
		return patients;
	}

	
	public Patient foundPatientByID(Integer id) {
	
		Patient p= null;
		
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM owners WHERE ID=" + id;
		
			ResultSet rs = stmt.executeQuery(sql);
			
			Integer p_id = rs.getInt("ID");
			Date dob = rs.getDate("dob");//esto o lo quitamos o lo añadimos a sql
			String email = rs.getString("email");
			Integer phoneN = rs.getInt("phoneNumber");
			String name = rs.getString("name");
			Double height = rs.getDouble("height");
			Double weight= rs.getDouble("weight");
			String bloodType = rs.getString("bloodType"); 
			String gender = rs.getString("Gender");
			Integer age = rs.getInt("age");
			
			
			 p = new Patient (id,dob,email,phoneN,name,height,weight,bloodType,gender,age);
		    
		    rs.close();
		    stmt.close();
		    
		}catch(Exception e) {e.printStackTrace();}
		
		
		return p;
	}
	/*
	
	@Override
	public void deleteAppointment() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyAppointment() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<Patient> readPatients() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	*/
}
