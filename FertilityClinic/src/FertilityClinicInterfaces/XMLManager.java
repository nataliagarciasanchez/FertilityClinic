package FertilityClinicInterfaces;

import java.io.File;


import FertilityClinicPOJOs.Doctor;

public interface XMLManager {

	public Doctor xml2Doctor(File xml);
	public void doctor2xml(Integer id);
}
