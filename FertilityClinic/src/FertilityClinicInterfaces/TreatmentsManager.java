package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Treatments;

public interface TreatmentsManager {
	void addTreatment(Treatments treatment);
    Treatments getTreatmentById(int id);
    void updateTreatment(Treatments treatment);
    void deleteTreatment(int id);
    List<Treatments> searchTreatmentsByPatientName(String name);
    void assignDoctorToTreatment(int treatmentId, int doctorId);
    List<Treatments> getTreatmentsByDoctorId(int doctorId);
    List<Treatments> getTreatmentsByStartDate(Date startDate, Date endDate);
}