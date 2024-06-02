package FertilityClinicJDBC;

import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FertilityClinicInterfaces.TreatmentManager;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Treatment;
import FertilityClinicPOJOs.TreatmentStep;


public class JDBCTreatmentManager implements TreatmentManager {
	
	private JDBCManager manager; 
	
	public JDBCTreatmentManager (JDBCManager m) {
		this.manager = m;
	}
	public Treatment getTreatmentById(Integer id) {
		
		Treatment t= null;
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM Treatments WHERE ID=" + id;
		
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Integer p_id = rs.getInt("ID");
				String name = rs.getString("name");
				String description = rs.getString("description"); 
				Integer durationInDays = rs.getInt("durationInDays");
			
				t = new Treatment (p_id,name,description,durationInDays);
			}
		    rs.close();
		    stmt.close();
		    
		}catch(Exception e) {e.printStackTrace();}
		
		
		return t;
	}
	
	

	public void addTreatment(Treatment treatment) {

	    try {
		    String sql = "INSERT INTO treatments (treatmentID, name, description, durationInDays) VALUES (?, ?, ?, ?)";
	    
	         PreparedStatement pstmt = manager.getConnection().prepareStatement(sql); 

	        pstmt.setInt(1, treatment.getTreatmentID());
	        pstmt.setString(2, treatment.getNameTreatment());
	        pstmt.setString(3, treatment.getDescription());
	        pstmt.setInt(4, treatment.getDurationInDays());

	        pstmt.executeUpdate();
	        pstmt.close();
	


}catch(SQLException e) {
	e.printStackTrace();
}
	}
	
	public void updateTreatment(Treatment treatment) {
	    String sql = "UPDATE treatments SET name = ?, description = ?, durationInDays = ? WHERE treatmentID = ?";
	    try {
	    	
	    
	         PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);

	        pstmt.setString(1, treatment.getNameTreatment());
	        pstmt.setString(2, treatment.getDescription());
	        pstmt.setInt(3, treatment.getDurationInDays());
	        pstmt.setInt(4, treatment.getTreatmentID());

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteTreatment(int id) {
	    String sql = "DELETE FROM treatments WHERE treatmentID = ?";
	    try {
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setInt(1, id);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<Treatment> searchTreatmentsByPatientName(String patientName) {
	    List<Treatment> treatmentList = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM treatments WHERE name LIKE ? ORDER BY name";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setString(1, "%" + patientName + "%");
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	Integer p_id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description"); 
				Integer durationInDays = rs.getInt("duration");
				//como añadimos aquí el doctor
			
				Treatment treatments = new Treatment (p_id,name,description,durationInDays);
	           
	            treatmentList.add(treatments);
	        }

	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return treatmentList;
	}

	
	@Override
	public List<Treatment> searchTreatmentsByDoctorId(int doctorId) {
		List<Treatment> treatmentList = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM treatments WHERE doctorid LIKE ? ORDER BY doctorid";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setString(1, "%" + doctorId + "%");
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	Integer p_id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description"); 
				Integer durationInDays = rs.getInt("duration");
	
			
				Treatment treatments = new Treatment (p_id,name,description,durationInDays);
	           
	            treatmentList.add(treatments);
	        }

	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return treatmentList;
	}
	
	public Map<Integer, Boolean> getStepCompletion(int patientId, int treatmentId) {
	    Map<Integer, Boolean> completion = new HashMap<>();
	    String sql = "SELECT treatmentStepID, isCompleted FROM patientTreatment WHERE patientID = ?";

	    try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
	        pstmt.setInt(1, patientId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            completion.put(rs.getInt("treatmentStepID"), rs.getBoolean("isCompleted"));
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return completion;
	}
	/*
	public Map<Integer, Boolean> getStepCompletion(int patientId, int treatmentId) {
	    Map<Integer, Boolean> completionStatus = new HashMap<>();
	    String sql = "SELECT ts.id, pts.isCompleted FROM treatmentSteps ts " +
	                 "LEFT JOIN patientTreatmentStep pts ON ts.id = pts.treatmentStepID " +
	                 "AND pts.patientID = ? WHERE ts.treatmentID = ?";

	    try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
	        pstmt.setInt(1, patientId);
	        pstmt.setInt(2, treatmentId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int stepId = rs.getInt("id");
	            boolean isCompleted = rs.getBoolean("isCompleted") && rs.wasNull() == false;
	            completionStatus.put(stepId, isCompleted);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return completionStatus;
	}*/

	
	public void updateStepCompletion(int patientId, int stepId, boolean isSelected) {
	    String sql = "UPDATE patientTreatment SET isCompleted = ? WHERE patientID = ? AND treatmentStepID = ?";

	    try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
	        pstmt.setBoolean(1, isSelected);
	        pstmt.setInt(2, patientId);
	        pstmt.setInt(3, stepId);

	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            System.out.println("No rows affected, it seems there was nothing to update.");
	        } else {
	            System.out.println("Step completion updated successfully for step ID: " + stepId + " and patient ID: " + patientId);
	        }

	    } catch (SQLException e) {
	        System.out.println("Error updating step completion: " + e.getMessage());
	        e.printStackTrace();
	    }
	}



	
	public List<TreatmentStep> getTreatmentSteps(int treatmentId) {
	    List<TreatmentStep> steps = new ArrayList<>();
	    String sql = "SELECT * FROM treatmentSteps WHERE treatmentID = ? ORDER BY stepOrder";
	    try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
	        pstmt.setInt(1, treatmentId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            int tId = rs.getInt("treatmentID");
	            String stepDescription = rs.getString("stepDescription");
	            int stepOrder = rs.getInt("stepOrder");
	            TreatmentStep step = new TreatmentStep(id, tId, stepDescription, stepOrder);
	            steps.add(step);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return steps;
	}
	/*
	public void updateStepCompletion(int patientId, int stepId, boolean isSelected) {
	    // SQL command to update the completion status of a treatment step
	    String sql = "UPDATE patientTreatment SET isCompleted = ? WHERE patientID = ? AND treatmentStepID = ?";

	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = manager.getConnection(); // Get the database connection from your manager
	        conn.setAutoCommit(false); // Disable auto-commit for transaction handling

	        pstmt = conn.prepareStatement(sql); // Prepare the SQL statement

	        pstmt.setBoolean(1, isSelected); // Set completion status
	        pstmt.setInt(2, patientId); // Set patient ID
	        pstmt.setInt(3, stepId); // Set treatment step ID

	        int affectedRows = pstmt.executeUpdate(); // Execute the update

	        if (affectedRows == 0) {
	            // If no rows affected, something went wrong
	            conn.rollback(); // Roll back transaction
	            throw new SQLException("Updating treatment step completion failed, no rows affected.");
	        }

	        conn.commit(); // Commit the transaction if all good

	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // Roll back transaction on error
	            } catch (SQLException ex) {
	                System.out.println("Error rolling back transaction");
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close(); // Close PreparedStatement
	            if (conn != null) conn.close(); // Close connection
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}*/


	public List<Treatment> getAllTreatments() {
	    List<Treatment> treatments = new ArrayList<>();
	    String sql = "SELECT * FROM treatments";  

	    try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)){
	    	
	    	ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            int durationInDays = rs.getInt("durationInDays");

	            Treatment treatment = new Treatment(id, name, description, durationInDays);
	            treatments.add(treatment);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving all treatments: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return treatments;
	}
	




}
	
