package FertilityClinicJDBC;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import FertilityClinicInterfaces.TreatmentsManager;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Treatments;


public class JDBCTreatmentsManager implements TreatmentsManager {
	
	private JDBCManager manager; 
	
	public JDBCTreatmentsManager (JDBCManager m) {
		this.manager = m;
	}
	public Treatments getTreatmentById(Integer id) {
		
		Treatments t= null;
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM Treatments WHERE ID=" + id;
		
			ResultSet rs = stmt.executeQuery(sql);
			
			Integer p_id = rs.getInt("id");
			String name = rs.getString("name");
			String description = rs.getString("description"); 
			Integer durationInDays = rs.getInt("duration");
			
			t = new Treatments (p_id,name,description,durationInDays);
		    
		    rs.close();
		    stmt.close();
		    
		}catch(Exception e) {e.printStackTrace();}
		
		
		return t;
	}
	
	

	public void addTreatment(Treatments treatment) {

	    try {
		    String sql = "INSERT INTO Treatments (treatmentID, name, description, durationInDays) VALUES (?, ?, ?, ?)";
	    
	         PreparedStatement pstmt = manager.getConnection().prepareStatement(sql); 

	        pstmt.setInt(1, treatment.getTreatmentID());
	        pstmt.setString(2, treatment.getName());
	        pstmt.setString(3, treatment.getDescription());
	        pstmt.setInt(4, treatment.getDurationInDays());

	        pstmt.executeUpdate();
	        pstmt.close();
	


}catch(SQLException e) {
	e.printStackTrace();
}
	}
	
	public void updateTreatment(Treatments treatment) {
	    String sql = "UPDATE Treatments SET name = ?, description = ?, durationInDays = ? WHERE treatmentID = ?";
	    try {
	    	
	    
	         PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);

	        pstmt.setString(1, treatment.getName());
	        pstmt.setString(2, treatment.getDescription());
	        pstmt.setInt(3, treatment.getDurationInDays());
	        pstmt.setInt(4, treatment.getTreatmentID());

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteTreatment(int id) {
	    String sql = "DELETE FROM Treatments WHERE treatmentID = ?";
	    try {
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setInt(1, id);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<Treatments> searchTreatmentByPatientName(String patientName) {
	    List<Treatments> treatmentList = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM Treatments WHERE name LIKE ? ORDER BY name";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setString(1, "%" + patientName + "%");
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	Integer p_id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description"); 
				Integer durationInDays = rs.getInt("duration");
				//como añadimos aquí el doctor
			
				Treatments treatments = new Treatments (p_id,name,description,durationInDays);
	            

	 
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
	public Treatments getTreatmentById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Treatments> searchTreatmentsByPatientName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Treatments> searchTreatmentsByDoctorId(int doctorId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Treatments> searchTreatmentsByStartDate(java.util.Date startDate, java.util.Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
	
