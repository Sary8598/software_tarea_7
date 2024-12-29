package com.uapa.tareas.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.uapa.tareas.models.Product;
import com.uapa.tareas.service.ProductService;
import com.uapa.tareas.utils.GenerateReport;

public class ProductView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
    private JTextField descriptionField;
    private JTextField stockField;
    private JButton submitButton;
    private JButton reportButton;
    private ProductService productService = new ProductService();
    
    
    public ProductView() {
    	// Set JFrame properties
        setTitle("Product Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add form components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);

        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(stockLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(stockField, gbc);

        // Add submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this::handleSubmit);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);
        
        reportButton = new JButton("Report");
        reportButton.addActionListener(this::handleReport);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(reportButton, gbc);

        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        pack(); // Adjust size to fit components
    }
    
    public void handleSubmit(ActionEvent e) {
    	String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String stock = stockField.getText().trim();
        
        if (name.isEmpty() || description.isEmpty() || stock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setStock(Integer.parseInt(stock));
        
        productService.saveProduct(product); 
        
    }
    
    public void handleReport(ActionEvent e) {
    	new GenerateReport().generateReport();
    }
}
