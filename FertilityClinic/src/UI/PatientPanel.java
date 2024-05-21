package UI;

import java.awt.*;
import javax.swing.*;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Appointment;
import java.util.ArrayList;

/*
public class PatientPanel extends JPanel {
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private int patientId;

    public PatientPanel(PatientManager patientManager, AppointmentManager appointmentManager, int patientId) {
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.patientId = patientId;
        //initializeUI();
    }
   /*
    private void initializeUI() {
        setLayout(new BorderLayout());
        add(createContentPanel(), BorderLayout.CENTER);
        JButton viewAppointmentsBtn = new JButton("View Appointments");
        viewAppointmentsBtn.addActionListener(e -> updateAppointmentsView());
        add(viewAppointmentsBtn, BorderLayout.WEST);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        Patient patient = patientManager.viewMyInfo(patientId);
        String patientInfo = (patient != null) ? patient.toString() : "No information available.";
        JTextArea infoArea = new JTextArea(patientInfo);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        panel.add(scrollPane);
        return panel;
    }

    private void updateAppointmentsView() {
        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(patientId);
        JPanel appointmentsPanel = new JPanel();
        appointmentsPanel.setLayout(new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS));

        for (Appointment ap : appointments) {
            appointmentsPanel.add(new JLabel(ap.toString()));
        }

        JScrollPane scrollPane = new JScrollPane(appointmentsPanel);
        add(scrollPane, BorderLayout.CENTER);
        validate();
        repaint();
    }
}
*/


public class PatientPanel extends JPanel {
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private int patientId;

    public PatientPanel(PatientManager patientManager, AppointmentManager appointmentManager, int patientId) {
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.patientId = patientId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(createButtonPanel(), BorderLayout.WEST);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton viewMyInfoBtn = new JButton("View My Information");
        JButton extraOption1Btn = new JButton("Extra Option 1");
        JButton extraOption2Btn = new JButton("Extra Option 2");

        viewMyInfoBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, viewMyInfoBtn.getMinimumSize().height));
        extraOption1Btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, extraOption1Btn.getMinimumSize().height));
        extraOption2Btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, extraOption2Btn.getMinimumSize().height));

        viewMyInfoBtn.addActionListener(e -> updatePatientInfoView());

        buttonPanel.add(viewMyInfoBtn);
        buttonPanel.add(extraOption1Btn);
        buttonPanel.add(extraOption2Btn);

        return buttonPanel;
    }


    private void updatePatientInfoView() {
        // Fetch the patient information from the database
        Patient patient = patientManager.viewMyInfo(patientId);
        String patientInfo = (patient != null) ? patient.toString() : "No information available.";
        JTextArea infoArea = new JTextArea(patientInfo);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        // Clear the panel and add new content
        removeAll();
        add(scrollPane, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private void updateAppointmentsView() {
        // Assume this method is implemented correctly
    }
}
