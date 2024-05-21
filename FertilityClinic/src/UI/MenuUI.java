package UI;

import javax.swing.*;

import FertilityClinicInterfaces.*;
import FertilityClinicJPA.JPAUserManager;
import FertilityClinicJDBC.*;
import FertilityClinicPOJOs.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class MenuUI extends JFrame {
    
    private JDBCManager manager;
    private UserManager userManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private ManagerManager managerManager;
    private AppointmentManager appointmentManager; 
    private User loggedInUser;
   
    public MenuUI() {
        super("Fertility Clinic System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        manager = new JDBCManager();
        doctorManager = new JDBCDoctorManager(manager);
        patientManager = new JDBCPatientManager(manager);
        managerManager = new JDBCManagerManager(manager);
        appointmentManager = new JDBCAppointmentManager(manager); 
        
        userManager = new JPAUserManager(); 
        
        showInitialDialog();
    }
    

    private void showInitialDialog() {
        String[] options = {"Login", "Sign Up"};
        int choice = JOptionPane.showOptionDialog(this, "Do you want to Login or Sign Up?", 
                                                  "Welcome", JOptionPane.DEFAULT_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE, null, 
                                                  options, options[0]);

        if (choice == 0) {
            showLoginDialog();
        } else if (choice == 1) {
            showSignUpDialog();
        }
    }	 

    private void showLoginDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);


        int result = JOptionPane.showConfirmDialog(this, panel, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            loggedInUser = userManager.checkPassword(email, password);

            if (loggedInUser != null) {
                loadUserInterface(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                showLoginDialog();
            }
        }
    }
    private void showSignUpDialog() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel initialPanel = new JPanel(new GridLayout(3, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<Role> roleComboBox = new JComboBox<>(new DefaultComboBoxModel<>(userManager.getRoles().toArray(new Role[0])));
        
        initialPanel.add(new JLabel("Email:"));
        initialPanel.add(emailField);
        initialPanel.add(new JLabel("Password:"));
        initialPanel.add(passwordField);
        initialPanel.add(new JLabel("Role:"));
        initialPanel.add(roleComboBox);

        JPanel roleSpecificPanel = new JPanel(new CardLayout());

        //Patient
        JPanel patientPanel = new JPanel(new GridLayout(8, 2));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField bloodTypeField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField genderField = new JTextField();

        patientPanel.add(new JLabel("Name:"));
        patientPanel.add(nameField);
        patientPanel.add(new JLabel("Phone:"));
        patientPanel.add(phoneField);
        patientPanel.add(new JLabel("Height:"));
        patientPanel.add(heightField);
        patientPanel.add(new JLabel("Weight:"));
        patientPanel.add(weightField);
        patientPanel.add(new JLabel("Blood Type:"));
        patientPanel.add(bloodTypeField);
        patientPanel.add(new JLabel("DOB (yyyy-mm-dd):"));
        patientPanel.add(dobField);
        patientPanel.add(new JLabel("Gender:"));
        patientPanel.add(genderField);

        // Doctor 
        JPanel doctorPanel = new JPanel(new GridLayout(5, 2));
        JTextField doctorNameField = new JTextField();
        JTextField doctorPhoneField = new JTextField();
        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(getSpecialities().toArray(new Speciality[0])));
        
        doctorPanel.add(new JLabel("Name:"));
        doctorPanel.add(doctorNameField);
        doctorPanel.add(new JLabel("Phone:"));
        doctorPanel.add(doctorPhoneField);
        doctorPanel.add(new JLabel("Speciality:"));
        doctorPanel.add(specialityComboBox);
        
        //Manager
        JPanel managerPanel = new JPanel(new GridLayout(5, 2));
        JTextField managerNameField = new JTextField();
        JTextField managerPhoneField = new JTextField();
        
        
        managerPanel.add(new JLabel("Name:"));
        managerPanel.add(managerNameField);
        managerPanel.add(new JLabel("Phone:"));
        managerPanel.add(managerPhoneField);
        

        roleSpecificPanel.add(new JPanel(), "default"); // Default empty panel
        roleSpecificPanel.add(patientPanel, "patient");
        roleSpecificPanel.add(doctorPanel, "doctor");
        roleSpecificPanel.add(managerPanel, "manager");

        roleComboBox.addActionListener(e -> {
            CardLayout cl = (CardLayout) roleSpecificPanel.getLayout();
            Role selectedRole = (Role) roleComboBox.getSelectedItem();
            if (selectedRole != null) {
                cl.show(roleSpecificPanel, selectedRole.getName());
            }
        });

        panel.add(initialPanel, BorderLayout.NORTH);
        panel.add(roleSpecificPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Role selectedRole = (Role) roleComboBox.getSelectedItem();

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] hashedPassword = md.digest();

                User newUser = new User(email, hashedPassword, selectedRole);
                userManager.newUser(newUser);

                if ("patient".equals(selectedRole.getName())) {
                    java.sql.Date dob = java.sql.Date.valueOf(dobField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    int phone = Integer.parseInt(phoneField.getText());

                    Patient newPatient = new Patient(nameField.getText(), dob, email, phone, height, weight, bloodTypeField.getText(), genderField.getText());
                    patientManager.addPatient(newPatient);
                    JOptionPane.showMessageDialog(this, "Patient registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else if ("doctor".equals(selectedRole.getName())) {
                    int phone = Integer.parseInt(doctorPhoneField.getText());
                    Speciality speciality = (Speciality) specialityComboBox.getSelectedItem();

                    Doctor newDoctor = new Doctor(null,email, phone, doctorNameField.getText(), speciality);
                   
                    doctorManager.createDoctor(newDoctor);
                    JOptionPane.showMessageDialog(this, "Doctor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else if ("manager".equals(selectedRole.getName())) {
                    int phone = Integer.parseInt(managerPhoneField.getText());
                    Manager newManager = new Manager(null, email, phone, managerNameField.getText());
                    managerManager.addManager(newManager);
                    JOptionPane.showMessageDialog(this, "Manager registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

                showLoginDialog();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    private ArrayList<Speciality> getSpecialities() {
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality(1, "Urologist"));
        specialities.add(new Speciality(2, "Gynecologist"));
        specialities.add(new Speciality(3, "Psychologist"));
        return specialities;
    }

    

    private void loadUserInterface(User user) {
        getContentPane().removeAll();
        
        // elementos comunes a todos los roles
        add(createTopPanel(), BorderLayout.NORTH);
        add(createSidePanel(user.getRole()), BorderLayout.WEST);

        JPanel userPanel;
        if ("doctor".equals(user.getRole().getName())) {
            userPanel = new DoctorPanel();
        } else if ("patient".equals(user.getRole().getName())) {
        	 userPanel = new PatientPanel(patientManager, appointmentManager, user.getId());
        } else if ("manager".equals(user.getRole().getName())) {
            userPanel = new ManagerPanel();
        } else {
            userPanel = new JPanel();
            userPanel.add(new JLabel("Error: Unknown role."));
        }

        add(userPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton logoutButton = new JButton("Logout");
        topPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            getContentPane().removeAll();
            showInitialDialog();
        });
        

        return topPanel;
    }
    

    private JPanel createSidePanel(Role role) {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//alome quitar

        if ("manager".equals(role.getName())) {
            JButton manageDoctorsButton = new JButton("Manage Doctors");
            JButton managePatientsButton = new JButton("Manage Patients");
            manageDoctorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            managePatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            sidePanel.add(manageDoctorsButton);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
            sidePanel.add(managePatientsButton);
        } else if ("doctor".equals(role.getName())) {
            JButton viewPatientsButton = new JButton("View Patients");
            viewPatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);//alome quitar

            sidePanel.add(viewPatientsButton);
        } else if ("patient".equals(role.getName())) {
            JButton viewAppointmentsButton = new JButton("View Appointments");
            viewAppointmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);//alome quitar

            sidePanel.add(viewAppointmentsButton);
        }

        return sidePanel;
    }
    
   
/*
    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Main Area"));
        JLabel label = new JLabel("Welcome to Fertility Clinic System!");
        label.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(label);
        return mainPanel;
    }*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUI().setVisible(true));
    }
}
