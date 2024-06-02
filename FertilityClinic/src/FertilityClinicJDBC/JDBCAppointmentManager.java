package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.TreatmentManager;
import FertilityClinicPOJOs.Appointment;

public class JDBCAppointmentManager implements AppointmentManager{
	
	    private JDBCManager manager;
	    private DoctorManager doctorManager; 
	    private PatientManager patientManager;

	    
	    public JDBCAppointmentManager (JDBCManager manager, DoctorManager doctormanager, PatientManager patientManager) {
	    	this.manager = manager;
	    	this.doctorManager = doctormanager;
	    	this.patientManager = patientManager;
	    }
	    
	 
	    @Override
	    public ArrayList<Appointment> viewAppointment(int patientId) {
	        ArrayList<Appointment> appointments = new ArrayList<>();
	        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
	        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql)) {
	            stmt.setInt(1, patientId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String description = rs.getString("description");
	                    String timeStr = rs.getString("time");
	                    String dateStr = rs.getString("date");
	                    LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
	                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
	                    
	                    int doctorId = rs.getInt("doctor_id");
	                    
	                    appointments.add(new Appointment(id, patientId, description, time, date, doctorId));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return appointments;
	    }
	    
	    @Override
	    public ArrayList<Appointment> viewAppointmentByDoctorId(int doctorId) {
	        ArrayList<Appointment> appointments = new ArrayList<>();
	        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
	        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql)) {
	            stmt.setInt(1, doctorId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    int patientId = rs.getInt("patient_id");
	                    String description = rs.getString("description");
	                    String timeStr = rs.getString("time");
	                    String dateStr = rs.getString("date");
	                    LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
	                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);

	                    appointments.add(new Appointment(id, patientId, description, time, date, doctorId));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return appointments;
	    }


	    @Override
	    public void bookAppointment(Appointment ap) {
	        String sql = "INSERT INTO appointments (patient_id, description, time, date, doctor_id) VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement prep = manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            prep.setInt(1, ap.getPatientId());
	            prep.setString(2, ap.getDescription());
	            prep.setString(3, ap.getTime().format(DateTimeFormatter.ISO_LOCAL_TIME)); // LocalTime to String
	            prep.setString(4, ap.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)); // LocalDate to String
	            prep.setInt(5, ap.getDoctorId());
	            prep.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    @Override 
	    public void deleteAppointment(int appointmentId) {
	        String sql = "DELETE FROM appointments WHERE id = ?";

	        try {
	        	PreparedStatement prep = manager.getConnection().prepareStatement(sql);
	        	prep.setInt(1,appointmentId);
	        	prep.executeUpdate();
	        	prep.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	   
	   
	    @Override
	    public void updateAppointment(Appointment appointment) {
	        String sql = "UPDATE appointments SET time = ?, date = ?, doctor_id = ? WHERE id = ?";
	        try (PreparedStatement prep = manager.getConnection().prepareStatement(sql)) {
	            prep.setString(1, appointment.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	            prep.setString(2, appointment.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
	            prep.setInt(3, appointment.getDoctorId());
	            prep.setInt(4, appointment.getId());

	            int rowsAffected = prep.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Appointment updated successfully.");
	            } else {
	                System.out.println("Failed to update appointment. Appointment with ID " + appointment.getId() + " not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    @Override
	    public ArrayList<Appointment> getCurrentAppointments(int patientId) {
	        ArrayList<Appointment> appointments = new ArrayList<>();
	        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
	        try (PreparedStatement prep = manager.getConnection().prepareStatement(sql)) {
	            prep.setInt(1, patientId);
	            try (ResultSet rs = prep.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String timeStr = rs.getString("time");
	                    String dateStr = rs.getString("date");
	                    LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
	                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
	                    int doctorId = rs.getInt("doctor_id");
	                    String description = rs.getString("description");

	                    appointments.add(new Appointment(id, patientId, description, time, date, doctorId));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return appointments;
	    }



}
