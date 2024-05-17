package FertilityClinicInterfaces;

import java.io.File;

import FertilityClinicPOJOs.Patient;

public interface XMLManager {

	public void Doctor2xml(Integer id);
	public Patient xml2Pet(File xml);
}
