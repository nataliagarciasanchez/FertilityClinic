package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Speciality;

public interface SpecialityManager {
	public void createSpeciality(Speciality speciality);
    public void updateSpeciality(Speciality speciality);
    public void deleteSpeciality(Integer id);
    public Speciality getSpecialityById(Integer id);
    public List<Speciality> getAllSpecialities();
}
