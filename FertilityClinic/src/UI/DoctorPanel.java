package UI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import FertilityClinicInterfaces.*;
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
    private SpecialityManager specialityManager;
    private int doctorId;
    
    //initializer
    public DoctorPanel(DoctorManager doctorManager,PatientManager patientManager, AppointmentManager appointmentManager,  int doctorId) {
    	this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.doctorId = doctorId;
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

        JButton op1 = new JButton("View my Information");
        JButton op2 = new JButton("Update my Information");
        JButton op3 = new JButton("My appointments");
        JButton op4 = new JButton("View all my Patients");
        JButton op5 = new JButton("Assign a patient ");
        JButton op6 = new JButton("View stock ");
        

        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));
        op4.setMaximumSize(new Dimension(Integer.MAX_VALUE, op4.getMinimumSize().height));
        op5.setMaximumSize(new Dimension(Integer.MAX_VALUE, op5.getMinimumSize().height));
        op6.setMaximumSize(new Dimension(Integer.MAX_VALUE, op5.getMinimumSize().height));

        op1.addActionListener(e -> viewMyinfoPanel()); //igual para doctor
        op2.addActionListener(e -> updateInfoPanel());//igual para doctor 
        op3.addActionListener(e -> viewMyinfoPanel()); //igual para doctor pero modificar cita patient solo delete y add
        op4.addActionListener(e -> viewMyinfoPanel());//NUEVO
        op5.addActionListener(e -> viewMyinfoPanel());
        op6.addActionListener(e -> viewMyinfoPanel());

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

            // Si licensePDF no es nulo, muestra un botón para abrir/guardar el PDF
            if (doctor.getLicensePDF() != null && doctor.getLicensePDF().length > 0) {
                JButton btnViewPDF = new JButton("View Photo");
                btnViewPDF.addActionListener(e -> {
                    // Llama a un método que maneja la visualización del PDF
                    displayPDF(doctor.getLicensePDF());
                });
                infoPanel.add(btnViewPDF);
            } else {
                infoPanel.add(new JLabel("No license file available."));
            }
        } else {
            infoPanel.add(new JLabel("No information available."));
        }

        currentPanel = infoPanel;
        showCurrentPanel();
    }

    /**
     * Método para mostrar el PDF en una ventana.
     * Aquí deberás implementar la lógica para abrir un visualizador de PDFs.
     * Este es solo un placeholder para ilustrar la funcionalidad.
     * @param pdfBytes El array de bytes del PDF a visualizar.
     */
    private void displayPDF(byte[] pdfBytes) {
        try {
            File tempFile = File.createTempFile("license", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(pdfBytes);
            }
            // Abre el archivo PDF temporal con el software predeterminado del sistema
            Desktop.getDesktop().open(tempFile);
            tempFile.deleteOnExit();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

/*
    //OPTION 3
    private void viewAppointmentsPanel() {
        ArrayList<Appointment> appointments =(ArrayList<Appointment>) appointmentManager.viewAppointment(doctorId); // Assuming method allows doctorId
        JPanel apptPanel = new JPanel();
        apptPanel.setLayout(new BoxLayout(apptPanel, BoxLayout.Y_AXIS));
        for (Appointment appt : appointments) {
            apptPanel.add(new JLabel("Appointment: " + appt.getDescription() + " on " + appt.getDate()));
        }
        currentPanel = apptPanel;
        showCurrentPanel();
    }*/

    //OPTION 4
    private void viewAllPatientsPanel() {
        List<Patient> patients = patientManager.getPatientsByDoctorId(doctorId); // Assuming such a method exists
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        for (Patient patient : patients) {
            patientPanel.add(new JLabel("Patient: " + patient.getName() + " - " + patient.getEmail()));
        }
        currentPanel = patientPanel;
        showCurrentPanel();
    }

   
    private void updateInfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(doctorId);

        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(7, 2, 10, 10)); // Adjusted rows for doctor-specific fields including PDF upload

        JTextField emailField = new JTextField(doctor != null ? doctor.getEmail() : "");
        JTextField phoneField = new JTextField(doctor != null ? String.valueOf(doctor.getPhone()) : "");
        JTextField nameField = new JTextField(doctor != null ? doctor.getName() : "");
        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(MenuUI.getSpecialities().toArray(new Speciality[0])));
        JButton selectPDFButton = new JButton("Select Photo PDF");
        JLabel pdfLabel = new JLabel("No file selected");

        // Preset the selected speciality if available
        if (doctor != null && doctor.getSpeciality() != null) {
            specialityComboBox.setSelectedItem(doctor.getSpeciality());
        }

        // Handle PDF selection
        final byte[][] pdfContainer = new byte[1][];
        selectPDFButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    pdfContainer[0] = Files.readAllBytes(selectedFile.toPath());
                    pdfLabel.setText("Selected: " + selectedFile.getName());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error reading file.");
                }
            }
        });

        // Adding labels and text fields/components
        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(nameField);
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(emailField);
        updatePanel.add(new JLabel("Phone:"));
        updatePanel.add(phoneField);
        updatePanel.add(new JLabel("Speciality:"));
        updatePanel.add(specialityComboBox);
        updatePanel.add(selectPDFButton);
        updatePanel.add(pdfLabel);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                Integer phone = Integer.parseInt(phoneField.getText());
                String name = nameField.getText();
                Speciality speciality = (Speciality) specialityComboBox.getSelectedItem();

                // Llamar al método para modificar la información del doctor con los nuevos valores
                if (speciality != null && pdfContainer[0] != null) {
                    doctorManager.modifyDoctorInfo(doctorId, email, phone, name, speciality, pdfContainer[0]);
                    JOptionPane.showMessageDialog(this, "Information updated successfully.");
                    viewMyinfoPanel(); // Actualizar y mostrar la información del doctor después de la modificación
                } else {
                    JOptionPane.showMessageDialog(this, "Please ensure all fields are correctly filled and a PDF is selected.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while updating the information.");
                ex.printStackTrace();
            }
        });

        updatePanel.add(updateBtn);

        currentPanel = updatePanel;
        showCurrentPanel();
    }



    private byte[] readFileAsBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to read the file.");
            return new byte[0];
        }
    }



	private JComboBox<Speciality> getSpecialityOptions() {
        // Assume you have a method to get all specialities
        List<Speciality> specialities = specialityManager.getAllSpecialities();
        JComboBox<Speciality> comboBox = new JComboBox<>();
        for (Speciality speciality : specialities) {
            comboBox.addItem(speciality);
        }
        return comboBox;
    }


    // View the speciality of the doctor
    private void viewMySpecialityPanel() {
        // Implementation required based on how speciality data is managed
    }


}


