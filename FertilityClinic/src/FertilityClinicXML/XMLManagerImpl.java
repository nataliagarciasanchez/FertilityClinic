package FertilityClinicXML;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.XMLManager;
import FertilityClinicJDBC.JDBCManager;
import FertilityClinicJDBC.JDBCDoctorManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;

public class XMLManagerImpl implements XMLManager {
	JDBCManager manager;
	DoctorManager doctormanager;
	PatientManager patientmanager; 

	@Override
	public void Doctor2xml(Integer id) {
		// TODO Auto-generated method stub
		Doctor d = null;
		List<Patient> patients = new ArrayList<Patient>();
		manager = new JDBCManager();
		//doctormanager = new JDBCDoctorManager(manager);
		//patientmanager = new JDBCPatientManager(manager);
		
		try {
			//Do a sql query to get the doctor by id
			//d = doctormanager.searchDoctorById(id); Falta crear el metodo de searchDoctorById(id)
			
		} catch() {
			
		}
	}

	@Override
	public Patient xml2Pet(File xml) {
		// TODO Auto-generated method stub
		return null;
	}

}
