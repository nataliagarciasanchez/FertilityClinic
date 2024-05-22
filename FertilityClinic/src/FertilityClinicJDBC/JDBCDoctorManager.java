package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Speciality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class JDBCDoctorManager implements DoctorManager {
	private JDBCManager manager;
    private SpecialityManager specialityManager;

    public JDBCDoctorManager(JDBCManager manager, SpecialityManager specialityManager) {
        this.manager = manager;
        this.specialityManager = specialityManager;
    }
	
	public void createDoctor(Doctor d) {
			
			try {
				String sql= "INSERT INTO doctors (email, phone, name, speciality_id)"
						+ "VALUES (?,?,?,?)";
			
				PreparedStatement p = manager.getConnection().prepareStatement(sql);
			    p.setString(1, d.getEmail());
			    p.setInt(2, d.getPhone());
			    p.setString(3, d.getName());
			    p.setInt(4, d.getSpeciality().getId());
			    
			    
				p.executeUpdate();
				p.close();
				
			}catch(SQLException e ) {
			e.printStackTrace();
			
			}
			
			}
	
	
	

	 @Override
	    public List<Doctor> getListOfDoctors() {
	        List<Doctor> doctors = new ArrayList<>();
	        try {
	            String sql = "SELECT * FROM doctors";
	            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                Integer id = rs.getInt("ID");
	                String email = rs.getString("email");
	                Integer phone = rs.getInt("phone");
	                String name = rs.getString("name");
	                Integer specialityId = rs.getInt("speciality_id");

	                Speciality speciality = specialityManager.getSpecialityById(specialityId);

	                Doctor doctor = new Doctor(id, email, phone, name, speciality);
	                doctors.add(doctor);
	            }

	            rs.close();
	            stmt.close();

	        } catch (SQLException e) {
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
	            speciality = specialityManager.getSpecialityById(speciality_id);
	           
	             doctor = new Doctor (d_id,email,phone,name,speciality);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return doctor;
	}
		
		public List<Doctor> searchDoctorByName(String name) {
		    List<Doctor> doctors = new ArrayList<>();
		    Speciality speciality=null;
		    try {
		        String sql = "SELECT * FROM doctors WHERE name LIKE ?";
		        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
		        stmt.setString(1, "%" + name + "%");
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		        	Integer d_id = rs.getInt("ID");
		            String email = rs.getString("email");
		            Integer phone = rs.getInt("phone");
		            String n = rs.getString("name");
	                Integer speciality_id = rs.getInt("speciality_id");
		            speciality = specialityManager.getSpecialityById(speciality_id);
		           
		            doctors.add( new Doctor (d_id,email,phone,n,speciality));
		        
		        }
		        rs.close();
		        stmt.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return doctors;
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

	
		
	public void assignPatientToDoctor(int doctorId, int patientId) {
	    String sql = "INSERT INTO treats (doctor_id, patient_id) VALUES (?, ?)";
	    try (PreparedStatement p = manager.getConnection().prepareStatement(sql);) {
	        p.setInt(1, doctorId);
	        p.setInt(2, patientId);
	        p.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	

	public void removePatientFromDoctor(int doctorId, int patientId) {
	    String sql = "DELETE FROM treats WHERE doctor_id = ? AND patient_id = ?";
	    try (PreparedStatement p = manager.getConnection().prepareStatement(sql);) {
	        p.setInt(1, doctorId);
	        p.setInt(2, patientId);
	        p.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	

	
}
