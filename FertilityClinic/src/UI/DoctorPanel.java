package UI;

import javax.swing.*;
import java.awt.*;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.Doctor;

public class DoctorPanel extends JPanel {
    private DoctorManager doctorManager;
    private int doctorId; // ID del doctor, puedes cambiarlo por User si es más apropiado

    // Constructor que acepta DoctorManager y el ID del doctor (o del usuario si es aplicable)
    public DoctorPanel(DoctorManager doctorManager, int doctorId) {
        this.doctorManager = doctorManager;
        this.doctorId = doctorId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Doctor Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        // Aquí podrías añadir más componentes específicos de la funcionalidad del doctor
        createDoctorSpecificComponents();
    }

    // Método para añadir componentes específicos del doctor
    private void createDoctorSpecificComponents() {
        // Supongamos que queremos añadir una lista de pacientes
        JButton viewPatientsButton = new JButton("View My Patients");
        viewPatientsButton.addActionListener(e -> viewPatients());
        add(viewPatientsButton, BorderLayout.SOUTH);
    }

    // Ejemplo de un método para visualizar pacientes
    private void viewPatients() {
        // Puedes implementar la lógica para mostrar pacientes del doctor aquí
        // Utilizando doctorManager y doctorId para recuperar los pacientes específicos del doctor
        System.out.println("Displaying patients for Doctor ID: " + doctorId);
    }
}


