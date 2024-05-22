package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;
import FertilityClinicPOJOs.Treatments;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
	
	public Doctor viewMyInfo(Integer doctorId) {
	    Doctor doctor = null;
	    try {
	        String sql = "SELECT doctors.*, specialities.name AS specialityName FROM doctors " +
	                     "LEFT JOIN specialities ON doctors.speciality_id = specialities.id " +
	                     "WHERE doctors.id = ?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, doctorId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer id = rs.getInt("id");
	            String email = rs.getString("email");
	            Integer phone = rs.getInt("phone");
	            String name = rs.getString("name");
	            String specialityName = rs.getString("specialityName");
	            byte[] licensePDF = rs.getBytes("licensePDF");

	            Speciality speciality = new Speciality(rs.getInt("speciality_id"), specialityName);
	            doctor = new Doctor(id, name, phone, email, speciality, licensePDF);
	        } else {
	            System.out.println("Doctor with ID " + doctorId + " not found.");
	        }

	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return doctor;
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
	            Integer phone = rs.getInt("phone");
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
	
	@Override
	public Doctor getDoctorByEmail(String email) {
	    Doctor doctor = null;

	    try {
	        String sql = "SELECT * FROM doctors WHERE email = ?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Integer id = rs.getInt("id");
	            String name = rs.getString("name");
	            Integer phone = rs.getInt("phone");
	            Integer specialityId = rs.getInt("speciality_id");
	            byte[] licensePDF = rs.getBytes("licensePDF");

	            Speciality speciality = specialityManager.getSpecialityById(specialityId); // Assume you have a method to get Speciality

	            doctor = new Doctor(id, email, phone, name, speciality, licensePDF);
	        } else {
	            System.out.println("Doctor with email " + email + " not found.");
	        }

	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return doctor;
	}
	public void modifyDoctorInfo(int doctorId, String email, int phone, String name, Speciality speciality, byte[] pdfBytes) {
	    String sql = "UPDATE doctors SET email = ?, phone = ?, name = ?, speciality_id = ?, license_pdf = ? WHERE id = ?";
	    try {
	    	PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setInt(2, phone);
	        stmt.setString(3, name);
	        stmt.setInt(4, speciality.getId());
	        if (pdfBytes.length > 0) {
	        	stmt.setBytes(5, pdfBytes);
	        } else {
	        	stmt.setNull(5, Types.BLOB);
	        }
	        stmt.setInt(6, doctorId);

	        stmt.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
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
