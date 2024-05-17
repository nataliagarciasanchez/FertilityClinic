package FertilityClinicInterfaces;



import java.util.List;

import FertilityClinicPOJOs.Patient;

public interface PatientManager {
	
	//public void viewMyInfo();
	//public void manageInfo();
	//public void manageAppointment();
	//public void manageTransactions();
	//public void viewAppointment();
	//public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();
	List<Patient> getListOfPatients();// asi vemos la info completa del paciente desde la base de datos
	void addPatient( String email, Integer phoneN, String name, double height, double weight, String bloodType, String gender, Integer age);
	Patient foundPatientById(Integer id);

}
