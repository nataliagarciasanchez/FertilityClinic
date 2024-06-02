package FertilityClinicInterfaces;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;

public interface PatientManager {
	
	public Patient viewMyInfo(Integer id);
	public List<Patient> getListOfPatients();
	public Patient getPatientByEmail(String email);
	public void addPatient( Patient patient);
	public Patient searchPatientById(Integer id); 
	public void modifyPatientInfo(Integer patientId, String email, Integer phoneN, String name, Double height, Double weight);
	public void removePatientById (Integer id);
	public List<Patient> getPatientsByDoctorId(int doctorId);
	public List<Patient> getAvailablePatientsForDoctor(int doctorId);
	public void assignTreatmentToPatient(int patientId, int treatmentId) ;
	public Patient getPatientById(int patientId);
}

//Coment