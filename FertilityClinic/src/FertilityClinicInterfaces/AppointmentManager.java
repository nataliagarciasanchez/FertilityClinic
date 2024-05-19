package FertilityClinicInterfaces;

import FertilityClinicPOJOs.Appointment;

public interface AppointmentManager {
	public void viewAppointment();
	public void modifyAppointment();
	void bookAppointment(Appointment ap);
	void deleteAppointment(int appointmentId);
	 
}
