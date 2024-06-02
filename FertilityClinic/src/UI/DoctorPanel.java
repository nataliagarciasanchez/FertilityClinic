package UI;

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;
//import org.apache.pdfbox.rendering.ImageType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FertilityClinicInterfaces.*;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicPOJOs.Appointment;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Speciality;
import FertilityClinicPOJOs.Treatment;
import FertilityClinicPOJOs.TreatmentStep;

public class DoctorPanel extends JPanel {
	 
	private static final long serialVersionUID = 6394348626177860687L;

	private JPanel currentPanel;
    
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private SpecialityManager specialityManager;
    private TreatmentManager treatmentManager;
    
    private int doctorId;
    
    //initializer
    public DoctorPanel(DoctorManager doctorManager,PatientManager patientManager, AppointmentManager appointmentManager, TreatmentManager treatmentManager, int doctorId) {
    	this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.treatmentManager = treatmentManager;
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
        buttonPanel.setBackground(new Color(25, 25, 112)); // Fondo azul

        JButton op1 = new JButton("View my Information");
        JButton op2 = new JButton("Update my Information");
        JButton op3 = new JButton("My appointments");
        JButton op4 = new JButton("View all my Patients");
        JButton op5 = new JButton("Assign a patient ");
        JButton op6 = new JButton("View stock ");

        // Configurar botones
        Font buttonFont = new Font("Calibri", Font.BOLD, 18);
        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, 40);

        configureButton(op1, buttonFont, buttonSize);
        configureButton(op2, buttonFont, buttonSize);
        configureButton(op3, buttonFont, buttonSize);
        configureButton(op4, buttonFont, buttonSize);
        configureButton(op5, buttonFont, buttonSize);
        configureButton(op6, buttonFont, buttonSize);

        op1.addActionListener(e -> viewMyinfoPanel()); 
        op2.addActionListener(e -> updateInfoPanel());
        op3.addActionListener(e -> viewAppointmentsPanel()); 
        op4.addActionListener(e -> viewAllPatientsPanel());
        op5.addActionListener(e -> assignPatientPanel());
        op6.addActionListener(e -> viewMyinfoPanel());

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        buttonPanel.add(op1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        buttonPanel.add(op2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(op3);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(op4);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(op5);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(op6);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        return buttonPanel;
    }

    private void configureButton(JButton button, Font font, Dimension size) {
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    //OPTION 1
    private void viewMyinfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(doctorId);

        JPanel wrapperPanel = new JPanel(new BorderLayout(10, 0)); 
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (doctor != null) {
            JLabel nameLabel = new JLabel("Name: " + doctor.getName());
            nameLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            infoPanel.add(nameLabel);

            JLabel emailLabel = new JLabel("Email: " + doctor.getEmail());
            emailLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            infoPanel.add(emailLabel);

            JLabel phoneLabel = new JLabel("Phone: " + doctor.getPhone());
            phoneLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            infoPanel.add(phoneLabel);

            JLabel specialityLabel = new JLabel("Speciality: " + doctor.getSpeciality().getName());
            specialityLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            infoPanel.add(specialityLabel);
        } else {
            JLabel noInfoLabel = new JLabel("No information available.");
            noInfoLabel.setFont(new Font("Calibri", Font.BOLD, 18));
            infoPanel.add(noInfoLabel);
        }

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (doctor != null && doctor.getLicensePDF() != null) {
            ImageIcon icon = new ImageIcon(doctor.getLicensePDF());
            Image img = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH); // Escala la imagen
            icon = new ImageIcon(img);

            JLabel photoLabel = new JLabel(icon);
            photoLabel.setHorizontalAlignment(JLabel.CENTER);
            leftPanel.add(photoLabel, BorderLayout.NORTH);
        } else {
            JLabel noPhotoLabel = new JLabel("No photo yet.");
            noPhotoLabel.setFont(new Font("Calibri", Font.BOLD, 18));
            leftPanel.add(noPhotoLabel, BorderLayout.NORTH);
        }

        wrapperPanel.add(leftPanel, BorderLayout.WEST);
        wrapperPanel.add(infoPanel, BorderLayout.CENTER);

        currentPanel = wrapperPanel; // Set the current panel to the wrapper panel
        showCurrentPanel(); // Display the current panel in the main container
    }


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
    }

    //OPTION 4
    
    private void viewAllPatientsPanel() {
        List<Patient> patients = patientManager.getPatientsByDoctorId(doctorId);
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));

        for (Patient patient : patients) {
            JPanel singlePatientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel patientLabel = new JLabel("Patient: " + patient.getName() + " - " + patient.getEmail());
            patientLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            JButton detailsButton = new JButton("View Details");
            detailsButton.addActionListener(e -> showPatientDetails(patient));

            JButton assignTreatmentButton = new JButton("Assign Treatment");
            assignTreatmentButton.addActionListener(e -> assignTreatmentPanel(patient));

            singlePatientPanel.add(patientLabel);
            singlePatientPanel.add(detailsButton);
            singlePatientPanel.add(assignTreatmentButton);
            patientPanel.add(singlePatientPanel);
        }

        currentPanel = patientPanel;
        showCurrentPanel();
    }
    
    private void assignTreatmentPanel(Patient patient) {
        List<Treatment> treatments = treatmentManager.getAllTreatments(); // Asume que tienes un método para obtener todos los tratamientos
        JPanel treatmentPanel = new JPanel();
        treatmentPanel.setLayout(new BoxLayout(treatmentPanel, BoxLayout.Y_AXIS));

        JComboBox<Treatment> treatmentComboBox = new JComboBox<>();
        for (Treatment treatment : treatments) {
            treatmentComboBox.addItem(treatment);
        }

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e -> {
            Treatment selectedTreatment = (Treatment) treatmentComboBox.getSelectedItem();
            patientManager.assignTreatmentToPatient(patient.getId(), selectedTreatment.getTreatmentID());
            JOptionPane.showMessageDialog(null, "Treatment assigned successfully!");
        });

        treatmentPanel.add(new JLabel("Select a Treatment:"));
        treatmentPanel.add(treatmentComboBox);
        treatmentPanel.add(assignButton);

        currentPanel = treatmentPanel;
        showCurrentPanel();
    }




    private void showPatientDetails(Patient patient) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        backButton.addActionListener(e -> viewAllPatientsPanel());
        detailsPanel.add(backButton);

        JLabel nameLabel = new JLabel("Name: " + patient.getName());
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        detailsPanel.add(nameLabel);

        JLabel emailLabel = new JLabel("Email: " + patient.getEmail());
        emailLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        detailsPanel.add(emailLabel);

        JLabel phoneLabel = new JLabel("Phone: " + patient.getPhone());
        phoneLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        detailsPanel.add(phoneLabel);

        JLabel dobLabel = new JLabel("Date of Birth: " + patient.getDob().toString());
        dobLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        detailsPanel.add(dobLabel);

        Treatment treatment = patient.getTreatment();
        if (treatment != null) {
            JLabel treatmentLabel = new JLabel("Treatment: " );
            treatmentLabel.setFont(new Font("Calibri", Font.BOLD, 16));
            detailsPanel.add(treatmentLabel);

            JLabel descLabel = new JLabel("<html><p style='width:600px'>" + treatment.getDescription() + "</p></html>");
            descLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
            detailsPanel.add(descLabel);
            
            detailsPanel.add(Box.createVerticalStrut(20));
            
            ArrayList<TreatmentStep> steps = (ArrayList<TreatmentStep>) treatmentManager.getTreatmentSteps(treatment.getTreatmentID());
            Map<Integer, Boolean> completionStatus = treatmentManager.getStepCompletion(patient.getId(), treatment.getTreatmentID());
            HashMap<Integer, JCheckBox> checkBoxMap = new HashMap<>();

            for (TreatmentStep step : steps) {
                JPanel stepPanel = new JPanel();
                stepPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JCheckBox stepCheck = new JCheckBox((step.getStepOrder() + ". " + step.getStepDescription()));
                stepCheck.setSelected(completionStatus.getOrDefault(step.getId(), false));
                stepCheck.setFont(new Font("Calibri", Font.PLAIN, 16));

                
               /* stepCheck.addItemListener(e -> {
                    boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
                    treatmentManager.updateStepCompletion(patient.getId(), step.getId(), isSelected);
                });*/

                stepPanel.add(stepCheck);
                stepPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
                detailsPanel.add(stepPanel);
                checkBoxMap.put(step.getId(), stepCheck); 
            }
            detailsPanel.add(Box.createVerticalStrut(20));

            JLabel durationLabel = new JLabel("Duration: " + treatment.getDurationInDays() + " days");
            durationLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
            detailsPanel.add(durationLabel);

            JButton updateButton = new JButton("Update Steps");
            updateButton.setFont(new Font("Calibri", Font.PLAIN, 16));
            updateButton.addActionListener(e -> {
                boolean updateSuccessful = true;
                for (TreatmentStep step : steps) {  
                	 JCheckBox checkBox = checkBoxMap.get(step.getId());  
                    boolean isSelected =checkBox.isSelected();  // Estado del checkbox correspondiente a este paso
                    try {
                        treatmentManager.updateStepCompletion(patient.getId(), step.getId(), isSelected);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Failed to update step completion: " + ex.getMessage());
                        updateSuccessful = false;
                        break;
                    }
                }
                if (updateSuccessful) {
                    JOptionPane.showMessageDialog(null, "Treatment steps updated successfully.");
                }
            });
            detailsPanel.add(updateButton);
        } else {
            JLabel noTreatmentLabel = new JLabel("Treatment: None assigned");
            noTreatmentLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
            detailsPanel.add(noTreatmentLabel);
        }

        currentPanel = detailsPanel;
        showCurrentPanel();
    }





    private void updateInfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(doctorId);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel updatePanel = new JPanel(new GridLayout(7, 2, 10, 10)); 
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); 

        JTextField emailField = new JTextField(doctor != null ? doctor.getEmail() : "");
        JTextField phoneField = new JTextField(doctor != null ? String.valueOf(doctor.getPhone()) : "");
        JTextField nameField = new JTextField(doctor != null ? doctor.getName() : "");
        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(MenuUI.getSpecialities().toArray(new Speciality[0])));
        JButton selectImageButton = new JButton("Select Image");
        JLabel imageLabel = new JLabel("No image selected");

        selectImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    byte[] imageData = Files.readAllBytes(selectedFile.toPath());
                    doctor.setLicensePDF(imageData);
                    imageLabel.setText("Selected: " + selectedFile.getName());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error reading file.");
                }
            }
        });

        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(nameField);
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(emailField);
        updatePanel.add(new JLabel("Phone:"));
        updatePanel.add(phoneField);
        updatePanel.add(new JLabel("Speciality:"));
        updatePanel.add(specialityComboBox);
        updatePanel.add(selectImageButton);
        updatePanel.add(imageLabel);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                Integer phone = Integer.parseInt(phoneField.getText());
                String name = nameField.getText();
                Speciality speciality = (Speciality) specialityComboBox.getSelectedItem();
                doctorManager.modifyDoctorInfo(doctorId, email, phone, name, speciality, doctor.getLicensePDF());
                JOptionPane.showMessageDialog(null, "Information updated successfully.");
                viewMyinfoPanel(); // Refresh display
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while updating the information.");
            }
        });

        updatePanel.add(new JLabel()); // Placeholder for layout
        updatePanel.add(updateBtn);

        wrapperPanel.add(updatePanel, BorderLayout.CENTER);
        currentPanel = wrapperPanel;
        showCurrentPanel();
    }


    private void assignPatientPanel() {
        List<Patient> patients = patientManager.getAvailablePatientsForDoctor(doctorId); 
        JPanel wrapperPanel = new JPanel(new BorderLayout(10, 0));
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        patientPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Assign a Patient");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        patientPanel.add(titleLabel);

        JComboBox<Patient> patientComboBox = new JComboBox<>();
        for (Patient patient : patients) {
            patientComboBox.addItem(patient);
        }
        
        patientComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Patient) {
                    setText(((Patient) value).getName());
                }
                return this;
            }
        });
        patientPanel.add(patientComboBox);

        JButton assignButton = new JButton("Assign Patient");
        assignButton.setFont(new Font("Calibri", Font.PLAIN, 18));
        assignButton.addActionListener(e -> {
            Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
            if (selectedPatient != null) {
                doctorManager.assignPatientToDoctor(selectedPatient.getId(), doctorId);  // Utiliza el ID del doctor desde el contexto de la sesión
                JOptionPane.showMessageDialog(null, "Patient assigned successfully.");
                viewAllPatientsPanel(); // Actualizar la lista de pacientes asignados (si es necesario)
            }
        });
        patientPanel.add(assignButton);

        wrapperPanel.add(patientPanel, BorderLayout.CENTER);

        currentPanel = wrapperPanel; // Establece el panel actual al panel de envoltura
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
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


