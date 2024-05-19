package FertilityClinicInterfaces;

import FertilityClinicPOJOs.Appointment;

public interface AppointmentManager {
	public void viewAppointment();
	public void bookAppointment(Appointment ap);
	public void deleteAppointment(int appointmentId);
	public void modifyAppointment(Appointment ap);
	 
}
