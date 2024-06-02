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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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

    //OPTION 2
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

    //OPTION 3
    private void viewAppointmentsPanel() {
        JPanel appointmentsMainPanel = new JPanel(new BorderLayout());

        JPanel appointmentsOptionsPanel = new JPanel();
        appointmentsOptionsPanel.setLayout(new BoxLayout(appointmentsOptionsPanel, BoxLayout.Y_AXIS)); // Use BoxLayout along Y axis
        appointmentsOptionsPanel.setBackground(new Color(25, 25, 112)); // Dark blue background
        Font buttonFont = new Font("Calibri", Font.BOLD, 18);

        JButton op1 = new JButton("Update Appointment");
        op1.setFont(buttonFont);
        op1.setBackground(Color.WHITE);
        op1.setForeground(Color.BLACK);
        op1.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button horizontally
        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height)); // Ensure button occupies all available width

        JButton op2 = new JButton("Add Appointment");
        op2.setFont(buttonFont);
        op2.setBackground(Color.WHITE);
        op2.setForeground(Color.BLACK);
        op2.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button horizontally
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height)); // Ensure button occupies all available width

        JButton op3 = new JButton("Delete Appointment");
        op3.setFont(buttonFont);
        op3.setBackground(Color.WHITE);
        op3.setForeground(Color.BLACK);
        op3.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button horizontally
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height)); // Ensure button occupies all available width

        op1.addActionListener(e -> updateAppointmentPanel());
        op2.addActionListener(e -> addAppointmentPanel());
        op3.addActionListener(e -> deleteAppointmentPanel()); // Assuming deleteAppointmentPanel() is the method you'll use

        appointmentsOptionsPanel.add(Box.createVerticalStrut(10)); // Add vertical space between buttons
        appointmentsOptionsPanel.add(op1);
        appointmentsOptionsPanel.add(Box.createVerticalStrut(10)); // Add vertical space between buttons
        appointmentsOptionsPanel.add(op2);
        appointmentsOptionsPanel.add(Box.createVerticalStrut(10)); // Add vertical space between buttons
        appointmentsOptionsPanel.add(op3);

        appointmentsMainPanel.add(appointmentsOptionsPanel, BorderLayout.WEST);
        appointmentsMainPanel.add(new JScrollPane(currentAppointmentsPanel()), BorderLayout.CENTER);

        currentPanel = appointmentsMainPanel; // Set the current panel to the main panel
        showCurrentPanel(); // Display the current panel in the main container
    }
    
    private void deleteAppointmentPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JLabel headerLabel = new JLabel("Delete Appointments");
        headerLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel appointmentListPanel = new JPanel();
        appointmentListPanel.setLayout(new BoxLayout(appointmentListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(appointmentListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

       
        ArrayList<Appointment> appointments = appointmentManager.viewAppointmentByDoctorId(doctorId);

        if (appointments.isEmpty()) {
            appointmentListPanel.add(new JLabel("No appointments to display."));
        } else {
            for (Appointment ap : appointments) {
                JPanel singleAppointmentPanel = new JPanel();
                singleAppointmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                singleAppointmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

      
                String patientName = patientManager.searchPatientById(ap.getPatientId()).getName();
                JLabel appointmentDetails = new JLabel(
                    "Date: " + ap.getDate().toString() + 
                    ", Time: " + ap.getTime().toString() + 
                    ", Patient: " + patientName +
                    ", Description: " + ap.getDescription()
                );
                appointmentDetails.setFont(new Font("Calibri", Font.PLAIN, 18));

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    appointmentManager.deleteAppointment(ap.getId());
                    JOptionPane.showMessageDialog(null, "Appointment deleted successfully.");
                    deleteAppointmentPanel(); 
                });

                singleAppointmentPanel.add(appointmentDetails);
                singleAppointmentPanel.add(deleteButton);
                appointmentListPanel.add(singleAppointmentPanel);
            }
        }

        currentPanel = mainPanel;
        showCurrentPanel(); 
    }
    
    private JPanel currentAppointmentsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1)); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        ArrayList<Appointment> appointments = appointmentManager.viewAppointmentByDoctorId(doctorId); 

        Font labelFont = new Font("Calibri", Font.BOLD, 18); 

        if (appointments.isEmpty()) {
            JLabel noAppointmentsLabel = new JLabel("No appointments yet.");
            noAppointmentsLabel.setFont(labelFont);
            panel.add(noAppointmentsLabel);
        } else {
            for (Appointment appointment : appointments) {
                // Asumiendo que tienes un método en PatientManager para obtener el nombre del paciente por ID
                String patientName = patientManager.searchPatientById(appointment.getPatientId()).getName();

                JLabel appointmentLabel = new JLabel("<html><b>Date:</b> " + appointment.getDate() +
                    " <b>Time:</b> " + appointment.getTime() +
                    " <b>Patient's name:</b> " + patientName +
                    " <b>Doctor's name:</b> " + doctorManager.searchDoctorById(appointment.getDoctorId()).getName() +
                    " <b>Description:</b> " + appointment.getDescription() + "</html>");
                appointmentLabel.setFont(labelFont);
                panel.add(appointmentLabel);
            }
        }
        return panel;
    }

    private void addAppointmentPanel() {
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST;

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        JTextField dateField = new JTextField(15);
        dateField.setFont(new Font("Calibri", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        addPanel.add(dateField, gbc);

        JLabel timeLabel = new JLabel("Time (HH:MM:SS):");
        timeLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        JTextField timeField = new JTextField(15);
        timeField.setFont(new Font("Calibri", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        addPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        addPanel.add(timeField, gbc);

        JLabel patientLabel = new JLabel("Patient:");
        patientLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        JComboBox<Patient> patientIdComboBox = new JComboBox<>(new DefaultComboBoxModel<>(patientManager.getListOfPatients().toArray(new Patient[0])));
        patientIdComboBox.setFont(new Font("Calibri", Font.PLAIN, 18));
        patientIdComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Patient) {
                    setText(((Patient) value).getName());
                }
                return this;
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        addPanel.add(doctorLabel, gbc);
        gbc.gridx = 1;
        addPanel.add(doctorIdComboBox, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        JTextField descriptionField = new JTextField(15);
        descriptionField.setFont(new Font("Calibri", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        addPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        addPanel.add(descriptionField, gbc);

        JButton addBtn = new JButton("Add Appointment");
        addBtn.setFont(new Font("Calibri", Font.BOLD, 18));
        addBtn.setBackground(Color.WHITE);
        addBtn.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        addPanel.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(dateField.getText());
                LocalTime time = LocalTime.parse(timeField.getText());

                Doctor selectedDoctor = (Doctor) doctorIdComboBox.getSelectedItem();
                int doctorId = selectedDoctor.getId();

                String description = descriptionField.getText();
                Appointment ap = new Appointment(0, patientId, description, time, date, doctorId);
                appointmentManager.bookAppointment(ap);
                JOptionPane.showMessageDialog(this, "Appointment added successfully.");
                appointmentsPanel();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the appointment: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        currentPanel = addPanel;
        showCurrentPanel();
    }

    //OPTION 4 y 5
    
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
        List<Treatment> treatments = treatmentManager.getAllTreatments(); 
        JPanel treatmentPanel = new JPanel();
        treatmentPanel.setLayout(new BoxLayout(treatmentPanel, BoxLayout.Y_AXIS));

        JComboBox<Treatment> treatmentComboBox = new JComboBox<>();
        for (Treatment treatment : treatments) {
            treatmentComboBox.addItem(treatment);
        }
        treatmentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Treatment) {
                    Treatment treatment = (Treatment) value;
                    setText(treatment.getNameTreatment());  
                }
                return this;
            }
        });
        treatmentComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

       JLabel selectTreatmentLabel = new JLabel("Select a Treatment:");
        selectTreatmentLabel.setFont(new Font("Calibri", Font.BOLD, 30)); 
        selectTreatmentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton assignButton = new JButton("Assign");
        assignButton.setFont(new Font("Calibri", Font.PLAIN, 20)); 

        assignButton.addActionListener(e -> {
            Treatment selectedTreatment = (Treatment) treatmentComboBox.getSelectedItem();
            patientManager.assignTreatmentToPatient(patient.getId(), selectedTreatment.getTreatmentID());
            JOptionPane.showMessageDialog(null, "Treatment assigned successfully!");
            viewAllPatientsPanel(); 
        });

       treatmentPanel.add(selectTreatmentLabel);
        treatmentPanel.add(Box.createVerticalStrut(10)); 
        treatmentPanel.add(treatmentComboBox);
        treatmentPanel.add(Box.createVerticalStrut(150)); 
        treatmentPanel.add(assignButton);
        treatmentPanel.add(Box.createVerticalGlue());

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

                
                stepCheck.addItemListener(e -> {
                    boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
                    treatmentManager.updateStepCompletion(patient.getId(), step.getId(), isSelected);
                });

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


