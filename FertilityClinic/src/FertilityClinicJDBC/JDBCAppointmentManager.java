package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicPOJOs.Appointment;

public class JDBCAppointmentManager implements AppointmentManager{
	
	    private JDBCManager manager;
	    
	    public JDBCAppointmentManager (JDBCManager manager) {
	    	this.manager = manager;
	    }
	    
	    
	    @Override
	    public ArrayList<Appointment> viewAppointment(int patientId) {
	        ArrayList<Appointment> appointments = new ArrayList<>();
	        try {
	            String sql = "SELECT * FROM appointments WHERE patient_id = ?";
	            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	            stmt.setInt(1, patientId);  // Set the patientId dynamically
	            ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next()) {
	                Integer id = rs.getInt("id");
	                Integer patientIdFromDB = rs.getInt("patient_id");  // Adjust the column name if needed
	                String description = rs.getString("description");
	                Time time = rs.getTime("time");
	                Date date = rs.getDate("date");
	                Integer doctorId = rs.getInt("doctorId");
	                
	                Appointment ap = new Appointment(id, patientIdFromDB, description, time, date, doctorId);
	                appointments.add(ap);
	            }
	            rs.close();
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return appointments;
	    }

	     
	    @Override
	    public void bookAppointment(Appointment ap) {
	        String sql = "INSERT INTO appointments ((id, patientId, description, time, date, doctorId) VALUES (?, ?, ?, ?, ?, ?)";
	        
	        try {
				PreparedStatement prep = manager.getConnection().prepareStatement(sql);

	            prep.setInt(1, ap.getId());
	            prep.setInt(2, ap.getPatientId());
				prep.setString(3, ap.getDescription());
				String time= ap.getTime().toString();
				prep.setString(4,time);
				String date = ap.getDate().toString();
				prep.setString(5, date);
	            prep.setInt(6, ap.getDoctorId());
	            prep.executeUpdate();
				prep.close();
				
	        } catch (Exception e) {
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
/*
	    @Override
	    public void modifyAppointment(Appointment ap) {
	    	String sql = "UPDATE appointments SET patientId = ?, description = ?, time = ?, date = ?, doctorId = ? WHERE id = ?";

	    	    try {
	    	        PreparedStatement prep = manager.getConnection().prepareStatement(sql);

	    	        prep.setInt(1, ap.getPatientId());
	    	        prep.setString(2, ap.getDescription());
	    	        prep.setTime(3, ap.getTime());
	    	        prep.setDate(4, (Date) ap.getDate());
	    	        prep.setInt(5, ap.getDoctorId());
	    	        prep.setInt(6, ap.getId());
	    	        prep.executeUpdate();

	    	    } catch (SQLException e) {
	    	        e.printStackTrace();
	    	    }
	    }//hola
	    
	   */
	    
	    @Override
	    public void updateAppointment(Appointment appointment) {
	        String sql = "UPDATE appointments SET date = ?, time = ?, doctor_id = ? WHERE id = ?";
	        try {
	            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	            
	            // Convert String to java.sql.Date
	            java.sql.Date sqlDate = java.sql.Date.valueOf(appointment.getDate().toString());
	            
	            // Convert String to java.sql.Time
	            java.sql.Time sqlTime = java.sql.Time.valueOf(appointment.getTime().toString());
	            
	            stmt.setDate(1, sqlDate);
	            stmt.setTime(2, sqlTime);
	            stmt.setInt(3, appointment.getDoctorId());
	            stmt.setInt(4, appointment.getId());
	            
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Appointment updated successfully.");
	            } else {
	                System.out.println("Failed to update appointment. Appointment with ID " + appointment.getId() + " not found.");
	            }
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    

/*

		@Override
		public ArrayList<Appointment> getCurrentAppointments(int patientId) {
	        ArrayList<Appointment> appointments = new ArrayList<>();
	        String sql = "SELECT * FROM appointments WHERE patient_id = ?";

	        try{
	        	
	        	PreparedStatement prep = manager.getConnection().prepareStatement(sql);
	        	prep.setInt(1, patientId);
	        
	            ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String date = rs.getString("date");
	                String time = rs.getString("time");
	                int doctorId = rs.getInt("doctor_id");
	                String doctorName = doctorManager.searchDoctorById(appointment.getDoctorId()); // Assuming you have a method to get the doctor's name by their ID

	                Appointment appointment = new Appointment(id, date, , doctorName);
	                appointments.add(appointment);
	                Appointment ap = new Appointment(id, patientIdFromDB, description, time, date, doctorId);
	                appointments.add(ap);
	            }

	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return appointments;
	    }*/


		@Override
		public ArrayList<Appointment> getCurrentAppointments(int patientId) {
			// TODO Auto-generated method stub
			return null;
		}


}
