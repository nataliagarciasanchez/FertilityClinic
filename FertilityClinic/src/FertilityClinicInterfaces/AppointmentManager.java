package FertilityClinicInterfaces;

import java.util.ArrayList;

import FertilityClinicPOJOs.Appointment;

public interface AppointmentManager {
	public ArrayList<Appointment> viewAppointment(int patientId);
	public void bookAppointment(Appointment ap);
	public void deleteAppointment(int appointmentId);
	public void modifyAppointment(Appointment ap);
	 
}
