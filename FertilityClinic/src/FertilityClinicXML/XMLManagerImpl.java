package FertilityClinicXML;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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
	public void doctor2xml(Integer id) {
		// TODO Auto-generated method stub
		Doctor d = null;
		List<Patient> patients = new ArrayList<Patient>();
		manager = new JDBCManager();
		doctormanager = new JDBCDoctorManager(manager);//Falta crear el metodo de searchDoctorById(id)
		patientmanager = new JDBCPatientManager(manager);
		
		try {
			//Do a sql query to get the doctor by id
			d = doctormanager.searchDoctorById(id); 
			patients=patientmanager.getListOfPatients();
			d.setPatients(patients);
			
			JAXBContext jaxbContext= JAXBContext.newInstance(Doctor.class);
			Marshaller marshaller=jaxbContext.createMarshaller();
			File file=new File("./xmls/Doctor.xml");
			marshaller.marshal(d, file);
		} catch(Exception e) {
			
		}
	}

	@Override
	public Doctor xml2Doctor(File xml) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
