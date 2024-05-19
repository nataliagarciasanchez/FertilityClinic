package FertilityClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import FertilityClinicInterfaces.AppointmentManager;

public class JDBCAppointmentManager implements AppointmentManager{
	
	  	/*private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdatabase";
	    private static final String USER = "yourusername";
	    private static final String PASS = "yourpassword";
	    */
	    private JDBCManager manager;
	    
	    public JDBCAppointmentManager (JDBCManager manager) {
	    	this.manager = manager;
	    }
	    
	    @Override
	    public void viewAppointment() {

	        try {
		        String sql = "SELECT * FROM appointments";
		        Statement stmt = manager.getConnection().createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String title = rs.getString("title");
	                String description = rs.getString("description");
	                Date date = rs.getDate("date");
	                Time time = rs.getTime("time");

	                System.out.println("ID: " + id);
	                System.out.println("Title: " + title);
	                System.out.println("Description: " + description);
	                System.out.println("Date: " + date);
	                System.out.println("Time: " + time);
	                System.out.println("---------------");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void bookAppointment() {

	        try {
		        String sql = "INSERT INTO appointments (title, description, date, time) VALUES (?, ?, ?, ?)";
				PreparedStatement prep = manager.getConnection().prepareStatement(sql);

	            prep.setString(1, "Meeting");
	            prep.setString(2, "Project discussion");
	            prep.setDate(3, Date.valueOf("2023-05-19"));
	            prep.setTime(4, Time.valueOf("10:00:00"));

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void deleteAppointment() {
	        String query = "DELETE FROM appointments WHERE id = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            // Dummy data for demonstration
	            int appointmentId = 1;
	            preparedStatement.setInt(1, appointmentId);

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Appointment deleted successfully.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void modifyAppointment() {
	        String query = "UPDATE appointments SET title = ?, description = ?, date = ?, time = ? WHERE id = ?";

	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            // Dummy data for demonstration
	            int appointmentId = 1;
	            preparedStatement.setString(1, "Updated Meeting");
	            preparedStatement.setString(2, "Updated description");
	            preparedStatement.setDate(3, Date.valueOf("2023-06-20"));
	            preparedStatement.setTime(4, Time.valueOf("11:00:00"));
	            preparedStatement.setInt(5, appointmentId);

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Appointment modified successfully.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
