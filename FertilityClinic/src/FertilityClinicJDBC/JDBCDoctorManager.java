package FertilityClinicJDBC;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import java.sql.PreparedStatement;

public class JDBCDoctorManager {
	private JDBCManager manager;
	
	public JDBCDoctorManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	public void createDoctor(Doctor d) {
		try {
			String sql ="INSERT INTO patients (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
					+"VALUES(dejar claro los atributos)";
			PreparedStatement prep=manager.getConnection().prepareStatement(sql);
		
			prep.setInt(1, p.getId());
			prep.setString(2, p.getName());
			prep.setInt(3, p.getAge());
			prep.setDouble(4, p.getHeight());
			prep.setDouble(5, p.getWeight());
			prep.setInt(6,  p.getPhoneN());
			prep.setString(7, p.getBloodType());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	@Override
	public void addPatient (Patient p) {
		try {
			String sql ="INSERT INTO patients (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
						+"VALUES(dejar claro los atributos)";
			PreparedStatement prep=manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, p.getId());
			prep.setString(2, p.getName());
			prep.setInt(3, p.getAge());
			prep.setDouble(4, p.getHeight());
			prep.setDouble(5, p.getWeight());
			prep.setInt(6,  p.getPhoneN());
			prep.setString(7, p.getBloodType());
			
			
		}
		
	}
	*/

}
