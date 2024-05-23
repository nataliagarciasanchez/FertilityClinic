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
   
    public MenuUI() {
        super("Fertility Clinic System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
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
        mainPanel.setBackground(new Color(135, 206, 235));  // Color azul claro

        JLabel imageLabel = new JLabel();
        try {
            File imagePath = new File("./logo/photo.png");  // Suponiendo que la carpeta logo está en el mismo nivel que src
            if (!imagePath.exists()) {
                throw new IllegalArgumentException("Image file not found at: " + imagePath.getAbsolutePath());
            }
            ImageIcon originalIcon = new ImageIcon(imagePath.getAbsolutePath());
            Image image = originalIcon.getImage(); // Transform it 
            Image newimg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // Scale it the smooth way  
            imageLabel.setIcon(new ImageIcon(newimg));  // Transform it back
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("Image not found: " + e.getMessage());
        }
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.WEST);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(135, 206, 235));
        JLabel titleLabel = new JLabel("NEW LIFE CLINIC", JLabel.CENTER);
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 70)); // Fuente más grande
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0)); // Espacio arriba del título
        rightPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel questionLabel = new JLabel("Do you want to log in or sign up?");
        questionLabel.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(135, 206, 235));
        JButton loginButton = new JButton("Log in");
        JButton signupButton = new JButton("Sign up");

        loginButton.setBackground(Color.WHITE);
        signupButton.setBackground(Color.WHITE);

        loginButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
        signupButton.setFont(new Font("Cooper Black", Font.BOLD, 16));

        // Añadir funcionalidad a los botones
        loginButton.addActionListener(e -> showLoginDialog());
        signupButton.addActionListener(e -> showSignUpDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        // Añade más espacio en la parte inferior
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0)); // Aumentar margen inferior

        rightPanel.add(questionLabel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }



    //ESTE ES EL QUE FUNCIONA
    /*
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
    }	*/ 

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
        }if (result == JOptionPane.OK_CANCEL_OPTION) {
        	showSignUpDialog();
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
        }if (result == JOptionPane.OK_CANCEL_OPTION) {
        	showSignUpDialog();
        }
    }
 


    

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
