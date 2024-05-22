package UI;

import javax.swing.*;
import java.awt.*;

import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicPOJOs.Doctor;

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
        removeAll(); // Elimina todo el contenido del contenedor principal
        add(panelesLadoIzq(), BorderLayout.WEST); // Vuelve a agregar el panel de opciones del paciente
        add(currentPanel, BorderLayout.CENTER); // Agrega el panel actual al centro
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
        JButton op4 = new JButton("View All Doctors");
        JButton op5 = new JButton("My Treatment");
        

        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));
        op4.setMaximumSize(new Dimension(Integer.MAX_VALUE, op4.getMinimumSize().height));
        op5.setMaximumSize(new Dimension(Integer.MAX_VALUE, op5.getMinimumSize().height));

        op1.addActionListener(e -> viewMyinfoPanel());
        op2.addActionListener(e -> updateInfoPanel());
        op3.addActionListener(e -> appointmentsPanel());
        op4.addActionListener(e -> viewAllDoctorsPanel());
        op5.addActionListener(e -> myTreatmentPanel());

        buttonPanel.add(op1);
        buttonPanel.add(op2);
        buttonPanel.add(op3);
        buttonPanel.add(op4);
        buttonPanel.add(op5);

        return buttonPanel;
    }

}


