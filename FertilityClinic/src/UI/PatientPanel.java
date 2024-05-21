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
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private DoctorManager doctorManager;
    private int patientId;

    public PatientPanel(PatientManager patientManager, AppointmentManager appointmentManager, int patientId) {
        this.patientManager = patientManager;
        this.patientId = patientId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(panelesLadoIzq(), BorderLayout.WEST);
    }

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
/*
    private void viewMyinfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);
        
        // Create a panel to display patient information
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

        add(infoPanel, BorderLayout.CENTER); 
        validate();
        repaint();
    }*/
    
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

        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private void updateInfoPanel() {
        Patient patient = patientManager.viewMyInfo(patientId);
        
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(8, 2));

        JTextField emailField = new JTextField(patient != null ? patient.getEmail() : "");
        JTextField phoneField = new JTextField(patient != null ? String.valueOf(patient.getPhone()) : "");
        JTextField nameField = new JTextField(patient != null ? patient.getName() : "");
        JTextField heightField = new JTextField(patient != null ? String.valueOf(patient.getHeight()) : "");
        JTextField weightField = new JTextField(patient != null ? String.valueOf(patient.getWeight()) : "");

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
            viewMyinfoPanel(); // Refresh view after update
        });

        updatePanel.add(new JLabel());
        updatePanel.add(updateBtn);

        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(updatePanel, BorderLayout.CENTER);
        validate();
        repaint();
    }
    
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

        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(appointmentsMainPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private JPanel currentAppointmentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ArrayList<Appointment> appointments = appointmentManager.getCurrentAppointments(patientId);

        for (Appointment appointment : appointments) {
            panel.add(new JLabel("Appointment with Dr. " + doctorManager.searchDoctorById(appointment.getDoctorId()) + " on " + appointment.getDate() + " at " + appointment.getTime()));
        }

        return panel;
    }

    private void addAppointmentPanel() {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 2));

        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField doctorIdField = new JTextField();

        addPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        addPanel.add(dateField);
        addPanel.add(new JLabel("Time (HH:MM):"));
        addPanel.add(timeField);
        addPanel.add(new JLabel("Doctor ID:"));
        addPanel.add(doctorIdField);

        JButton addBtn = new JButton("Add Appointment");
        addBtn.addActionListener(e -> {
            String date = dateField.getText();
            String time = timeField.getText();
            int doctorId = Integer.parseInt(doctorIdField.getText());
            appointmentManager.bookAppointment(patientId, doctorId, date, time);
            JOptionPane.showMessageDialog(this, "Appointment added successfully.");
            appointmentsPanel(); // Refresh appointments panel after adding
        });

        addPanel.add(new JLabel());
        addPanel.add(addBtn);

        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(addPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private void updateAppointmentPanel() {
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));

        List<Appointment> appointments = appointmentManager.getCurrentAppointments(patientId);

        for (Appointment appointment : appointments) {
            JPanel singleAppointmentPanel = new JPanel(new GridLayout(1, 4));
            singleAppointmentPanel.add(new JLabel("Dr. " + appointment.getDoctorName()));
            JTextField dateField = new JTextField(appointment.getDate());
            JTextField timeField = new JTextField(appointment.getTime());
            singleAppointmentPanel.add(dateField);
            singleAppointmentPanel.add(timeField);

            JButton updateBtn = new JButton("Update");
            updateBtn.addActionListener(e -> {
                String newDate = dateField.getText();
                String newTime = timeField.getText();
                appointmentManager.updateAppointment(appointment.getId(), newDate, newTime);
                JOptionPane.showMessageDialog(this, "Appointment updated successfully.");
                appointmentsPanel(); // Refresh appointments panel after updating
            });

            singleAppointmentPanel.add(updateBtn);
            updatePanel.add(singleAppointmentPanel);
        }

        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(updatePanel, BorderLayout.CENTER);
        validate();
        repaint();
    }





    private void updateAppointmentsView() {
        // Assume this method is implemented correctly
    }
}
