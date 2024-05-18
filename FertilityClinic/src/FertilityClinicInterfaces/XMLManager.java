package FertilityClinicInterfaces;

import java.io.File;

import FertilityClinicPOJOs.Patient;

public interface XMLManager {

	public void manager2xml(Integer id);
	public Patient xml2Patient(File xml);
}
