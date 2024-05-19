package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import FertilityClinicIfaces.OwnerManager;
import FertilityClinicIfaces.PetManager;
import FertilityClinicIfaces.UserManager;
import FertilityClinicJDBC.JDBCManager;
import FertilityClinicJDBC.JDBCDoctorManager;
import FertilityClinicJDBC.JDBCPatientManager;
import FertilityClinicJPA.JPAUserManager;
import FertilityClinicPOJOs.Role;
import FertilityClinicPOJOs.User;

public class MenuUI extends JFrame {
    private UserManager userManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;

    public MenuUI() {
        super("Fertility Clinic System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        userManager = new JPAUserManager();
        doctorManager = new JDBCDoctorManager(new JDBCManager());
        patientManager = new JDBCPatientManager(new JDBCManager());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createSidePanel(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        topPanel.add(loginButton);
        topPanel.add(signUpButton);

        loginButton.addActionListener(e -> showLoginDialog());
        signUpButton.addActionListener(e -> showSignUpDialog());

        return topPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton manageDoctorsButton = new JButton("Manage Doctor");
        JButton managePatientsButton = new JButton("Manage Patient");
        manageDoctorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        managePatientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel.add(manageDoctorsButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(managePatientsButton);

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

    private void showLoginDialog() {
        // Implement login dialog logic
        JOptionPane.showMessageDialog(this, "Login Dialog Placeholder");
    }

    private void showSignUpDialog() {
        // Implement sign-up dialog logic
        JOptionPane.showMessageDialog(this, "Sign-Up Dialog Placeholder");
    }

    public static void main(String[] args) {
        new MenuUI();
    }
}

