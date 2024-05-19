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

}
