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
import FertilityClinicPOJOs.Patient;

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
        op3.addActionListener(e -> viewMyinfoPanel()); //igual para doctor pero modificar cita patient solo delete y add
        

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
    //OPCION 2
    private void updateInfoPanel() {
        Manager manager = managerManager.viewMyInfo(managerId);

        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 rows for including button row, with gaps for spacing

        JTextField emailField = new JTextField(manager != null ? manager.getEmail() : "");
        JTextField phoneField = new JTextField(manager != null ? String.valueOf(manager.getPhone()) : "");
        JTextField nameField = new JTextField(manager != null ? manager.getName() : "");
        
        // Adding labels and text fields
        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(nameField);
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(emailField);
        updatePanel.add(new JLabel("Phone:"));
        updatePanel.add(phoneField);
        

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                Integer phone = Integer.parseInt(phoneField.getText());
                String name = nameField.getText();
                
                
                // Llamar al método para modificar la información del paciente con los nuevos valores
                managerManager.modifyManagerInfo(managerId, email, phone, name);
                
                JOptionPane.showMessageDialog(this, "Information updated successfully.");
                
                // Después de actualizar, volver a mostrar la información actualizada
                viewMyinfoPanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid values.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while updating the information.");
                ex.printStackTrace();
            }
        });

        updatePanel.add(new JLabel());
        updatePanel.add(updateBtn);

        // Adding padding around the panel
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        paddedPanel.add(updatePanel, BorderLayout.CENTER);

        // Set a preferred size to ensure the panel is somewhat square
        paddedPanel.setPreferredSize(new Dimension(400, 400));

        
        currentPanel = updatePanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }
    //OPCION 3
    
    
}