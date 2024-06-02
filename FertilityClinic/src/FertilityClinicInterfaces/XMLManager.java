package FertilityClinicInterfaces;

import java.io.File;
import FertilityClinicPOJOs.Patient;

public interface XMLManager {

	public Patient xml2Patient(File xml);
	public void doctor2xml(Integer id);
	public Doctor xml2Doctor(File xml);
	public void patient2xml(Integer id);
}
