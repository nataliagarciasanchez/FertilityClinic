package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Treatments;

public interface TreatmentsManager {
	public void addTreatment(Treatments treatment);
    public Treatments getTreatmentById(Integer id);
    public void updateTreatment(Treatments treatment);
    public void deleteTreatment(int id);
    public List<Treatments> searchTreatmentsByPatientName(String name);
    public List<Treatments> searchTreatmentsByDoctorId(int doctorId);
   
    
}


package UI;

import javax.swing.*;
import java.awt.*;
import FertilityClinicInterfaces.*;
import FertilityClinicPOJOs.*;

public class DoctorPanel extends JPanel {

    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private int doctorId;

    public DoctorPanel(DoctorManager doctorManager, PatientManager patientManager, AppointmentManager appointmentManager, int doctorId) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.doctorId = doctorId;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(panelesLadoIzq(), BorderLayout.WEST);
    }

    private JPanel panelesLadoIzq() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton viewInfoButton = new JButton("View My Information");
        JButton updateInfoButton = new JButton("Update My Information");
        JButton viewAppointmentsButton = new JButton("My Appointments");
        JButton viewPatientsButton = new JButton("View All my Patients");
        JButton mySpecialityButton = new JButton("My Speciality");

        viewInfoButton.addActionListener(e -> viewMyInfoPanel());
        updateInfoButton.addActionListener(e -> updateInfoPanel());
        viewAppointmentsButton.addActionListener(e -> viewAppointmentsPanel());
        viewPatientsButton.addActionListener(e -> viewAllPatientsPanel());
        mySpecialityButton.addActionListener(e -> viewMySpecialityPanel());

        buttonPanel.add(viewInfoButton);
        buttonPanel.add(updateInfoButton);
        buttonPanel.add(viewAppointmentsButton);
        buttonPanel.add(viewPatientsButton);
        buttonPanel.add(mySpecialityButton);

        return buttonPanel;
    }

    // View doctor's personal information
    private void viewMyInfoPanel() {
        Doctor doctor = doctorManager.viewMyInfo(doctorId);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        if (doctor != null) {
            infoPanel.add(new JLabel("Name: " + doctor.getName()));
            infoPanel.add(new JLabel("Email: " + doctor.getEmail()));
            infoPanel.add(new JLabel("Speciality: " + doctor.getSpeciality().getName())); // Assuming Doctor has a Speciality field
        } else {
            infoPanel.add(new JLabel("No information available."));
        }
        showPanel(infoPanel);
    }

    // View all patients assigned to this doctor
    private void viewAllPatientsPanel() {
        List<Patient> patients = patientManager.getPatientsByDoctorId(doctorId); // Assuming such a method exists
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        for (Patient patient : patients) {
            patientPanel.add(new JLabel("Patient: " + patient.getName() + " - " + patient.getEmail()));
        }
        showPanel(patientPanel);
    }

    // View appointments for this doctor
    private void viewAppointmentsPanel() {
        ArrayList<Appointment> appointments = appointmentManager.viewAppointment(doctorId); // Assuming method allows doctorId
        JPanel apptPanel = new JPanel();
        apptPanel.setLayout(new BoxLayout(apptPanel, BoxLayout.Y_AXIS));
        for (Appointment appt : appointments) {
            apptPanel.add(new JLabel("Appointment: " + appt.getDescription() + " on " + appt.getDate()));
        }
        showPanel(apptPanel);
    }

    // Update information panel (sample layout, real implementation needs more details)
    private void updateInfoPanel() {
        // Implementation required similar to viewMyInfoPanel but with editable fields
    }

    // View the speciality of the doctor
    private void viewMySpecialityPanel() {
        // Implementation required based on how speciality data is managed
    }

    // Utility to switch displayed panel
    private void showPanel(JPanel panel) {
        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
