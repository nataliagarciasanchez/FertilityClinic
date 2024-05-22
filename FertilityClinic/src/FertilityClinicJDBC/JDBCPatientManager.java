package FertilityClinicJDBC;

import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Treatments;

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
	
	@Override
	public List<Patient> getPatientsByDoctorId(int doctorId) {
	    List<Patient> patients = new ArrayList<>();
	    String sql = "SELECT patients.*, treatments.* FROM patients " +
	                 "LEFT JOIN treatments ON patients.treatment_id = treatments.id " +
	                 "WHERE doctor_id = ?";
	    try (Connection conn = manager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	         
	        pstmt.setInt(1, doctorId);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Treatments treatment = null;
	            if (rs.getInt("treatment_id") != 0) { // Asumiendo que el 'treatment_id' es un campo en la tabla 'patients'
	                treatment = new Treatments(
	                    rs.getInt("treatment_id"),
	                    rs.getString("name"), // Asumiendo que estos son los campos en la tabla 'treatments'
	                    rs.getString("description"),
	                    rs.getInt("durationInDays")
	                );
	            }

	            Patient patient = new Patient(
	                rs.getInt("id"),
	                rs.getString("name"),
	                rs.getDate("dob"),
	                rs.getString("email"),
	                rs.getInt("phone"),
	                rs.getDouble("height"),
	                rs.getDouble("weight"),
	                rs.getString("bloodType"),
	                rs.getString("gender"),
	                treatment
	            );
	            patients.add(patient);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return patients;
	}

	

	
	public void modifyPatientInfo(Integer patientId, String email, Integer phoneN, String name, Double height, Double weight) {
	    try {
	        String sql = "UPDATE patients SET email=?, phone=?, name=?, height=?, weight=? WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setInt(2, phoneN);
	        stmt.setString(3, name);
	        stmt.setDouble(4, height);
	        stmt.setDouble(5, weight);
	        stmt.setInt(6, patientId);

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
