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
import java.io.File;


public class MenuUI extends JFrame {
    
    private JDBCManager manager;
    private UserManager userManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private ManagerManager managerManager;
    private AppointmentManager appointmentManager; 
    private TreatmentsManager treatmentManager;
    private SpecialityManager specialityManager;
    private StockManager stockManager;
    private User loggedInUser;
   // private  JPanel rightPanel = new JPanel(new CardLayout());
    private JPanel rightPanel;  // Usar CardLayout para manejar paneles
    private CardLayout cardLayout;
   
    public MenuUI() {
        
        manager = new JDBCManager();

        
        specialityManager = new JDBCSpecialityManager(manager);

        doctorManager = new JDBCDoctorManager(manager, specialityManager);
        treatmentManager = new JDBCTreatmentsManager(manager);
        patientManager = new JDBCPatientManager(manager, treatmentManager);
        managerManager = new JDBCManagerManager(manager);
        appointmentManager = new JDBCAppointmentManager(manager, doctorManager, patientManager);

        userManager = new JPAUserManager();
        
        userManager = new JPAUserManager(); 
        
        showInitialDialog();
    }
   

    private void showInitialDialog() {
        setTitle("NEW LIFE CLINIC");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width - 100, screenSize.height - 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(25, 25, 112));

        JLabel imageLabel = setupImageLabel();
        mainPanel.add(imageLabel, BorderLayout.WEST);

        rightPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) rightPanel.getLayout();
        rightPanel.setBackground(new Color(25, 25, 112));

        JPanel loginPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();
        rightPanel.add(loginPanel, "Login");
        rightPanel.add(signupPanel, "Signup");

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(25, 25, 112));

        JLabel titleLabel = new JLabel("NEW LIFE CLINIC", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 70));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel initialPanel = new JPanel(new BorderLayout());
        initialPanel.setBackground(new Color(25, 25, 112));
        JLabel questionLabel = new JLabel("Do you want to log in or sign up?");
        questionLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setForeground(Color.WHITE);
        initialPanel.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(25, 25, 112));
        JButton loginButton = new JButton("Log in");
        JButton signupButton = new JButton("Sign up");
        loginButton.setBackground(Color.WHITE);
        signupButton.setBackground(Color.WHITE);
        loginButton.setFont(new Font("Calibri", Font.BOLD, 16));
        signupButton.setFont(new Font("Calibri", Font.BOLD, 16));
        loginButton.addActionListener(e -> cardLayout.show(rightPanel, "Login"));
        signupButton.addActionListener(e -> cardLayout.show(rightPanel, "Signup"));
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
        initialPanel.add(buttonPanel, BorderLayout.SOUTH);

        rightPanel.add(initialPanel, "Initial");

        contentPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        cardLayout.show(rightPanel, "Initial");  // Mostrar el panel inicial por defecto
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(25, 25, 112));

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(new Color(25, 25, 112));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        JTextField emailField = new JTextField(15);
        emailField.setHorizontalAlignment(JTextField.CENTER);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setHorizontalAlignment(JTextField.CENTER);

        // Ajustes para alinear los componentes más cerca y arriba
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 2, 10); // Menor espacio entre los componentes
        fieldsPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        fieldsPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        fieldsPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Reducir el espacio entre los botones y los campos
        buttonPanel.setBackground(new Color(25, 25, 112));

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, fieldsPanel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                loggedInUser = userManager.checkPassword(email, password);
                if (loggedInUser != null) {
                    System.out.println("Login successful");
                    loadUserInterface(loggedInUser);
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    cardLayout.show(rightPanel, "Login");
                }
            } else if (result == JOptionPane.CANCEL_OPTION) {
                cardLayout.show(rightPanel, "Initial");
            }
        });

        cancelButton.addActionListener(e -> cardLayout.show(rightPanel, "Initial"));

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Reducir el espacio entre los campos y los botones
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.setBackground(new Color(25, 25, 112));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Ajustar el padding para que los botones estén más cerca de los campos

        loginPanel.add(fieldsPanel, BorderLayout.NORTH);
        loginPanel.add(southPanel, BorderLayout.CENTER);

        return loginPanel;
    }



    private JPanel createSignupPanel() {
        JPanel signupPanel = new JPanel(new BorderLayout());
        signupPanel.setBackground(new Color(25, 25, 112));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        fieldsPanel.setBackground(new Color(25, 25, 112));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Calibri", Font.PLAIN, 18));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Calibri", Font.PLAIN, 18));

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        JComboBox<Role> roleComboBox = new JComboBox<>(new DefaultComboBoxModel<>(userManager.getRoles().toArray(new Role[0])));
        roleComboBox.setFont(new Font("Calibri", Font.PLAIN, 18));
        
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(roleLabel);
        fieldsPanel.add(roleComboBox);
        
       // Role specific panels
        JPanel roleSpecificPanel = new JPanel(new CardLayout());
        roleSpecificPanel.setBackground(new Color(25, 25, 112));
        
        // Patient Panel
        JPanel patientPanel = new JPanel(new GridLayout(8, 2));
        patientPanel.setBackground(new Color(25, 25, 112));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField bloodTypeField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField genderField = new JTextField();
        patientPanel.add(new JLabel("Name:"));
        patientPanel.setFont(new Font("Calibri", Font.BOLD, 20));
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

        // Doctor Panel
        JPanel doctorPanel = new JPanel(new GridLayout(5, 2));
        doctorPanel.setBackground(new Color(25, 25, 112));
        JTextField doctorNameField = new JTextField();
        doctorNameField.setForeground(Color.WHITE);
        doctorNameField.setFont(new Font("Calibri", Font.BOLD, 20));
        JTextField doctorPhoneField = new JTextField();
        doctorPhoneField.setForeground(Color.WHITE);
        doctorPhoneField.setFont(new Font("Calibri", Font.BOLD, 20));
        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(getSpecialities().toArray(new Speciality[0])));
        specialityComboBox.setForeground(Color.WHITE);
        specialityComboBox.setFont(new Font("Calibri", Font.BOLD, 20));
        
        doctorPanel.add(new JLabel("Name:"));
        doctorPanel.add(doctorNameField);
        doctorPanel.add(new JLabel("Phone:"));
        doctorPanel.add(doctorPhoneField);
        doctorPanel.add(new JLabel("Speciality:"));
        doctorPanel.add(specialityComboBox);

        // Manager Panel
        JPanel managerPanel = new JPanel(new GridLayout(5, 2));
        managerPanel.setBackground(new Color(25, 25, 112));
        JTextField managerNameField = new JTextField();
        managerNameField.setForeground(Color.WHITE);
        managerNameField.setFont(new Font("Calibri", Font.BOLD, 20));
        
        JTextField managerPhoneField = new JTextField();
        managerPhoneField.setForeground(Color.WHITE);
        managerPhoneField.setFont(new Font("Calibri", Font.BOLD, 20));
        
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(25, 25, 112));
        JButton signupButton = new JButton("Sign up");
        JButton cancelButton = new JButton("Cancel");

        signupButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, fieldsPanel, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
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
                        cardLayout.show(rightPanel, "Login");
                    } else if ("doctor".equals(selectedRole.getName())) {
                    	int phone = Integer.parseInt(doctorPhoneField.getText());
                        Speciality speciality = (Speciality) specialityComboBox.getSelectedItem();
                        Doctor newDoctor = new Doctor(null,email, phone, doctorNameField.getText(), speciality);
                       
                        doctorManager.createDoctor(newDoctor);
                        JOptionPane.showMessageDialog(this, "Doctor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(rightPanel, "Login");
                    } else if ("manager".equals(selectedRole.getName())) {
                    	int phone = Integer.parseInt(managerPhoneField.getText());
                        Manager newManager = new Manager(null, email, phone, managerNameField.getText());
                        managerManager.addManager(newManager);
                        JOptionPane.showMessageDialog(this, "Manager registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(rightPanel, "Login");
                    }

                } catch (NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(null, "Error during sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (result == JOptionPane.CANCEL_OPTION) {
                cardLayout.show(rightPanel, "Initial");
            }
        });
        cancelButton.addActionListener(e -> cardLayout.show(rightPanel, "Initial"));

        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        signupPanel.add(fieldsPanel, BorderLayout.NORTH);
        signupPanel.add(roleSpecificPanel, BorderLayout.CENTER);
        signupPanel.add(buttonPanel, BorderLayout.SOUTH);

        return signupPanel;
    }

    private JLabel setupImageLabel() {
        JLabel imageLabel = new JLabel();
        try {
            File imagePath = new File("./logo/photo.png");
            if (!imagePath.exists()) {
                throw new IllegalArgumentException("Image file not found at: " + imagePath.getAbsolutePath());
            }
            ImageIcon originalIcon = new ImageIcon(imagePath.getAbsolutePath());
            Image image = originalIcon.getImage();
            Image newimg = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(newimg));
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("Image not found: " + e.getMessage());
        }
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        return imageLabel;
    }




 /*   
    private void showInitialDialog() {
        setTitle("NEW LIFE CLINIC");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width - 100, screenSize.height - 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(25, 25, 112));  // Dark blue color

        JLabel imageLabel = setupImageLabel();
        mainPanel.add(imageLabel, BorderLayout.WEST);

      
        rightPanel.setBackground(new Color(25, 25, 112));
        
        JPanel lohinPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();
        rightPanel.add(loginpanel, "login");
        rightPanel.add(signupPanel, "Signup");
        
        
        JLabel titleLabel = new JLabel("NEW LIFE CLINIC", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 70));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        rightPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel questionLabel = new JLabel("Do you want to log in or sign up?");
        questionLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(25, 25, 112));
        JButton loginButton = new JButton("Log in");
        JButton signupButton = new JButton("Sign up");

        loginButton.setBackground(Color.WHITE);
        signupButton.setBackground(Color.WHITE);

        loginButton.setFont(new Font("Calibri", Font.BOLD, 16));
        signupButton.setFont(new Font("Calibri", Font.BOLD, 16));

        loginButton.addActionListener(e -> createLoginPanel());
        signupButton.addActionListener(e -> createSignupPanel());

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0)); // Increase bottom margin

        rightPanel.add(questionLabel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void createLoginPanel() {
    	cardLayout.show(rightPanel, "Login");
        // Crear un panel con un diseño de cuadrícula para los campos de entrada
        JPanel loginpanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginpanel.setBackground(new Color(25, 25, 112)); // Color de fondo consistente

        // Crear los campos de texto y etiquetas con el estilo apropiado
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); // Color de texto para visibilidad
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField();
        
        // Añadir componentes al panel
        loginpanel.add(emailLabel);
        loginpanel.add(emailField);
        loginpanel.add(passwordLabel);
        loginpanel.add(passwordField);
        

        // Mostrar el panel en un diálogo de confirmación
        int result = JOptionPane.showConfirmDialog(this, loginpanel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            // Suponiendo que userManager es un miembro de clase que gestiona la autenticación del usuario
            loggedInUser = userManager.checkPassword(email, password);

            if (loggedInUser != null) {
                loadUserInterface(loggedInUser); // Carga la interfaz principal para el usuario conectado
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                createLoginPanel(); // Reabrir el diálogo de inicio de sesión en caso de falla de autenticación
            }
        } else if (result == JOptionPane.CANCEL_OPTION) {
        	showInitialDialog(); // Si el usuario cancela, ofrecer registrarse en su lugar.
        }
    }

    
    
    
    private void createSignupPanel() {
    	cardLayout.show(rightPanel, "Sign up");
        JPanel panel = new JPanel(new BorderLayout());
        JPanel initialPanel = new JPanel(new GridLayout(3, 2));
        initialPanel.setBackground(new Color(25, 25, 112));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<Role> roleComboBox = new JComboBox<>(new DefaultComboBoxModel<>(userManager.getRoles().toArray(new Role[0])));
        
        panel.add(new JLabel("Email:"));
        emailField.setForeground(Color.WHITE);
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        passwordField.setForeground(Color.WHITE);
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        roleComboBox.setForeground(Color.WHITE);
        panel.add(roleComboBox);
        
        

        JPanel roleSpecificPanel = new JPanel(new CardLayout());
        roleSpecificPanel.setBackground(new Color(25, 25, 112));
        

        //Patient
        JPanel patientPanel = new JPanel(new GridLayout(8, 2));
        patientPanel.setBackground(new Color(25, 25, 112));
        JTextField nameField = new JTextField();
        nameField.setForeground(Color.WHITE);
        JTextField phoneField = new JTextField();
        phoneField.setForeground(Color.WHITE);
        JTextField heightField = new JTextField();
        heightField.setForeground(Color.WHITE);
        JTextField weightField = new JTextField();
        weightField.setForeground(Color.WHITE);
        JTextField bloodTypeField = new JTextField();
        bloodTypeField.setForeground(Color.WHITE);
        JTextField dobField = new JTextField();
        dobField.setForeground(Color.WHITE);
        JTextField genderField = new JTextField();
        genderField.setForeground(Color.WHITE);

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
        JPanel doctorPanel = new JPanel(new GridLayout(8, 2));
        doctorPanel.setBackground(new Color(25, 25, 112));
        JTextField doctorNameField = new JTextField();
        doctorNameField.setForeground(Color.WHITE);
        JTextField doctorPhoneField = new JTextField();
        doctorPhoneField.setForeground(Color.WHITE);
        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(getSpecialities().toArray(new Speciality[0])));
        specialityComboBox.setForeground(Color.WHITE);
        
        doctorPanel.add(new JLabel("Name:"));
        doctorPanel.add(doctorNameField);
        doctorPanel.add(new JLabel("Phone:"));
        doctorPanel.add(doctorPhoneField);
        doctorPanel.add(new JLabel("Speciality:"));
        doctorPanel.add(specialityComboBox);
        
        //Manager
        JPanel managerPanel = new JPanel(new GridLayout(8, 2));
        managerPanel.setBackground(new Color(25, 25, 112));
        JTextField managerNameField = new JTextField();
        managerNameField.setForeground(Color.WHITE);
        JTextField managerPhoneField = new JTextField();
        managerPhoneField.setForeground(Color.WHITE);
        
        
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

        signupPanel.add(initialPanel, BorderLayout.NORTH);
        signupPanel.add(roleSpecificPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, signupPanel, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
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

                createLoginPanel();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }if (result == JOptionPane.OK_CANCEL_OPTION) {
        	showInitialDialog();
        }
    }
 

*/
    

    public static ArrayList<Speciality> getSpecialities() {//esto esta bien aqui??
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality(1, "Urologist"));
        specialities.add(new Speciality(2, "Gynecologist"));
        specialities.add(new Speciality(3, "Psychologist"));
        return specialities;
    }

    

    private void loadUserInterface(User user) {
        getContentPane().removeAll();
        add(createTopPanel(), BorderLayout.NORTH);

        JPanel userPanel;
        if ("doctor".equals(user.getRole().getName())) {
        	Doctor doctor = doctorManager.getDoctorByEmail(user.getEmail());
            if (doctor != null) {
                userPanel = new DoctorPanel(doctorManager,patientManager, appointmentManager, doctor.getId());
            } else {
                userPanel = new JPanel();
                userPanel.add(new JLabel("Doctor record not found."));
            }
        } else if ("patient".equals(user.getRole().getName())) {
            Patient patient = patientManager.getPatientByEmail(user.getEmail());
            if (patient != null) {
                userPanel = new PatientPanel(patientManager, appointmentManager, doctorManager, patient.getId());
            } else {
                userPanel = new JPanel();
                userPanel.add(new JLabel("Patient record not found."));
            }
        } else if ("manager".equals(user.getRole().getName())) {
        	Manager manager = managerManager.getManagerByEmail(user.getEmail());
            if (manager != null) {
            	userPanel = new ManagerPanel(managerManager,doctorManager,patientManager,stockManager,manager.getId());
            } else {
                userPanel = new JPanel();
                userPanel.add(new JLabel("Manager record not found."));
            }
        	
        	
        	 // Assuming ManagerPanel requires a managerManager
        } else {
            userPanel = new JPanel();
            userPanel.add(new JLabel("Unknown role specified."));
        }

        add(userPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    private JPanel createTopPanel() {
        // Crea el panel superior con el botón de logout
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
    }//ultimos
}
