package UI;

import javax.swing.*;

import FertilityClinicInterfaces.*;
import FertilityClinicJPA.JPAUserManager;
import FertilityClinicJDBC.*;
import FertilityClinicPOJOs.Role;
import FertilityClinicPOJOs.User;

import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MenuUI extends JFrame {
    
    private JDBCManager manager;
    private UserManager userManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
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
        
        userManager = new JPAUserManager(); // Aquí usamos JPAUserManager
        
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
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<Role> roleComboBox = new JComboBox<>(new DefaultComboBoxModel<>(userManager.getRoles().toArray(new Role[0])));
        
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

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

                JOptionPane.showMessageDialog(this, "Sign Up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showLoginDialog();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadUserInterface(User user) {
        getContentPane().removeAll();
        
        // Agregar los elementos comunes a todos los roles
        add(createTopPanel(), BorderLayout.NORTH);
        add(createSidePanel(user.getRole()), BorderLayout.WEST);

        // Determinar el panel específico del usuario y agregarlo al centro
        JPanel userPanel;
        if ("doctor".equals(user.getRole().getName())) {
            userPanel = new DoctorPanel();
        } else if ("patient".equals(user.getRole().getName())) {
            userPanel = new PatientPanel();
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
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
            viewPatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            sidePanel.add(viewPatientsButton);
        } else if ("patient".equals(role.getName())) {
            JButton viewAppointmentsButton = new JButton("View Appointments");
            viewAppointmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            sidePanel.add(viewAppointmentsButton);
        }

        return sidePanel;
    }

    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Main Area"));
        JLabel label = new JLabel("Welcome to Fertility Clinic System!");
        label.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(label);
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUI().setVisible(true));
    }
}
