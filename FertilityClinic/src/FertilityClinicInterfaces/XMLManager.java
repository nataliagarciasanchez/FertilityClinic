package FertilityClinicInterfaces;

import java.io.File;

import FertilityClinicPOJOs.Patient;

public interface XMLManager {

	public Patient xml2Patient(File xml);
	public void owner2xml(Integer id);
}
