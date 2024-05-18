package FertilityClinicInterfaces;



import java.util.List;

import FertilityClinicPOJOs.Patient;

public interface PatientManager {
	
	public void viewMyInfo();
	public List<Patient> getListOfPatients();// asi vemos la info completa del paciente desde la base de datos
	public void addPatient( Patient patient);
	public Patient searchPatientById(Integer id); //cambiar nombre en JDBC
	public void modifyPatientInfo();
	public void removePatientById (Integer id);
	public void assingPatient2Doctor(Integer patient_id, Integer doctor_id);
	
	
}
