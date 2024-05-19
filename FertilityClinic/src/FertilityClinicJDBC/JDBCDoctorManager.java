package FertilityClinicJDBC;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//TODO los override dan problemas

public class JDBCDoctorManager {
	private JDBCManager manager;
	
	public JDBCDoctorManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	public void createDoctor(Doctor d) {
		try {
			String sql ="INSERT INTO Doctor (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
					+ "VALUES(dejar claro los atributos)";
			PreparedStatement prep=manager.getConnection().prepareStatement(sql);
		
			prep.setInt(1, d.getId());
			prep.setString(2, d.getName());
			prep.setString(3, d.getEmail());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Stock> viewStock() {
		  List<Stock> listStock = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM Stock";

			Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock item = new Stock(productId, productName, category, quantity, expiryDate);
                listStock.add(item);
            }
            
            rs.close();
			stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listStock;
    }

	/*
	@Override
	public void addPatient (Patient p) {
		try {
			String sql ="INSERT INTO patients (Patient_id, Name, Age, Height, Weight, Phone, Bloodtype, Email, BankAccountNumber, Gender )"
						+"VALUES(dejar claro los atributos)";
			
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setInt(1, p.getId());
			prep.setString(2, p.getName());
			prep.setInt(3, p.getAge());
			prep.setDouble(4, p.getHeight());
			prep.setDouble(5, p.getWeight());
			prep.setInt(6,  p.getPhoneN());
			//prep.setString(7, p.getBloodType());
			prep.setInt(8, p.getBanckAc());
			//prep.setString(9, p.getGender());
			prep.setInt(10, p.getAge());
			//TODO lineas 29 y 31 dan error porque deberia ser setBloodType y setGender pero no deja
			
			prep.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
<<<<<<< HEAD
	*/


	public void removePatientById (Integer patient_id) {
		try {
			String sql ="DELETE FROM patients WHERE id = ?";
			
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, patient_id);
			
			prep.executeUpdate();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * MÉTODOS A IMPLEMENTAR:
 
	public void askForStock();
	public List<Patient> getListOfPatients();
	public void searchPatientById(Integer Id);
	public void modifyPatientInfo();
	public void viewAppointment();
	public void bookAppointment();
	public void deleteAppointment();
	public void modifyAppointment();
 */

