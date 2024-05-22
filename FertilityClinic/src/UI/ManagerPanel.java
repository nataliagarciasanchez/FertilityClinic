package UI;

import javax.swing.*;
import java.awt.*;
import FertilityClinicInterfaces.ManagerManager;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Manager;

public class ManagerPanel extends JPanel {
	
	
	private static final long serialVersionUID = 7116827211267658216L;

	private JPanel currentPanel;
	
    private ManagerManager managerManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private SpecialityManager specialityManager;
    private int managerId;

    // Constructor que acepta interfaces para la gestión de doctores y pacientes
    public ManagerPanel(ManagerManager managerManager,DoctorManager doctorManager,PatientManager patientManager, AppointmentManager appointmentManager,  int managerId) {
        this.managerManager = managerManager;
        this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.managerId = managerId;
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
        JButton op3 = new JButton("View Stock");
       
        

        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));
        
        op1.addActionListener(e -> viewMyinfoPanel()); //igual para doctor
        op2.addActionListener(e -> updateInfoPanel());//igual para doctor 
        op3.addActionListener(e -> viewStockPanel()); //igual para doctor pero modificar cita patient solo delete y add
        

        buttonPanel.add(op1);
        buttonPanel.add(op2);
        buttonPanel.add(op3);
       

        return buttonPanel;
    }
  //OPTION 1
    private void viewMyinfoPanel() {
        Manager manager = managerManager.viewMyInfo(managerId);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        if (manager != null) {
            infoPanel.add(new JLabel("Name: " + manager.getName()));
            infoPanel.add(new JLabel("Email: " + manager.getEmail()));
            infoPanel.add(new JLabel("Phone: " + manager.getPhone()));
            
        }

        currentPanel = infoPanel;
        showCurrentPanel();
    }
}