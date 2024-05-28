package FertilityClinicXML;

import java.io.File;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.XMLManager;
import FertilityClinicJDBC.JDBCManager;
import FertilityClinicJDBC.JDBCDoctorManager;
import FertilityClinicJDBC.JDBCPatientManager;
import FertilityClinicJDBC.JDBCSpecialityManager;
import FertilityClinicJDBC.JDBCTreatmentsManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;


public class XMLManagerImpl implements XMLManager {
	

	
	JDBCManager manager;
	JDBCDoctorManager doctormanager; 
	JDBCPatientManager patientmanager; 
	JDBCTreatmentsManager treatmentmanager;
	JDBCSpecialityManager specialitymanager;

	@Override
	public void doctor2xml(Integer id) {
		// TODO Auto-generated method stub
		Doctor d = null;
		List<Patient> patients = new ArrayList<Patient>();
		manager = new JDBCManager();
		doctormanager = new JDBCDoctorManager(manager, specialitymanager);// supuestamente asi pero deberian ser pero DoctorManager
		patientmanager = new JDBCPatientManager(manager, treatmentmanager);//
		
		try {
			//Do a sql query to get the doctor by id
			d = doctormanager.searchDoctorById(id); //Falta crear el metodo de searchDoctorById(id)
			patients=patientmanager.getListOfPatients();
			d.setPatients(patients);
			
			JAXBContext jaxbContext= JAXBContext.newInstance(Doctor.class);
			Marshaller marshaller=jaxbContext.createMarshaller();
			File file=new File("Doctor.xml");
			marshaller.marshal(d, file);
			System.out.print(d);
		} catch(Exception e) {
			
		}
	}
	
	@Override
	public Patient xml2Patient (File xml) {
	//TODO Auto-generated method sub
	 Patient  p=null;
	 manager=new JDBCManager();
	 patientmanager=new JDBCPatientManager(manager, treatmentmanager);
	 try {
		 JAXBContext jaxbContext =JAXBContext.newInstance(Patient.class);
		 Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		 
		 p=(Patient) unmarshaller.unmarshal(xml);
		 patientmanager.addPatient(p);
	 }catch (Exception e) {
		 e.printStackTrace();
	 }
	 return p;
	}
	
	

}
