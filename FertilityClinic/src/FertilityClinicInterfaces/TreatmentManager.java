package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import FertilityClinicPOJOs.Treatment;
import FertilityClinicPOJOs.TreatmentStep;

public interface TreatmentManager {
	public void addTreatment(Treatment treatment);
    public Treatment getTreatmentById(Integer id);
    public void updateTreatment(Treatment treatment);
    public void deleteTreatment(int id);
    public List<Treatment> searchTreatmentsByPatientName(String name);
    public List<Treatment> searchTreatmentsByDoctorId(int doctorId);
    public Map<Integer, Boolean> getStepCompletion(int patientId, int treatmentId);
    public void updateStepCompletion(int patientId, int stepId, boolean isSelected);
    public List<TreatmentStep> getTreatmentSteps(int treatmentId);
    public List<Treatment> getAllTreatments() ;
    
    
}

