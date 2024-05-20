package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;

public class JDBCSpecialityManager implements SpecialityManager {
	
	private JDBCManager manager;
	
	public JDBCSpecialityManager(JDBCManager manager) {
		this.manager=manager;
	}

	@Override
	public void createSpeciality(Speciality s) {
		// TODO Auto-generated method stub
		
		try {
			String sql= "INSERT INTO specialities ( name )"
					+ "VALUES (?)";
		
			PreparedStatement p = manager.getConnection().prepareStatement(sql);
		    p.setString(1, s.getName());
		    
		    
			p.executeUpdate();
			p.close();
			
		}catch(SQLException e ) {
		e.printStackTrace();
		
		}
		
		}

	

	@Override
	public void updateSpeciality(Speciality s) {
		String sql = "UPDATE specialities SET name = ? WHERE id = ?";

	    try {
	        PreparedStatement prep = manager.getConnection().prepareStatement(sql);

	     
		    	prep.setString(1, s.getName());
	       
	        prep.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
}
		
	

	@Override
	public void deleteSpeciality(Integer id) {
		// TODO Auto-generated method stub
		try {
			String sql = "DELETE FROM specialities WHERE id=?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, id);
			
			prep.executeUpdate();			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public Speciality getSpecialityById(Integer id) {
		Speciality speciality=null;

    try {
        String sql = "SELECT * FROM specialities WHERE ID=?";
        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Integer s_id = rs.getInt("ID");
            String name = rs.getString("name");
           
             speciality = new Speciality (s_id,name);
        }

        rs.close();
        stmt.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return speciality;
	}
		


	@Override
	public List<Speciality> getAllSpecialities() {
		List<Speciality> specialities= new ArrayList<Speciality>();
	
	try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM specialities";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				 	Integer s_id = rs.getInt("id");
		            String name = rs.getString("name");
		            Speciality s = new Speciality (s_id,name);
				specialities.add(s);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		
		return specialities;
	}
		
		

}
