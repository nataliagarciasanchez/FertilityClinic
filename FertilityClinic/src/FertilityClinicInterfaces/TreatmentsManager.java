package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Treatments;

public interface TreatmentsManager {
	public void addTreatment(Treatments treatment);
    public Treatments getTreatmentById(Integer id);
    public void updateTreatment(Treatments treatment);
    public void deleteTreatment(int id);
    public List<Treatments> searchTreatmentsByPatientName(String name);
    public List<Treatments> searchTreatmentsByDoctorId(int doctorId);
   
    
}