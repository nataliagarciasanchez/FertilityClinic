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
	    }
}
