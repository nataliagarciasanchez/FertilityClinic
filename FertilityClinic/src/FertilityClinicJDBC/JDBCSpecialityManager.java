package FertilityClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicInterfaces.TreatmentsManager;
import FertilityClinicPOJOs.Doctor;

public class JDBCSpecialityManager implements SpecialityManager {
	private JDBCManager manager;

	public JDBCSpecialityManager (JDBCManager m) {
		this.manager = m;
	}

	//TODO puedo pasarle un a lista por parametro?? o le paso el id del doctor directamente
	@Override
	public void createSpeciality(String name, List<Doctor> doctors) {
		  try {
	            String sql = "INSERT INTO Speciality (name) VALUES (?)";
	            PreparedStatement prep = manager.getConnection().prepareStatement(sql);
	            prep.setString(1, name);
	            prep.executeUpdate();

	            /*
	            ResultSet generatedKeys = prep.getGeneratedKeys();
	            
	            if (generatedKeys.next()) {
	                int specialityId = generatedKeys.getInt(1);
	                insertDoctorsForSpeciality(specialityId, doctors);
	            }
	            */
	            prep.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}
	
	/*
	private void insertDoctorsForSpeciality(int specialityId, List<Doctor> doctors) throws SQLException {
        String sql = "INSERT INTO Doctor (name, speciality_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql);

        for (Doctor doctor : doctors) {
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setInt(2, specialityId);
            preparedStatement.executeUpdate();
        }
    }
    */

	@Override
	public void updateSpeciality(int id, String name, List<Doctor> doctors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpeciality(int specialityId) {
		try {
			String sql ="DELETE FROM specialities WHERE id = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, specialityId);
			prep.executeUpdate();
			prep.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
