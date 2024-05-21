package UI;

import javax.swing.*;
import FertilityClinicInterfaces.*;
import FertilityClinicJDBC.*;
import FertilityClinicJPA.JPAUserManager;
import FertilityClinicPOJOs.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MenuUI extends JFrame {

    private JDBCManager manager;
    private UserManager userManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager; // Ensure this is initialized
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
        appointmentManager = new JDBCAppointmentManager(manager); // Initialize appointmentManager here

        userManager = new JPAUserManager(); // Using JPAUserManager

        showInitialDialog();
    }

    private void showInitialDialog() {
        // Dialog to choose between Login and Sign Up
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
        // Login form
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
        JPanel panel = new JPanel(new GridLayout(11, 2)); // Ajustado para más campos
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField bloodTypeField = new JTextField();
        JTextField dobField = new JTextField(); // Para fecha de nacimiento
        JTextField genderField = new JTextField();
        JComboBox<Role> roleComboBox = new JComboBox<>(new DefaultComboBoxModel<>(userManager.getRoles().toArray(new Role[0])));
        
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);
        panel.add(new JLabel("Blood Type:"));
        panel.add(bloodTypeField);
        panel.add(new JLabel("DOB (yyyy-mm-dd):"));
        panel.add(dobField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Role selectedRole = (Role) roleComboBox.getSelectedItem();

            if ("patient".equals(selectedRole.getName())) {
                try {
                    // Convert and validate data
                    java.sql.Date dob = java.sql.Date.valueOf(dobField.getText()); // Simple date parsing, consider using a more robust method
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    int phone = Integer.parseInt(phoneField.getText());

                    // Hash password
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] hashedPassword = md.digest();

                    // Create user and patient
                    User newUser = new User(email, hashedPassword, selectedRole);
                    Patient newPatient = new Patient(nameField.getText(), dob, emailField.getText(), phone, height, weight, bloodTypeField.getText(), genderField.getText());

                    userManager.newUser(newUser);
                    patientManager.addPatient(newPatient);

                    JOptionPane.showMessageDialog(this, "Patient registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing registration", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    private void loadUserInterface(User user) {
        // User interface setup
        getContentPane().removeAll();
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
        // Top panel with logout button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            getContentPane().removeAll();
            showInitialDialog();
        });
        topPanel.add(logoutButton);
        return topPanel;
    }

    private JPanel createSidePanel(Role role) {
        // Side panel setup based on user role
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        if ("manager".equals(role.getName())) {
            JButton manageDoctorsButton = new JButton("Manage Doctors");
            JButton managePatientsButton = new JButton("Manage Patients");
            sidePanel.add(manageDoctorsButton);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
            sidePanel.add(managePatientsButton);
        } else if ("doctor".equals(role.getName())) {
            JButton viewPatientsButton = new JButton("View Patients");
            sidePanel.add(viewPatientsButton);
        } else if ("patient".equals(role.getName())) {
            JButton viewAppointmentsButton = new JButton("View Appointments");
            sidePanel.add(viewAppointmentsButton);
        }
        return sidePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUI().setVisible(true));
    }//Hola
}
