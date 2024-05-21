package UI;

import javax.swing.*;
import java.awt.*;
import FertilityClinicInterfaces.ManagerManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;

public class ManagerPanel extends JPanel {
    private ManagerManager managerManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;

    // Constructor que acepta interfaces para la gestión de doctores y pacientes
    public ManagerPanel(ManagerManager managerManager) {
        this.managerManager = managerManager;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Manager Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        // Agregar componentes específicos para la gestión
        createManagementComponents();
    }

    // Método para añadir componentes de gestión
    private void createManagementComponents() {
        JButton manageDoctorsButton = new JButton("Manage Doctors");
        manageDoctorsButton.addActionListener(e -> manageDoctors());

        JButton managePatientsButton = new JButton("Manage Patients");
        managePatientsButton.addActionListener(e -> managePatients());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(manageDoctorsButton);
        buttonPanel.add(managePatientsButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos para las acciones de los botones
    private void manageDoctors() {
        // Implementación de la gestión de doctores
        System.out.println("Managing doctors...");
    }

    private void managePatients() {
        // Implementación de la gestión de pacientes
        System.out.println("Managing patients...");
    }
}

