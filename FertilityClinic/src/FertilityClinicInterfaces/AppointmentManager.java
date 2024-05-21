package FertilityClinicInterfaces;

import java.util.ArrayList;

import FertilityClinicPOJOs.Appointment;

public interface AppointmentManager {
	public ArrayList<Appointment> viewAppointment (int patient_id);
	public void bookAppointment(Appointment ap);
	public void deleteAppointment(int appointmentId);
	public void modifyAppointment(Appointment ap);
	 
}
