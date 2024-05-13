package FertilityClinicJDBC;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Patient;
import java.sql.PreparedStatement;

public class JDBCDoctorManager {
	private JDBCManager manager;
	
	public JDBCDoctorManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	@Override
	public void addPatient (Patient p) {
		try {
			String sql ="INSERT INTO patients (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
						+"VALUES(dejar claro los atributos)";
			PreparedStatement prep=manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, p.getId());
			prep.setString(2, p.getName());
			prep.setInt(3, p.getAge);
		}
		
	}

}
