package FertilityClinicJDBC;

import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Treatments;
import FertilityClinicPOJOs.Doctor;

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
	
	public void addPatient(Patient patient) {
		
		try {
			String sql= "INSERT INTO Patient (name, age, height, weight, bloodType, "
					+ "phoneNumber, email, Gender, doctor_id, treatment_id)"
					+ "VALUES (?,?,?,?,?;?,?,?,?,?)";
		
			PreparedStatement p = manager.getConnection().prepareStatement(sql);
		    p.setString(1, patient.getEmail());
		    p.setInt(2, patient.getPhoneN());
		    p.setString(3, patient.getName());
		    p.setDouble(4, patient.getHeight());
		    p.setDouble(5, patient.getWeight());
		    p.setString(6, patient.getBloodType());
		    p.setString(7, patient.getGender());
		    p.setInt(8, patient.getAge());
		    for(Doctor doctor: patient.getDoctors()) {
		    	p.setInt(9,doctor.getId());
		    }
		    p.setString(10,patient.getTreatmet().getName());
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
				 
				
				
				Patient p = new Patient (id, dob,email,phoneN,name,height,weight,bloodType,gender,age);
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
			String sql = "SELECT * FROM Patient WHERE ID=" + id;
		
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
	
	//@Override
	public void updateTreatment (Integer p_id, String treatment) {
		
		try {
			String sql="UPDATE patients SET speciality=? WHERE id=?; ";
			PreparedStatement prep=manager.getConnection().prepareStatement(sql);
			
			prep.setString(1, treatment);
			prep.setInt(2, p_id);
			
			prep.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
