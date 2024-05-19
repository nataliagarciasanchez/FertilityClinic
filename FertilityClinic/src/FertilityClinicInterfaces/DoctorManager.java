package FertilityClinicInterfaces;
import java.util.*;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;

public interface DoctorManager {
	
	public void createDoctor(Doctor d);
	
	public void viewStock(); //lo hacemos con searchStockbyName (String name)
	public void askForStock();
	public List<Patient> getListOfPatients();
	public void searchPatientById(Integer Id);
	public void modifyPatientInfo();
	public void addPatient (Patient patient);
	public void removePatientById (Integer id);
	public void viewAppointment();
	public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();

}
