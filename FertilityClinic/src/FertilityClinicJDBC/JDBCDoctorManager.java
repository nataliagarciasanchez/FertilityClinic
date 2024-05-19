package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;
import FertilityClinicPOJOs.Treatments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;



public class JDBCDoctorManager implements DoctorManager {
	private JDBCManager manager;
	private Speciality specialitymanager;
	
	public JDBCDoctorManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	public void createDoctor(Doctor d) {
			
			try {
				String sql= "INSERT INTO doctors (email, phone, name, speciality_id )"
						+ "VALUES (?,?,?,?)";
			
				PreparedStatement p = manager.getConnection().prepareStatement(sql);
			    p.setString(1, d.getEmail());
			    p.setInt(2, d.getPhone());
			    p.setString(3, d.getName());
			    p.setInt(4, d.getSpeciality().getId());
			    for(Patient patient: d.getPatients()) {
			    	p.setInt(5,patient.getId());
			    }
			    
				p.executeUpdate();
				p.close();
				
			}catch(SQLException e ) {
			e.printStackTrace();
			
			}
			
			}
	
	
	

		public List<Doctor> getListOfDoctors()	 {
		
		List<Doctor> doctors= new ArrayList<Doctor>();
		Speciality speciality=null;
	try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM doctors";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				 	Integer d_id = rs.getInt("ID");
		            String email = rs.getString("email");
		            Integer phone = rs.getInt("phoneNumber");
		            String name = rs.getString("name");
		            Integer speciality_id = rs.getInt("speciality_id");
		            speciality = specialitymanager.getSpecialityById(speciality_id);
		            
		          
		            Doctor d = new Doctor (d_id,email,phone,name,speciality);
				doctors.add(d);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		
		return doctors;
	}
		
		
		
		public Doctor searchDoctorById(Integer Id)	{
			Doctor doctor=null;
			Speciality speciality=null;

	    try {
	        String sql = "SELECT * FROM doctors WHERE ID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, Id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer d_id = rs.getInt("ID");
	            String email = rs.getString("email");
	            Integer phone = rs.getInt("phoneNumber");
	            String name = rs.getString("name");
                Integer speciality_id = rs.getInt("speciality_id");
	            speciality = specialitymanager.getSpecialityById(speciality_id);
	           
	             doctor = new Doctor (d_id,email,phone,name,speciality);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return doctor;
	}
		
		
		
	public void removeDoctorById(Integer id) {
		
		try {
			String sql = "DELETE FROM doctors WHERE id=?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, id);
			
			prep.executeUpdate();			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

	
}
