package UI;

import javax.swing.*;


import FertilityClinicInterfaces.*;
import FertilityClinicJPA.JPAUserManager;
import FertilityClinicJDBC.*;
import FertilityClinicPOJOs.*;
import FertilityClinicXML.XMLManagerImpl;

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
    private TreatmentManager treatmentManager;
    private SpecialityManager specialityManager;
    private StockManager stockManager;
    private User loggedInUser;
    private JPanel rightPanel;  
    private CardLayout cardLayout;
    private XMLManager xmlmanager;
   
    public MenuUI() {
        
        manager = new JDBCManager();

        
        specialityManager = new JDBCSpecialityManager(manager);

        doctorManager = new JDBCDoctorManager(manager, specialityManager);
        treatmentManager = new JDBCTreatmentManager(manager);
        patientManager = new JDBCPatientManager(manager, treatmentManager);
        managerManager = new JDBCManagerManager(manager);
        appointmentManager = new JDBCAppointmentManager(manager, doctorManager, patientManager);
        treatmentManager = new JDBCTreatmentManager(manager);
        stockManager =new JDBCStockManager(manager);
        userManager = new JPAUserManager();
        xmlmanager=new XMLManagerImpl(); 
        
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
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0)); // Añadir espacio extra arriba
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
        loginButton.setFont(new Font("Calibri", Font.BOLD, 18));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Calibri", Font.BOLD, 18));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            loggedInUser = userManager.checkPassword(email, password);
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(loginPanel, "Login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUserInterface(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                cardLayout.show(rightPanel, "Login");
            }
        });

        cancelButton.addActionListener(e -> cardLayout.show(rightPanel, "Initial"));

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.setBackground(new Color(25, 25, 112));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 

        loginPanel.add(fieldsPanel, BorderLayout.NORTH);
        loginPanel.add(southPanel, BorderLayout.CENTER);

        return loginPanel;
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

        JPanel defaultPanel = new JPanel();
        defaultPanel.setBackground(new Color(25, 25, 112));

        // Patient Panel
        JPanel patientPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        patientPanel.setBackground(new Color(25, 25, 112));

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField bloodTypeField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField genderField = new JTextField();
        JLabel nameLabel = createLabel("Name:");
        JLabel phoneLabel = createLabel("Phone:");
        JLabel heightLabel = createLabel("Height:");
        JLabel weightLabel = createLabel("Weight:");
        JLabel bloodTypeLabel = createLabel("Blood Type:");
        JLabel dobLabel = createLabel("DOB (yyyy-mm-dd):");
        JLabel genderLabel = createLabel("Gender:");
        patientPanel.add(nameLabel);
        patientPanel.add(nameField);
        patientPanel.add(phoneLabel);
        patientPanel.add(phoneField);
        patientPanel.add(heightLabel);
        patientPanel.add(heightField);
        patientPanel.add(weightLabel);
        patientPanel.add(weightField);
        patientPanel.add(bloodTypeLabel);
        patientPanel.add(bloodTypeField);
        patientPanel.add(dobLabel);
        patientPanel.add(dobField);
        patientPanel.add(genderLabel);
        patientPanel.add(genderField);

        // Doctor Panel
        JPanel doctorPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        doctorPanel.setBackground(new Color(25, 25, 112));

        JTextField doctorNameField = new JTextField();
        doctorNameField.setFont(new Font("Calibri", Font.PLAIN, 18)); 
        JTextField doctorPhoneField = new JTextField();
        doctorPhoneField.setFont(new Font("Calibri", Font.PLAIN, 18));  

        JComboBox<Speciality> specialityComboBox = new JComboBox<>(new DefaultComboBoxModel<>(getSpecialities().toArray(new Speciality[0])));
        specialityComboBox.setFont(new Font("Calibri", Font.PLAIN, 18));  

        doctorPanel.add(createLabel("Name:"));
        doctorPanel.add(doctorNameField);
        doctorPanel.add(createLabel("Phone:"));
        doctorPanel.add(doctorPhoneField);
        doctorPanel.add(createLabel("Speciality:"));
        doctorPanel.add(specialityComboBox);

        // Manager Panel
        JPanel managerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        managerPanel.setBackground(new Color(25, 25, 112));

        JTextField managerNameField = new JTextField();
        managerNameField.setFont(new Font("Calibri", Font.PLAIN, 18));  
        JTextField managerPhoneField = new JTextField();
        managerPhoneField.setFont(new Font("Calibri", Font.PLAIN, 18));  

        managerPanel.add(createLabel("Name:"));
        managerPanel.add(managerNameField);
        managerPanel.add(createLabel("Phone:"));
        managerPanel.add(managerPhoneField);

        roleSpecificPanel.add(defaultPanel, "default");
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
        signupButton.setFont(new Font("Calibri", Font.BOLD, 18));
        signupButton.setBackground(Color.WHITE);
        signupButton.setForeground(Color.BLACK);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Calibri", Font.BOLD, 18));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);

        signupButton.addActionListener(e -> {
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
                    Doctor newDoctor = new Doctor(null, email, phone, doctorNameField.getText(), speciality);
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
                JOptionPane.showMessageDialog(signupPanel, "Error during sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(signupPanel, "Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(signupPanel, "An error occurred during the sign-up process.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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


    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE); 
        label.setFont(new Font("Calibri", Font.BOLD, 20));  
        return label;
    }


    public static ArrayList<Speciality> getSpecialities() {
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
                userPanel = new DoctorPanel(doctorManager,patientManager, appointmentManager, treatmentManager,stockManager, doctor.getId());
            } else {
                userPanel = new JPanel();
                userPanel.add(new JLabel("Doctor record not found."));
            }
        } else if ("patient".equals(user.getRole().getName())) {
            Patient patient = patientManager.getPatientByEmail(user.getEmail());
            if (patient != null) {
                userPanel = new PatientPanel(patientManager, appointmentManager, doctorManager, treatmentManager, patient.getId());
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
        	
        } else {
            userPanel = new JPanel();
            userPanel.add(new JLabel("Unknown role specified."));
        }

        add(userPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(25, 25, 112)); 

        JLabel titleLabel = new JLabel("-- NEW LIFE CLINIC --");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30)); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setHorizontalAlignment(JLabel.LEFT); 

        JPanel titleContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); 
        titleContainer.setBackground(new Color(25, 25, 112)); 
        titleContainer.add(titleLabel); 

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Calibri", Font.BOLD, 20));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(Color.BLACK);

        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            getContentPane().removeAll();
            showInitialDialog();
        });

        topPanel.add(titleContainer, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); 
        buttonPanel.setBackground(new Color(25, 25, 112));
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        return topPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUI().setVisible(true));
    }
}
