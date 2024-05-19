package FertilityClinicInterfaces;



import java.util.List;

import FertilityClinicPOJOs.Patient;

public interface PatientManager {
	
	public void viewMyInfo();
	public List<Patient> getListOfPatients();
	public void addPatient( Patient patient);
	public Patient searchPatientById(Integer id); 
	public void modifyPatientInfo(Integer patientId, String email, Integer phoneN, String name);
	public void removePatientById (Integer id);
	public void assingPatient2Doctor(Integer patient_id, Integer doctor_id);
	
	
}
