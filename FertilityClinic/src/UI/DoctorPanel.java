package UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.ArrayList;
import FertilityClinicInterfaces.BoxLayout;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.JLabel;
import FertilityClinicInterfaces.JPanel;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicPOJOs.Appointment;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;

public class DoctorPanel extends JPanel {
	 
	private static final long serialVersionUID = 6394348626177860687L;

	private JPanel currentPanel;
    
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private int doctorId;
    
    //initializer
    public DoctorPanel(DoctorManager doctorManager,PatientManager patientManager, AppointmentManager appointmentManager,  int patientId) {
    	this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.doctorId = patientId;
        initializeUI();
    }
    private void initializeUI() {
        currentPanel = panelesLadoIzq(); 
        setLayout(new BorderLayout());
        add(currentPanel, BorderLayout.CENTER);
    }
    
    private void showCurrentPanel() {
        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST); 
        add(currentPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }
    
    
    //Buttons Panel
    private JPanel panelesLadoIzq() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton op1 = new JButton("View My Information");
        JButton op2 = new JButton("Update My Information");
        JButton op3 = new JButton("My appointments");
        JButton op4 = new JButton("View All my Patients");
        JButton op5 = new JButton("My ");
        

        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));
        op4.setMaximumSize(new Dimension(Integer.MAX_VALUE, op4.getMinimumSize().height));
        op5.setMaximumSize(new Dimension(Integer.MAX_VALUE, op5.getMinimumSize().height));

        op1.addActionListener(e -> viewMyinfoPanel()); //igual para doctor
        op2.addActionListener(e -> updateInfoPanel());//igual para doctor 
        op3.addActionListener(e -> appointmentsPanel()); //igual para doctor pero modificar cita patient solo delete y add
        op4.addActionListener(e -> viewAllPatientsPanel());//NUEVO
        op5.addActionListener(e -> mySpecialityPanel());

        buttonPanel.add(op1);
        buttonPanel.add(op2);
        buttonPanel.add(op3);
        buttonPanel.add(op4);
        buttonPanel.add(op5);

        return buttonPanel;
    }
    
  //OPTION 1
    private void viewMyinfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(doctorId);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        if (doctor != null) {
            infoPanel.add(new JLabel("Name: " + doctor.getName()));
            infoPanel.add(new JLabel("Email: " + doctor.getEmail()));
            infoPanel.add(new JLabel("Phone: " + doctor.getPhone()));
            infoPanel.add(new JLabel("Speciality: " + doctor.getSpeciality().getName())); 
        } else {
            infoPanel.add(new JLabel("No information available."));
        }

        currentPanel = infoPanel; 
        showCurrentPanel(); 
    }

    private void viewAllPatientsPanel() {
        List<Patient> patients = patientManager.getPatientsByDoctorId(doctorId); // Assuming such a method exists
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        for (Patient patient : patients) {
            patientPanel.add(new JLabel("Patient: " + patient.getName() + " - " + patient.getEmail()));
        }
        showPanel(patientPanel);
    }

    // View appointments for this doctor
    private void viewAppointmentsPanel() {
        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(doctorId); // Assuming method allows doctorId
        JPanel apptPanel = new JPanel();
        apptPanel.setLayout(new BoxLayout(apptPanel, BoxLayout.Y_AXIS));
        for (Appointment appt : appointments) {
            apptPanel.add(new JLabel("Appointment: " + appt.getDescription() + " on " + appt.getDate()));
        }
        showPanel(apptPanel);
    }

    // Update information panel (sample layout, real implementation needs more details)
    private void updateInfoPanel() {
        // Implementation required similar to viewMyInfoPanel but with editable fields
    }

    // View the speciality of the doctor
    private void viewMySpecialityPanel() {
        // Implementation required based on how speciality data is managed
    }

    // Utility to switch displayed panel
    private void showPanel(JPanel panel) {
        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}


