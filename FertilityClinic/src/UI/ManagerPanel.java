package UI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import FertilityClinicInterfaces.ManagerManager;
import FertilityClinicInterfaces.AppointmentManager;
import FertilityClinicInterfaces.DoctorManager;
import FertilityClinicInterfaces.PatientManager;
import FertilityClinicInterfaces.SpecialityManager;
import FertilityClinicInterfaces.StockManager;
import FertilityClinicPOJOs.Appointment;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Manager;
import FertilityClinicPOJOs.Patient;
import FertilityClinicPOJOs.Stock;

public class ManagerPanel extends JPanel {
	
	
	private static final long serialVersionUID = 7116827211267658216L;

	private JPanel currentPanel;
	
    private ManagerManager managerManager;
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private StockManager stockManager;
    private SpecialityManager specialityManager;
    private int managerId;

    // Constructor que acepta interfaces para la gestión de doctores y pacientes
    public ManagerPanel(ManagerManager managerManager,DoctorManager doctorManager,PatientManager patientManager, StockManager stockManager,  int managerId) {
        this.managerManager = managerManager;
        this.doctorManager = doctorManager;
    	this.patientManager = patientManager;
        this.stockManager = stockManager;
        this.managerId = managerId;
        initializeUI();
    }

    private void initializeUI() {
        currentPanel = panelesLadoIzq(); 
        setLayout(new BorderLayout());
        add(currentPanel, BorderLayout.CENTER);
    }
    
    private void showCurrentPanel() {
        removeAll();
        add(panelesLadoIzq(), BorderLayout.WEST); 
        add(currentPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }
    
    
    //Buttons Panel
    private JPanel panelesLadoIzq() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton op1 = new JButton("View My Information");
        JButton op2 = new JButton("Update My Information");
        JButton op3 = new JButton("View Stock");
        JButton op4 = new JButton("Manage doctors");
       
        

        op1.setMaximumSize(new Dimension(Integer.MAX_VALUE, op1.getMinimumSize().height));
        op2.setMaximumSize(new Dimension(Integer.MAX_VALUE, op2.getMinimumSize().height));
        op3.setMaximumSize(new Dimension(Integer.MAX_VALUE, op3.getMinimumSize().height));
        
        op1.addActionListener(e -> viewMyinfoPanel()); //igual para doctor
        op2.addActionListener(e -> updateInfoPanel());//igual para doctor 
        op3.addActionListener(e -> viewMyinfoPanel()); //igual para doctor pero modificar cita patient solo delete y add
        op4.addActionListener(e -> viewMyinfoPanel()); 

        buttonPanel.add(op1);
        buttonPanel.add(op2);
        buttonPanel.add(op3);
       

        return buttonPanel;
    }
  //OPTION 1
    private void viewMyinfoPanel() {
        Manager manager = managerManager.viewMyInfo(managerId);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        if (manager != null) {
            infoPanel.add(new JLabel("Name: " + manager.getName()));
            infoPanel.add(new JLabel("Email: " + manager.getEmail()));
            infoPanel.add(new JLabel("Phone: " + manager.getPhone()));
            
        }

        currentPanel = infoPanel;
        showCurrentPanel();
    }
    //OPCION 2
    private void updateInfoPanel() {
        Manager manager = managerManager.viewMyInfo(managerId);

        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 rows for including button row, with gaps for spacing

        JTextField emailField = new JTextField(manager != null ? manager.getEmail() : "");
        JTextField phoneField = new JTextField(manager != null ? String.valueOf(manager.getPhone()) : "");
        JTextField nameField = new JTextField(manager != null ? manager.getName() : "");
        
        // Adding labels and text fields
        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(nameField);
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(emailField);
        updatePanel.add(new JLabel("Phone:"));
        updatePanel.add(phoneField);
        

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                Integer phone = Integer.parseInt(phoneField.getText());
                String name = nameField.getText();
                
                
                // Llamar al método para modificar la información del paciente con los nuevos valores
                managerManager.modifyManagerInfo(managerId, email, phone, name);
                
                JOptionPane.showMessageDialog(this, "Information updated successfully.");
                
                // Después de actualizar, volver a mostrar la información actualizada
                viewMyinfoPanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid values.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while updating the information.");
                ex.printStackTrace();
            }
        });

        updatePanel.add(new JLabel());
        updatePanel.add(updateBtn);

        // Adding padding around the panel
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        paddedPanel.add(updatePanel, BorderLayout.CENTER);

        // Set a preferred size to ensure the panel is somewhat square
        paddedPanel.setPreferredSize(new Dimension(400, 400));

        
        currentPanel = updatePanel; // Establece el panel actual como el panel de información del paciente
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }
    //OPCION 3
  //OPTION 3
    private void viewStockPanel() {
        JPanel stockMainPanel = new JPanel(new BorderLayout());

        JPanel stockOptionsPanel = new JPanel();
        stockOptionsPanel.setLayout(new BoxLayout(stockOptionsPanel, BoxLayout.Y_AXIS)); // Cambio a BoxLayout con dirección Y

        JButton op1 = new JButton("Update Appointment");
        JButton op2 = new JButton("Add Appointment");

        op1.addActionListener(e -> updateStockPanel());
        op2.addActionListener(e -> addStockPanel());

        stockOptionsPanel.add(op1);
        stockOptionsPanel.add(op2);

        stockMainPanel.add(stockOptionsPanel, BorderLayout.WEST);
        stockMainPanel.add(currentStockPanel(), BorderLayout.CENTER);

        currentPanel = stockMainPanel; // Establece el panel actual como el panel principal
        showCurrentPanel(); // Muestra el panel actual en el contenedor principal
    }
    
    
    private JPanel currentStockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Cambio a GridLayout con una sola columna

        ArrayList<Stock> stocks = (ArrayList<Stock>) stockManager.viewStock(managerId);

        if (stocks.isEmpty()) {
            panel.add(new JLabel("No stocks yet."));
        } else {
            for (Stock stock : stocks) {
                JLabel appointmentLabel = new JLabel("hola");
                panel.add(appointmentLabel);
            }
        }

        return panel;
    }
    
    private void updateStockPanel() {
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));

        ArrayList<Stock> stocks =(ArrayList<Stock>) stockManager.viewStock(managerId);

        if (stocks != null && !stocks.isEmpty()) {
            for (Stock stock : stocks) {
                JPanel singleStockPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Panel para cada stock

                JTextField productNameField = new JTextField(stock.getProductName());
                JTextField categoryField = new JTextField(stock.getCategory());
                JTextField quantityField = new JTextField(String.valueOf(stock.getQuantity()));
                JTextField expiryDateField = new JTextField(stock.getExpiryDate().toString());

                singleStockPanel.add(new JLabel("Product Name:"));
                singleStockPanel.add(productNameField);
                singleStockPanel.add(new JLabel("Category:"));
                singleStockPanel.add(categoryField);
                singleStockPanel.add(new JLabel("Quantity:"));
                singleStockPanel.add(quantityField);
                singleStockPanel.add(new JLabel("Expiry Date:"));
                singleStockPanel.add(expiryDateField);

                JButton updateBtn = new JButton("Update");
                updateBtn.addActionListener(e -> {
                    try {
                        String newProductName = productNameField.getText();
                        String newCategory = categoryField.getText();
                        int newQuantity = Integer.parseInt(quantityField.getText());
                        String newExpiryDate = expiryDateField.getText();

                        java.sql.Date sqlExpiryDate = java.sql.Date.valueOf(newExpiryDate);

                        Stock updatedStock = new Stock(stock.getProductID(), newProductName, newCategory, newQuantity, sqlExpiryDate);
                        stockManager.updateStock(updatedStock); // Llamar al método de actualización con el nuevo stock
                        JOptionPane.showMessageDialog(this, "Stock updated successfully.");
                        updateStockPanel(); // Refrescar el panel de stock después de la actualización
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Please enter valid values.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "An error occurred while updating the stock.");
                        ex.printStackTrace();
                    }
                });

                singleStockPanel.add(new JLabel()); // Añadir espacio en blanco
                singleStockPanel.add(updateBtn);

                updatePanel.add(singleStockPanel);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No stocks found for this manager.");
        }
        currentPanel = updatePanel;
        showCurrentPanel();
    }
    
    
    private void addStockPanel() {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(0, 2, 10, 10));

        JTextField productNameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField expiryDateField = new JTextField("yyyy-MM-dd");

        addPanel.add(new JLabel("Product Name:"));
        addPanel.add(productNameField);
        addPanel.add(new JLabel("Category:"));
        addPanel.add(categoryField);
        addPanel.add(new JLabel("Quantity:"));
        addPanel.add(quantityField);
        addPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):"));
        addPanel.add(expiryDateField);

        JButton addBtn = new JButton("Add Stock");
        addBtn.addActionListener(e -> {
            try {
                String productName = productNameField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String expiryDateStr = expiryDateField.getText();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date expiryDate = dateFormat.parse(expiryDateStr);

                // Llamar al método para agregar el nuevo stock
                stockManager.addStock(managerId, productName, category, quantity, expiryDate);

                JOptionPane.showMessageDialog(this, "Stock added successfully.");

                // Refrescar el panel de stocks después de la adición
                updateStockPanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid values.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the stock.");
                ex.printStackTrace();
            }
        });

        addPanel.add(new JLabel()); // Añadir espacio en blanco
        addPanel.add(addBtn);

        currentPanel = addPanel;
        showCurrentPanel();
    }



    
    
}