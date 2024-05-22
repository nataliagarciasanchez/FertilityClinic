package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import FertilityClinicInterfaces.ManagerManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Manager;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;
import FertilityClinicPOJOs.Treatments;

public class JDBCManagerManager implements ManagerManager  {
	private JDBCManager manager;
	
	public JDBCManagerManager (JDBCManager manager) {
		this.manager=manager;
	}
	public void addManager(Manager m) {
		
		try {
			String sql= "INSERT INTO managers (email, phone, name) VALUES (?,?,?)";
		
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

	
	public Manager viewMyInfo(Integer managerId) {
	    Manager mAnager = null;
	    try {
	        String sql = "SELECT * FROM managers WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, managerId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer id = rs.getInt("id");
	            String email = rs.getString("email");
	            Integer phone = rs.getInt("phone");
	            String name = rs.getString("name");
	            mAnager =new Manager(id,email,phone,name);
	        }

	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return mAnager;
	}
	@Override
	public void modifyManagerInfo(Integer managerId, String email, Integer phoneN, String name) {
		try {
	        String sql = "UPDATE managers SET email=?, phone=?, name=? WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setInt(2, phoneN);
	        stmt.setString(3, name);
	        stmt.setInt(4, managerId);
	        
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Manager information updated successfully.");
	        } else {
	            System.out.println("Failed to update patient information. Manager with ID " + managerId + " not found.");
	        }

	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	@Override
	public Manager getManagerByEmail(String email) {
	    Manager mAnager = null;
	    
	    try {
	        String sql = "SELECT * FROM managers WHERE email = ?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            Integer id = rs.getInt("ID");	          
	            Integer phoneN = rs.getInt("phone");
	            String name = rs.getString("name");
	            
	            mAnager = new Manager(id, email, phoneN, name);
	        }
	        
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return mAnager;
	}




}
