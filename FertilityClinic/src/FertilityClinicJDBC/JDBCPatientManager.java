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

import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.TreatmentsManager;


public class JDBCPatientManager implements PatientManager{	
  
	private JDBCManager manager;
    private TreatmentsManager treatmentmanager;


        public JDBCPatientManager(JDBCManager manager, TreatmentsManager treatmentManager) {
            this.manager = manager;
            this.treatmentmanager = treatmentManager;
        }

    

	public void addPatient(Patient patient) {
	    try {
	        String sql = "INSERT INTO patients (name, dob, email, phone, height, weight, bloodType, gender, treatment_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);

	        stmt.setString(1, patient.getName());
	        stmt.setDate(2, new java.sql.Date(patient.getDob().getTime()));
	        stmt.setString(3, patient.getEmail());
	        stmt.setInt(4, patient.getPhone());
	        stmt.setDouble(5, patient.getHeight());
	        stmt.setDouble(6, patient.getWeight());
	        stmt.setString(7, patient.getBloodType());
	        stmt.setString(8, patient.getGender());
	        // Check if treatment is not null
	        Integer treatmentId = (patient.getTreatmet() != null) ? patient.getTreatmet().getTreatmentID() : null;
	        if (treatmentId != null) {
	            stmt.setInt(9, treatmentId);
	        } else {
	            stmt.setNull(9, java.sql.Types.INTEGER);
	        }

	        stmt.executeUpdate();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


					
	public List<Patient>  getListOfPatients() {
		
		List<Patient> patients= new ArrayList<Patient>();
		Treatments treatment=null;
	try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patients";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{ 
				 Integer p_id = rs.getInt("id");
		            Date dob = rs.getDate("dob");
		            String email = rs.getString("email");
		            Integer phoneN = rs.getInt("phoneNumber");
		            String name = rs.getString("name");
		            Double height = rs.getDouble("height");
		            Double weight = rs.getDouble("weight");
		            String bloodType = rs.getString("bloodType");
		            String gender = rs.getString("gender");
		            Integer treatmentId = rs.getInt("treatment_id");
		            treatment = treatmentmanager.getTreatmentById(treatmentId);
		            
		          
		            Patient p = new Patient (p_id,name, dob,email,phoneN,height,weight,bloodType,gender,treatment);
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
	

	
  public void modifyPatientInfo(Integer patientId, String email, Integer phoneN, String name) {
	    try {
	        String sql = "UPDATE patients SET email=?, phone=?, name=? WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setInt(2, phoneN);
	        stmt.setString(3, name);
	        stmt.setInt(4, patientId);

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Patient information updated successfully.");
	        } else {
	            System.out.println("Failed to update patient information. Patient with ID " + patientId + " not found.");
	        }

	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void removePatientById (Integer id) {
		
		try {
			String sql = "DELETE FROM patients WHERE id=?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, id);
			
			prep.executeUpdate();			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public Patient viewMyInfo(Integer patientId) {
	    Patient patient = null;
	    try {
	        String sql = "SELECT * FROM patients WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, patientId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer id = rs.getInt("id");
	            Date dob = rs.getDate("dob");
	            String email = rs.getString("email");
	            Integer phoneN = rs.getInt("phone");
	            String name = rs.getString("name");
	            Double height = rs.getDouble("height");
	            Double weight = rs.getDouble("weight");
	            String bloodType = rs.getString("bloodType");
	            String gender = rs.getString("gender");
	            Integer treatmentId = rs.getInt("treatment_id");
	            Treatments treatment = treatmentmanager.getTreatmentById(treatmentId);

	            patient = new Patient(id, name, dob,email, phoneN,height, weight, bloodType, gender, treatment);
	        } else {
	            System.out.println("Patient with ID " + patientId + " not found.");
	        }

	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return patient;
	}
	
	public Patient searchPatientById(Integer id) {
	    Patient patient = null;

	    try {
	        String sql = "SELECT * FROM patients WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer p_id = rs.getInt("id");
	            Date dob = rs.getDate("dob");
	            String email = rs.getString("email");
	            Integer phoneN = rs.getInt("phoneNumber");
	            String name = rs.getString("name");
	            Double height = rs.getDouble("height");
	            Double weight = rs.getDouble("weight");
	            String bloodType = rs.getString("bloodType");
	            String gender = rs.getString("gender");

	            
	            Integer treatmentId = rs.getInt("treatment_id");
	            Treatments treatment = treatmentmanager.getTreatmentById(treatmentId);

	           
	            patient = new Patient(p_id,name, dob, email, phoneN, height, weight, bloodType, gender, treatment);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return patient;
	}
	
	


	@Override
	public Patient getPatientByEmail(String email) {
	    Patient patient = null;
	    
	    try {
	        String sql = "SELECT * FROM patients WHERE email = ?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            Integer id = rs.getInt("ID");
	            Date dob = rs.getDate("dob");
	            Integer phoneN = rs.getInt("phone");
	            String name = rs.getString("name");
	            Double height = rs.getDouble("height");
	            Double weight = rs.getDouble("weight");
	            String bloodType = rs.getString("bloodType");
	            String gender = rs.getString("Gender");
	            Integer treatmentId = rs.getInt("treatment_id");
	            
	            Treatments treatment = null;
	            if (treatmentId != null && treatmentId > 0) { // Asegura que el treatmentId es válido
	                treatment = treatmentmanager.getTreatmentById(treatmentId);
	            }

	            patient = new Patient(id, name, dob, email, phoneN, height, weight, bloodType, gender, treatment);
	        }
	        
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return patient;
	}


	
	
//hola
	
	
	
	
	
}
