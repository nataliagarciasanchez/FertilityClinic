package FertilityClinicInterfaces;
import java.util.*;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Stock;

public interface DoctorManager {
	
	public void createDoctor(Doctor d);
	public List<Doctor> getListOfDoctors();
	public Doctor searchDoctorById (Integer Id);
    // vieInfo doctor
	public void removeDoctorById(Integer id);
	public void assignPatientToDoctor(int doctorId, int patientId);
	public void removePatientFromDoctor(int doctorId, int patientId);
	public List<Doctor> searchDoctorByName(String name);
		//hola
}
