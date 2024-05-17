package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

//TODO los override dan problemas

public class JDBCDoctorManager {
	private JDBCManager manager;
	
	public JDBCDoctorManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	public void createDoctor(Doctor d) {
		try {
			String sql ="INSERT INTO Doctor (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
					+ "VALUES(dejar claro los atributos)";
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
    public void viewStock() {
		try {
			
			//TODO no hay ningun atributo de Stock, como lo cojo?? de que tabla??
		
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	
	@Override
	public void addPatient (Patient p) {
		try {
			String sql ="INSERT INTO patients (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
						+"VALUES(dejar claro los atributos)";
			
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, p.getId());
			prep.setString(2, p.getName());
			prep.setInt(3, p.getAge());
			prep.setDouble(4, p.getHeight());
			prep.setDouble(5, p.getWeight());
			prep.setInt(6,  p.getPhoneN());
			//prep.setString(7, p.getBloodType());
			prep.setInt(8, p.getBanckAc());
			//prep.setString(9, p.getGender());
			prep.setInt(10, p.getAge());
			//TODO lineas 29 y 31 dan error porque deberia ser setBloodType y setGender pero no deja
			
			prep.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
<<<<<<< HEAD
	*/


	@Override
	public void removePatientById (Integer patient_id) {
		try {
			String sql ="DELETE FROM patients WHERE id = ?";
			
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, patient_id);
			
			prep.executeUpdate();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * MÉTODOS A IMPLEMENTAR:
 
    public void viewStock();
	public void askForStock();
	public List<Patient> getListOfPatients();
	public void searchPatientById(Integer Id);
	public void modifyPatientInfo();
	public void viewAppointment();
	public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();
 */

