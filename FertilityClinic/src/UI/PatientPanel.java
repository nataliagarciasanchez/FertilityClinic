package UI; 
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.*;
import UI.*;

public class PatientPanel extends JPanel {

    private static final long serialVersionUID = 7941153790910061405L;
    
    private JPanel currentPanel;
    
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private DoctorManager doctorManager;
    private int patientId;
    
    //initializer
    public PatientPanel(PatientManager patientManager, AppointmentManager appointmentManager, DoctorManager doctorManager, int patientId) {
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.doctorManager = doctorManager;
        this.patientId = patientId;
        initializeUI();
    }
    private void initializeUI() {
        currentPanel = panelesLadoIzq(); 
        setLayout(new BorderLayout());
        //add(createTopPanel(), BorderLayout.NORTH); // Adds the top panel at the top
        add(currentPanel, BorderLayout.CENTER); // Adds the side panel in the center
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
    	buttonPanel.setBackground(new Color(25, 25, 112));

    	JButton op1 = new JButton("View My Information");
    	JButton op2 = new JButton("Update My Information");
    	JButton op3 = new JButton("My appointments");
    	JButton op4 = new JButton("View All Doctors");
    	JButton op5 = new JButton("My Treatment");

    	Font buttonFont = new Font("Calibri", Font.BOLD, 18); // Font size set to 18

    	op1.setFont(buttonFont);
    	op1.setBackground(Color.WHITE);
    	op1.setForeground(Color.BLACK);
    	op1.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
    	op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));

    	op2.setFont(buttonFont);
    	op2.setBackground(Color.WHITE);
    	op2.setForeground(Color.BLACK);
    	op2.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
    	op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));

    	op3.setFont(buttonFont);
    	op3.setBackground(Color.WHITE);
    	op3.setForeground(Color.BLACK);
    	op3.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
    	op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));

    	op4.setFont(buttonFont);
    	op4.setBackground(Color.WHITE);
    	op4.setForeground(Color.BLACK);
    	op4.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
    	op4.setMaximumSize(new Dimension(Integer.MAX_VALUE, op4.getMinimumSize().height));

    	op5.setFont(buttonFont);
    	op5.setBackground(Color.WHITE);
    	op5.setForeground(Color.BLACK);
    	op5.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
    	op5.setMaximumSize(new Dimension(Integer.MAX_VALUE, op5.getMinimumSize().height));

    	op1.addActionListener(e -> viewMyinfoPanel());
    	op2.addActionListener(e -> updateInfoPanel());
    	op3.addActionListener(e -> appointmentsPanel());
    	op4.addActionListener(e -> viewAllDoctorsPanel());
    	op5.addActionListener(e -> myTreatmentPanel());

    	buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space at the top
    	buttonPanel.add(op1);
    	buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
    	buttonPanel.add(op2);
    	buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
    	buttonPanel.add(op3);
    	buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
    	buttonPanel.add(op4);
    	buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
    	buttonPanel.add(op5);

    	return buttonPanel;

    }

    //OPTION 1
    private void viewMyinfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);

        // Using a wrapper panel to provide padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // Top, left, bottom, right padding

        if (patient != null) {
            infoPanel.add(new JLabel("Name: " + patient.getName()));
            infoPanel.add(new JLabel("Date of Birth: " + patient.getDob().toString()));
            infoPanel.add(new JLabel("Gender: " + patient.getGender()));
            infoPanel.add(new JLabel("Email: " + patient.getEmail()));
            infoPanel.add(new JLabel("Phone: " + patient.getPhone()));
            infoPanel.add(new JLabel("Height: " + patient.getHeight()));
            infoPanel.add(new JLabel("Weight: " + patient.getWeight()));
            infoPanel.add(new JLabel("Blood Type: " + patient.getBloodType()));
        } else {
            infoPanel.add(new JLabel("No information available."));
        }

        Font infoFont = new Font("Calibri", Font.PLAIN, 18); // Setting the font size to 18 for better readability
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(infoFont); // Apply font to all labels
            }
        }

        wrapperPanel.add(infoPanel, BorderLayout.CENTER);
        currentPanel = wrapperPanel; // Setting the current panel to the wrapper panel
        showCurrentPanel(); // Display the current panel in the main container
    }


    //OPTION 2
    private void updateInfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);

        // Using a wrapper panel to provide padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 rows for including button row, with gaps for spacing
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // Top, left, bottom, right padding

        JTextField emailField = new JTextField(patient != null ? patient.getEmail() : "");
        JTextField phoneField = new JTextField(patient != null ? String.valueOf(patient.getPhone()) : "");
        JTextField nameField = new JTextField(patient != null ? patient.getName() : "");
        JTextField heightField = new JTextField(patient != null ? String.valueOf(patient.getHeight()) : "");
        JTextField weightField = new JTextField(patient != null ? String.valueOf(patient.getWeight()) : "");

        Font labelFont = new Font("Calibri", Font.BOLD, 18);
        Font fieldFont = new Font("Calibri", Font.PLAIN, 18);

        // Adding labels and text fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        updatePanel.add(nameLabel);
        nameField.setFont(fieldFont);
        updatePanel.add(nameField);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont);
        updatePanel.add(dobLabel);
        JLabel dobValue = new JLabel(patient != null ? patient.getDob().toString() : "");
        dobValue.setFont(fieldFont);
        updatePanel.add(dobValue);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        updatePanel.add(genderLabel);
        JLabel genderValue = new JLabel(patient != null ? patient.getGender() : "");
        genderValue.setFont(fieldFont);
        updatePanel.add(genderValue);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        updatePanel.add(emailLabel);
        emailField.setFont(fieldFont);
        updatePanel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        updatePanel.add(phoneLabel);
        phoneField.setFont(fieldFont);
        updatePanel.add(phoneField);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(labelFont);
        updatePanel.add(heightLabel);
        heightField.setFont(fieldFont);
        updatePanel.add(heightField);

        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(labelFont);
        updatePanel.add(weightLabel);
        weightField.setFont(fieldFont);
        updatePanel.add(weightField);

        JLabel bloodTypeLabel = new JLabel("Blood Type:");
        bloodTypeLabel.setFont(labelFont);
        updatePanel.add(bloodTypeLabel);
        JLabel bloodTypeValue = new JLabel(patient != null ? patient.getBloodType() : "");
        bloodTypeValue.setFont(fieldFont);
        updatePanel.add(bloodTypeValue);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Calibri", Font.BOLD, 18));
        updateBtn.setBackground(Color.WHITE);
        updateBtn.setForeground(Color.BLACK);
        updateBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                Integer phone = Integer.parseInt(phoneField.getText());
                String name = nameField.getText();
                Double height = Double.parseDouble(heightField.getText());
                Double weight = Double.parseDouble(weightField.getText());
                
                // Llamar al método para modificar la información del paciente con los nuevos valores
                patientManager.modifyPatientInfo(patientId, email, phone, name, height, weight);
                
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

        updatePanel.add(new JLabel()); // Placeholder for spacing
        updatePanel.add(updateBtn);

        wrapperPanel.add(updatePanel, BorderLayout.CENTER);

        currentPanel = wrapperPanel; // Set the current panel to the wrapper panel
        showCurrentPanel(); // Display the current panel in the main container
    }

    //OPTION 3
    private void appointmentsPanel() {
        JPanel appointmentsMainPanel = new JPanel(new BorderLayout());

        JPanel appointmentsOptionsPanel = new JPanel();
        appointmentsOptionsPanel.setLayout(new BoxLayout(appointmentsOptionsPanel, BoxLayout.Y_AXIS)); // Cambio a BoxLayout con dirección Y
        appointmentsOptionsPanel.setBackground(new Color(25, 25, 112)); // Fondo azul oscuro
        Font buttonFont = new Font("Calibri", Font.BOLD, 18);

        JButton op1 = new JButton("Update Appointment");
        op1.setFont(buttonFont);
        op1.setBackground(Color.WHITE);
        op1.setForeground(Color.BLACK);
        op1.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el botón horizontalmente
        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height)); // Asegurar que el botón ocupe todo el ancho disponible

        JButton op2 = new JButton("Add Appointment");
        op2.setFont(buttonFont);
        op2.setBackground(Color.WHITE);
        op2.setForeground(Color.BLACK);
        op2.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el botón horizontalmente
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height)); // Asegurar que el botón ocupe todo el ancho disponible

        op1.addActionListener(e -> updateAppointmentPanel());
        op2.addActionListener(e -> addAppointmentPanel());
        appointmentsOptionsPanel.add(Box.createVerticalStrut(10)); // Añadir espacio vertical entre botones
        appointmentsOptionsPanel.add(op1);
        appointmentsOptionsPanel.add(Box.createVerticalStrut(10)); // Añadir espacio vertical entre botones
        appointmentsOptionsPanel.add(op2);

        appointmentsMainPanel.add(appointmentsOptionsPanel, BorderLayout.WEST);
        appointmentsMainPanel.add(new JScrollPane(currentAppointmentsPanel()), BorderLayout.CENTER);


        appointmentsMainPanel.add(appointmentsOptionsPanel, BorderLayout.WEST);
        appointmentsMainPanel.add(new JScrollPane(currentAppointmentsPanel()), BorderLayout.CENTER);

        
        currentPanel = appointmentsMainPanel; // Establece el panel actual como el panel principal
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }
   /*
    private JPanel currentAppointmentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Cambio a GridLayout con una sola columna

        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(patientId);

        if (appointments.isEmpty()) {
            panel.add(new JLabel("No appointments yet."));
        } else {
            for (Appointment appointment : appointments) {
                JLabel appointmentLabel = new JLabel("<html><b>Date:</b> " + appointment.getDate() + " <b>Time:</b> " + appointment.getTime() + " <b>Doctor's name:</b> " + doctorManager.searchDoctorById(appointment.getDoctorId()).getName()+ " <b>Description:</b> " + appointment.getDescription() + "</html>");
                panel.add(appointmentLabel);
            }
        }

        return panel;
    }*/
    
    private JPanel currentAppointmentsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1)); // Usa GridLayout para una sola columna
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaciado de 10 píxeles alrededor
        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(patientId);

        Font labelFont = new Font("Calibri", Font.BOLD, 18); // Ajustar el tamaño de la fuente

        if (appointments.isEmpty()) {
            JLabel noAppointmentsLabel = new JLabel("No appointments yet.");
            noAppointmentsLabel.setFont(labelFont);
            panel.add(noAppointmentsLabel);
        } else {
            for (Appointment appointment : appointments) {
                JLabel appointmentLabel = new JLabel("<html><b>Date:</b> " + appointment.getDate() +
                    " <b>Time:</b> " + appointment.getTime() + 
                    " <b>Doctor's name:</b> " + doctorManager.searchDoctorById(appointment.getDoctorId()).getName() +
                    " <b>Description:</b> " + appointment.getDescription() + "</html>");
                appointmentLabel.setFont(labelFont);
                panel.add(appointmentLabel);
            }
        }
        return panel;
    }

/*
    private void addAppointmentPanel() {
        JPanel addPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JComboBox<String> doctorIdComboBox = new JComboBox<>();
        JTextField descriptionField = new JTextField();

        ArrayList<Doctor> allDoctors = (ArrayList<Doctor>) doctorManager.getListOfDoctors();
        for (Doctor doctor : allDoctors) {
        	doctorIdComboBox.addItem(String.valueOf(doctor.getName()));  
        }

        addPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        addPanel.add(dateField);
        addPanel.add(new JLabel("Time (HH:MM:SS):"));
        addPanel.add(timeField);
        addPanel.add(new JLabel("Doctor:"));
        addPanel.add(doctorIdComboBox);
        addPanel.add(new JLabel("Description:"));
        addPanel.add(descriptionField);

        JButton addBtn = new JButton("Add Appointment");
        addBtn.addActionListener(e -> {
            try {
                String dateStr = dateField.getText();
                String timeStr = timeField.getText();
                java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
                java.sql.Time sqlTime = java.sql.Time.valueOf(timeStr);

                int doctorId = Integer.parseInt((String) doctorIdComboBox.getSelectedItem());
                
                String description = descriptionField.getText();
                Appointment ap = new Appointment(0, patientId, description, sqlTime, sqlDate, doctorId);
                appointmentManager.bookAppointment(ap);
                JOptionPane.showMessageDialog(this, "Appointment added successfully.");
                appointmentsPanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the appointment: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        addPanel.add(new JLabel());
        addPanel.add(addBtn);
        currentPanel = addPanel;
        showCurrentPanel();
    }*/ 
    
    private void addAppointmentPanel() {
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); // Separación de 20 a la derecha y 10 arriba

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
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

        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        JComboBox<Doctor> doctorIdComboBox = new JComboBox<>(new DefaultComboBoxModel<>(doctorManager.getListOfDoctors().toArray(new Doctor[0])));
        doctorIdComboBox.setFont(new Font("Calibri", Font.PLAIN, 18));
        doctorIdComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor) {
                    setText(((Doctor) value).getName());
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
                String dateStr = dateField.getText();
                String timeStr = timeField.getText();
                java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
                java.sql.Time sqlTime = java.sql.Time.valueOf(timeStr);

                Doctor selectedDoctor = (Doctor) doctorIdComboBox.getSelectedItem();
                int doctorId = selectedDoctor.getId();

                String description = descriptionField.getText();
                Appointment ap = new Appointment(0, patientId, description, sqlTime, sqlDate, doctorId);
                appointmentManager.bookAppointment(ap);
                JOptionPane.showMessageDialog(this, "Appointment added successfully.");
                appointmentsPanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the appointment: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        currentPanel = addPanel;
        showCurrentPanel();
    }



    private void updateAppointmentPanel() {
        // Usando un panel de contenedor para proporcionar padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout con huecos para el espaciado
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // Top, left, bottom, right padding

        ArrayList<Appointment> appointments = appointmentManager.getCurrentAppointments(patientId);

        Font labelFont = new Font("Calibri", Font.BOLD, 18);
        Font fieldFont = new Font("Calibri", Font.PLAIN, 18);

        if (appointments != null && !appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                JPanel singleAppointmentPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Panel para cada cita
                singleAppointmentPanel.setBackground(new Color(240, 240, 240)); // Fondo blanco para cada cita

                JLabel doctorLabel = new JLabel("Dr. " + doctorManager.searchDoctorById(appointment.getDoctorId()).getName());
                doctorLabel.setFont(fieldFont);
                JTextField dateField = new JTextField(appointment.getDate().toString());
                dateField.setFont(fieldFont);
                JTextField timeField = new JTextField(appointment.getTime().toString());
                timeField.setFont(fieldFont);
                JTextField descriptionField = new JTextField(appointment.getDescription());
                descriptionField.setFont(fieldFont);

                JLabel doctorTextLabel = new JLabel("Doctor:");
                doctorTextLabel.setFont(labelFont);
                singleAppointmentPanel.add(doctorTextLabel);
                singleAppointmentPanel.add(doctorLabel);

                JLabel dateTextLabel = new JLabel("Date:");
                dateTextLabel.setFont(labelFont);
                singleAppointmentPanel.add(dateTextLabel);
                singleAppointmentPanel.add(dateField);

                JLabel timeTextLabel = new JLabel("Time:");
                timeTextLabel.setFont(labelFont);
                singleAppointmentPanel.add(timeTextLabel);
                singleAppointmentPanel.add(timeField);

                JLabel descriptionTextLabel = new JLabel("Description:");
                descriptionTextLabel.setFont(labelFont);
                singleAppointmentPanel.add(descriptionTextLabel);
                singleAppointmentPanel.add(descriptionField);

                JButton updateBtn = new JButton("Update");
                updateBtn.setFont(new Font("Calibri", Font.BOLD, 18));
                updateBtn.setBackground(Color.WHITE);
                updateBtn.setForeground(Color.BLACK);
                updateBtn.addActionListener(e -> {
                    try {
                        String newDate = dateField.getText();
                        String newTime = timeField.getText();
                        String newDescription = descriptionField.getText();

                        java.sql.Date sqlDate = java.sql.Date.valueOf(newDate);
                        java.sql.Time sqlTime = java.sql.Time.valueOf(newTime);

                        Appointment updatedAppointment = new Appointment(appointment.getId(), patientId, newDescription, sqlTime, sqlDate, appointment.getDoctorId());
                        appointmentManager.updateAppointment(updatedAppointment); // Llamar al método de actualización con el nuevo appointment
                        JOptionPane.showMessageDialog(this, "Appointment updated successfully.");
                        updateAppointmentPanel(); // Refrescar el panel de appointments después de la actualización
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "An error occurred while updating the appointment.");
                        ex.printStackTrace();
                    }
                });

                singleAppointmentPanel.add(new JLabel()); // Placeholder for spacing
                singleAppointmentPanel.add(updateBtn);

                updatePanel.add(singleAppointmentPanel);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No appointments found for this patient.");
        }

        wrapperPanel.add(new JScrollPane(updatePanel), BorderLayout.CENTER);

        currentPanel = wrapperPanel; // Set the current panel to the wrapper panel
        showCurrentPanel(); // Display the current panel in the main container
    }


    
    //OPTION 4
    private void viewAllDoctorsPanel() {
        JPanel doctorPanel = new JPanel();
        doctorPanel.setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Search");

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(new JLabel("Search Doctor by Name: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        // Load all doctors by default
        ArrayList<Doctor> allDoctors = (ArrayList<Doctor>) doctorManager.getListOfDoctors(); // Assuming you have this method
        displayDoctors(resultPanel, allDoctors);

        searchBtn.addActionListener(e -> {
            String searchQuery = searchField.getText();
            ArrayList<Doctor> doctors = (ArrayList<Doctor>) doctorManager.searchDoctorByName(searchQuery);
            resultPanel.removeAll();
            displayDoctors(resultPanel, doctors);
            validate();
            repaint();
        });

        doctorPanel.add(searchPanel, BorderLayout.NORTH);
        doctorPanel.add(new JScrollPane(resultPanel), BorderLayout.CENTER);
        
        currentPanel = doctorPanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
   
    }

    private void displayDoctors(JPanel resultPanel, ArrayList<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            JPanel doctorInfoPanel = new JPanel(new GridLayout(1, 2));
            JLabel nameLabel = new JLabel("Name: " + doctor.getName());
            doctorInfoPanel.add(nameLabel);
            Speciality speciality = doctor.getSpeciality();
            JLabel specialityLabel = new JLabel("Speciality: " + (speciality != null ? speciality.getName() : "Unknown"));
            doctorInfoPanel.add(specialityLabel);
            JLabel emailLabel = new JLabel("Email: " + doctor.getEmail());
            doctorInfoPanel.add(emailLabel);
            resultPanel.add(doctorInfoPanel);
        }
    }

/*
    //OPTION 5
    private void myTreatmentPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);
        Treatments treatment = patient.getTreatmet();
        
        JPanel treatmentPanel = new JPanel();
        treatmentPanel.setLayout(new BoxLayout(treatmentPanel, BoxLayout.Y_AXIS));
        
        if (treatment != null) {
            treatmentPanel.add(new JLabel("Name of Treatment: " + treatment.getName()));
            treatmentPanel.add(new JLabel("Description: " + treatment.getDescription()));
            treatmentPanel.add(new JLabel("Duration: " + treatment.getDurationInDays() + " days"));
        } else {
            treatmentPanel.add(new JLabel("No treatment information available."));
        }

        currentPanel = treatmentPanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
   
       
    }*/
    
    private void myTreatmentPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);
        Treatments treatment = patient.getTreatmet();

        // Using a wrapper panel to provide padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel treatmentPanel = new JPanel();
        treatmentPanel.setLayout(new BoxLayout(treatmentPanel, BoxLayout.Y_AXIS));
        treatmentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // Padding: top, left, bottom, right

        Font infoFont = new Font("Calibri", Font.PLAIN, 24); // Larger font size for better readability
        Font labelFont = new Font("Calibri", Font.BOLD, 24);

        if (treatment != null) {
            JLabel nameLabel = new JLabel("Name of Treatment: " + treatment.getName());
            nameLabel.setFont(labelFont);
            treatmentPanel.add(nameLabel);

            String descriptionText = "Description: " + treatment.getDescription().replace("  ", "<br>"); // Replace double spaces with HTML line break
            JLabel descriptionLabel = new JLabel("<html>" + descriptionText + "</html>"); // Wrap text in HTML to handle line breaks
            descriptionLabel.setFont(infoFont);
            treatmentPanel.add(descriptionLabel);

            JLabel durationLabel = new JLabel("Duration: " + treatment.getDurationInDays() + " days");
            durationLabel.setFont(labelFont);
            treatmentPanel.add(durationLabel);
        } else {
            JLabel noInfoLabel = new JLabel("No treatment information available.");
            noInfoLabel.setFont(labelFont);
            treatmentPanel.add(noInfoLabel);
        }

        wrapperPanel.add(treatmentPanel, BorderLayout.CENTER);
        currentPanel = wrapperPanel; // Set the current panel to the wrapper panel
        showCurrentPanel(); // Display the current panel in the main container
    }



}
