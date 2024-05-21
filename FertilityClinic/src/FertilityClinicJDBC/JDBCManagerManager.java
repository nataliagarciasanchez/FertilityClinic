package FertilityClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import FertilityClinicInterfaces.ManagerManager;
import FertilityClinicPOJOs.Manager;

public class JDBCManagerManager implements ManagerManager  {
	private JDBCManager manager;
	public void addManager(Manager m) {
		
		try {
			String sql= "INSERT INTO managers (email, phone, name)"
					+ "VALUES (?,?,?)";
		
			PreparedStatement p = manager.getConnection().prepareStatement(sql);
		    p.setString(1, m.getEmail());
		    p.setInt(2, m.getPhone());
		    p.setString(3, m.getName());
		    
		    
			p.executeUpdate();
			p.close();
			
		}catch(SQLException e ) {
		e.printStackTrace();
		
		}
		
		}

	@Override
	public void modifyInfo(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
