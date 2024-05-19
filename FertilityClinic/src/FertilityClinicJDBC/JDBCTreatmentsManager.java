package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Treatments;

public class JDBCTreatmentsManager {
	
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

}
