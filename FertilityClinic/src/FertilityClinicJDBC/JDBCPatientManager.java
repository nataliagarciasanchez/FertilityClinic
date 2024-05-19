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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.TreatmentsManager;


public class JDBCPatientManager {	
  
	private JDBCManager manager;
	private TreatmentsManager treatmentmanager;
	private DoctorManager doctormanager;
	
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
		    p.setInt(2, patient.getPhone());
		    p.setString(3, patient.getName());
		    p.setDouble(4, patient.getHeight());
		    p.setDouble(5, patient.getWeight());
		    p.setString(6, patient.getBloodType());
		    p.setString(7, patient.getGender());
		   
		    for(Doctor doctor: patient.getDoctors()) {
		    	p.setInt(8,doctor.getId());
		    }
		    p.setInt(9,patient.getTreatmet().getTreatmentID());
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
				String name = rs.getString("name");
				Date dob = rs.getDate("dob");//esto o lo quitamos o lo añadimos a sql
				String email = rs.getString("email");
				
				
				Patient p = new Patient (id, name, dob,email) ;
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
	    Patient patient = null;

	    try {
	        String sql = "SELECT * FROM Patient WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer p_id = rs.getInt("ID");
	            Date dob = rs.getDate("dob");
	            String email = rs.getString("email");
	            Integer phoneN = rs.getInt("phoneNumber");
	            String name = rs.getString("name");
	            Double height = rs.getDouble("height");
	            Double weight = rs.getDouble("weight");
	            String bloodType = rs.getString("bloodType");
	            String gender = rs.getString("Gender");

	     
	            Integer treatmentId = rs.getInt("treatment_id");
	            Treatments treatment = treatmentmanager.getTreatmentById(treatmentId);

	            List<Integer> doctorIds = new ArrayList<>();
	            Integer doctorId = rs.getInt("doctor_id");
	            doctorIds.add(doctorId);

	            List<Doctor> doctors = doctormanager.getDoctorForPatient(doctorIds);

	            // Crear el objeto Patient con los datos obtenidos
	            patient = new Patient(p_id, dob, email, phoneN, name, height, weight, bloodType, gender, doctors, treatment);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return patient;
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
