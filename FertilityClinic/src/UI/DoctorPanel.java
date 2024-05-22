package UI;

import javax.swing.*;
import java.awt.*;

import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;

public class DoctorPanel extends JPanel {
	 
    private JPanel currentPanel;
    
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private DoctorManager doctorManager;
    private int patientId;
    
    //initializer
    public DoctorPanel(DoctorManager doctorManager,PatientManager patientManager, AppointmentManager appointmentManager,  int patientId) {
    	this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.patientId = patientId;
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
        op5.addActionListener(e -> myTreatmentPanel());

        buttonPanel.add(op1);
        buttonPanel.add(op2);
        buttonPanel.add(op3);
        buttonPanel.add(op4);
        buttonPanel.add(op5);

        return buttonPanel;
    }
    
  //OPTION 1
    private void viewMyinfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(patientId);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        if (doctor != null) {
            infoPanel.add(new JLabel("Name: " + doctor.getName()));
            infoPanel.add(new JLabel("Date of Birth: " + doctor.getDob().toString()));
            infoPanel.add(new JLabel("Gender: " + doctor.getGender()));
            infoPanel.add(new JLabel("Email: " + doctor.getEmail()));
            infoPanel.add(new JLabel("Phone: " + doctor.getPhone()));
            infoPanel.add(new JLabel("Height: " + doctor.getHeight()));
            infoPanel.add(new JLabel("Weight: " + doctor.getWeight()));
            infoPanel.add(new JLabel("Blood Type: " + doctor.getBloodType()));
        } else {
            infoPanel.add(new JLabel("No information available."));
        }

        currentPanel = infoPanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }


}


