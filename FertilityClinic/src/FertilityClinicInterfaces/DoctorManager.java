package FertilityClinicInterfaces;

import FertilityClinicPOJOs.Patient;

public interface DoctorManager {
	public void viewStock();
	public void askForStock();
	public void viewPatients();
	public void searchPatientById(Integer Id);
	public void modifyPatientInfo();
	public void addPatient (Patient patient);
	public void removePatientById (Integer id);
	public void viewAppointment();
	public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();

}
