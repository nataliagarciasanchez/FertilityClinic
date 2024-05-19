package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//TODO los override dan problemas

public class JDBCDoctorManager implements DoctorManager {
	private JDBCManager manager;
	
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

	
	
	
	


	
	@Override
	public List<Doctor> getListOfDoctors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Doctor searchDoctorById(Integer Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDoctorById(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
