package FertilityClinicInterfaces;
import java.util.*;
import FertilityClinicPOJOs.Doctor;
public interface ManagerManager {
	
	public void buyStock();
	public void addStock();
	public void modifyStock();
	public List<Doctor> getListOfDoctors;
	public Doctor searchDoctorById (Integer Id);
	public void modifyInfo (Integer id);
	public void addDoctor(Doctor d);
	public void removeDoctorById(Integer id);
	public void viewAppointment();
	public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();
	
	
	
	
	
	

}
