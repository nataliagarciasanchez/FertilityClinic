package UI; 
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicPOJOs.*;

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

    //OPTION 1
    private void viewMyinfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

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

        currentPanel = infoPanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }

    
    
    //OPTION 2
    private void updateInfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);

        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 rows for including button row, with gaps for spacing

        JTextField emailField = new JTextField(patient != null ? patient.getEmail() : "");
        JTextField phoneField = new JTextField(patient != null ? String.valueOf(patient.getPhone()) : "");
        JTextField nameField = new JTextField(patient != null ? patient.getName() : "");
        JTextField heightField = new JTextField(patient != null ? String.valueOf(patient.getHeight()) : "");
        JTextField weightField = new JTextField(patient != null ? String.valueOf(patient.getWeight()) : "");

        // Adding labels and text fields
        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(nameField);
        updatePanel.add(new JLabel("Date of Birth:"));
        updatePanel.add(new JLabel(patient != null ? patient.getDob().toString() : ""));
        updatePanel.add(new JLabel("Gender:"));
        updatePanel.add(new JLabel(patient != null ? patient.getGender() : ""));
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(emailField);
        updatePanel.add(new JLabel("Phone:"));
        updatePanel.add(phoneField);
        updatePanel.add(new JLabel("Height:"));
        updatePanel.add(heightField);
        updatePanel.add(new JLabel("Weight:"));
        updatePanel.add(weightField);
        updatePanel.add(new JLabel("Blood Type:"));
        updatePanel.add(new JLabel(patient != null ? patient.getBloodType() : ""));

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            String email = emailField.getText();
            Integer phone = Integer.parseInt(phoneField.getText());
            String name = nameField.getText();
            Double height = Double.parseDouble(heightField.getText());
            Double weight = Double.parseDouble(weightField.getText());
            patientManager.modifyPatientInfo(patientId, email, phone, name);
            JOptionPane.showMessageDialog(this, "Information updated successfully.");
            //viewMyinfoPanel(); // Refresh view after update
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

    //OPTION 3
    private void appointmentsPanel() {
        JPanel appointmentsMainPanel = new JPanel(new BorderLayout());

        JPanel appointmentsOptionsPanel = new JPanel();
        appointmentsOptionsPanel.setLayout(new BoxLayout(appointmentsOptionsPanel, BoxLayout.Y_AXIS));

        JButton updateAppBtn = new JButton("Update Appointment");
        JButton addAppBtn = new JButton("Add Appointment");

        updateAppBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, updateAppBtn.getMinimumSize().height));
        addAppBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, addAppBtn.getMinimumSize().height));

        updateAppBtn.addActionListener(e -> updateAppointmentPanel());
        addAppBtn.addActionListener(e -> addAppointmentPanel());

        appointmentsOptionsPanel.add(updateAppBtn);
        appointmentsOptionsPanel.add(addAppBtn);

        appointmentsMainPanel.add(appointmentsOptionsPanel, BorderLayout.WEST);
        appointmentsMainPanel.add(currentAppointmentsPanel(), BorderLayout.CENTER);

        currentPanel = appointmentsOptionsPanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
   
    }

    private JPanel currentAppointmentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(patientId);

        for (Appointment appointment : appointments) {
            panel.add(new JLabel("Appointment with Dr. " + doctorManager.searchDoctorById(appointment.getDoctorId()) + " on " + appointment.getDate() + " at " + appointment.getTime()));
        }

        return panel;
    }

    private void addAppointmentPanel() {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(4, 2)); 

        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField descriptionField = new JTextField();

        addPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        addPanel.add(dateField);
        addPanel.add(new JLabel("Time (HH:MM:SS):")); 
        addPanel.add(timeField);
        addPanel.add(new JLabel("Doctor ID:"));
        addPanel.add(doctorIdField);
        addPanel.add(new JLabel("Description:"));
        addPanel.add(descriptionField);

        JButton addBtn = new JButton("Add Appointment");
        addBtn.addActionListener(e -> {
            try {
                String dateStr = dateField.getText();
                String timeStr = timeField.getText();
                int doctorId = Integer.parseInt(doctorIdField.getText());
                String description = descriptionField.getText();

                java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
                java.sql.Time sqlTime = java.sql.Time.valueOf(timeStr);

                Appointment ap = new Appointment(0, patientId, description, sqlTime, sqlDate, doctorId);
                appointmentManager.bookAppointment(ap);
                JOptionPane.showMessageDialog(this, "Appointment added successfully.");
                appointmentsPanel(); // Refresh appointments panel after adding
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the appointment.");
                ex.printStackTrace();
            }
        });

        addPanel.add(new JLabel());
        addPanel.add(addBtn);

        
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(addPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private void updateAppointmentPanel() {
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));

        ArrayList<Appointment> appointments = appointmentManager.getCurrentAppointments(patientId);

        for (Appointment appointment : appointments) {
            JPanel singleAppointmentPanel = new JPanel(new GridLayout(1, 5)); // Modificamos GridLayout para acomodar la descripción
            singleAppointmentPanel.add(new JLabel("Dr. " + doctorManager.searchDoctorById(appointment.getDoctorId())));
            JTextField dateField = new JTextField(appointment.getDate().toString());
            JTextField timeField = new JTextField(appointment.getTime().toString());
            JTextField descriptionField = new JTextField(appointment.getDescription()); // Nuevo campo para la descripción
            singleAppointmentPanel.add(dateField);
            singleAppointmentPanel.add(timeField);
            singleAppointmentPanel.add(descriptionField);

            JButton updateBtn = new JButton("Update");
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
                    appointmentsPanel(); // Refrescar el panel de appointments después de la actualización
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid date and time formats.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while updating the appointment.");
                    ex.printStackTrace();
                }
            });

            singleAppointmentPanel.add(updateBtn);
            updatePanel.add(singleAppointmentPanel);
        }

        // Limpiar los componentes existentes y agregar nuevos componentes
        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(updatePanel, BorderLayout.CENTER);
        validate();
        repaint();
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
            // Crear un panel para cada doctor
            JPanel doctorInfoPanel = new JPanel(new GridLayout(1, 2)); // 2 columnas para nombre y detalles
            // Añadir nombre del doctor al panel
            JLabel nameLabel = new JLabel("Name: " + doctor.getName());
            doctorInfoPanel.add(nameLabel);
            // Obtener la especialidad del doctor
            Speciality speciality = doctor.getSpeciality();
            // Añadir la especialidad al panel
            JLabel specialityLabel = new JLabel("Speciality: " + (speciality != null ? speciality.getName() : "Unknown"));
            doctorInfoPanel.add(specialityLabel);
            // Añadir el correo electrónico del doctor al panel
            JLabel emailLabel = new JLabel("Email: " + doctor.getEmail());
            doctorInfoPanel.add(emailLabel);
            // Añadir el panel de información del doctor al panel de resultados
            resultPanel.add(doctorInfoPanel);
        }
    }


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
   
       
    }


}
