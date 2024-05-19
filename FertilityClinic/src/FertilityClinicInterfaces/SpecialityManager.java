package FertilityClinicInterfaces;

import java.util.List;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Speciality;

public interface SpecialityManager {
    void createSpeciality(String name, List<Doctor> doctors);
    void updateSpeciality(int id, String name, List<Doctor> doctors);
    void deleteSpeciality(int id);
    Speciality getSpecialityById(int id);
    List<Speciality> getAllSpecialities();
}
