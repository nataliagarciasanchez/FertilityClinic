package FertilityClinicInterfaces;
import java.io.File;
import java.util.*;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;
import FertilityClinicPOJOs.Stock;

public interface DoctorManager {
	
	public void createDoctor(Doctor d);
	public List<Doctor> getListOfDoctors();
	public Doctor searchDoctorById (Integer Id);
	public Doctor viewMyInfo(Integer id);
	public void removeDoctorById(Integer id);
	public void assignPatientToDoctor(int doctorId, int patientId);
	public void removePatientFromDoctor(int doctorId, int patientId);
	public List<Doctor> searchDoctorByName(String name);
	public Doctor getDoctorByEmail(String email);
	public void modifyDoctorInfo(int doctorId, String email, int phone, String name, Speciality speciality, byte[] pdfBytes);
	
}
