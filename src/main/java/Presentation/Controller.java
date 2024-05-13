package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.LogBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    JButton viewButton;
    private final DefaultTableModel tableModel;
    private JTable elementsTable = null;
    private final ArrayList<Object[]> objectsInTable;
    public Controller(String choice) throws SQLException, NoSuchFieldException, IllegalAccessException {
        this.tableModel = new DefaultTableModel();
        this.objectsInTable = new ArrayList<>();
        switch (choice) {
            case "Client" -> showClientOperations();
            case "Product" -> showProductOperations();
            case "Log" -> showLogView();
        }
    }
    private void showClientOperations() throws SQLException {
        ClientBLL clientBLL = new ClientBLL(this);

        JFrame clientFrame = new JFrame("Client Operations");
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setSize(600, 400);
        clientFrame.setLayout(new BorderLayout());

        //creating a table for displaying the clients with basic information
        JScrollPane clientScrollPane = getScrollPane();
        clientFrame.add(clientScrollPane, BorderLayout.CENTER);

        //adding buttons
        JPanel clientButtonPanel = getButtons("Client");

        addButton.addActionListener(e -> addClient(clientBLL));
        editButton.addActionListener(e->{
            int idClicked = elementsTable.getSelectedRow();
            editClient(idClicked, clientBLL);
        });
        deleteButton.addActionListener(e->{
            int idClicked = elementsTable.getSelectedRow();
            deleteClient(idClicked, clientBLL);
        });
        viewButton.addActionListener(e -> clientBLL.viewClients());
        clientFrame.add(clientButtonPanel, BorderLayout.SOUTH);
        clientFrame.setVisible(true);

        clientFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                new MainView();
            }
        });
    }
    private void showProductOperations() throws SQLException {
        ProductBLL productBLL = new ProductBLL(this);

        JFrame productFrame = new JFrame("Product Operations");
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setSize(600, 400);
        productFrame.setLayout(new BorderLayout());

        JScrollPane productScrollPane = getScrollPane();
        productFrame.add(productScrollPane, BorderLayout.CENTER);

        JPanel productButtonPanel = getButtons("Product");

        addButton.addActionListener(e -> addProduct(productBLL));
        editButton.addActionListener(e->{
            int idClicked = elementsTable.getSelectedRow();
            editProduct(idClicked, productBLL);
        });
        deleteButton.addActionListener(e->{
            int idClicked = elementsTable.getSelectedRow();
            deleteProduct(idClicked, productBLL);
        });
        viewButton.addActionListener(e -> productBLL.viewProducts());
        productFrame.add(productButtonPanel, BorderLayout.SOUTH);
        productFrame.setVisible(true);

        productFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                new MainView();
            }
        });
    }
    void showLogView() throws SQLException, NoSuchFieldException, IllegalAccessException {
        LogBLL logBLL = new LogBLL(this);

        JFrame logFrame = new JFrame("Log Operations");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setSize(600, 400);
        logFrame.setLayout(new BorderLayout());

        JScrollPane logScrollPane = getScrollPane();
        logFrame.add(logScrollPane, BorderLayout.CENTER);

        logBLL.viewLog();
        logFrame.setVisible(true);

        logFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                new ProductOrders(Controller.this);
            }
        });
    }
    private JScrollPane getScrollPane()
    {
        this.elementsTable = new JTable(tableModel);
        return new JScrollPane(elementsTable);
    }
    private JPanel getButtons(String name)
    {
        JPanel buttonPanel = new JPanel();
        this.addButton = new JButton("Add New " + name);
        this.editButton = new JButton("Edit " + name);
        this.deleteButton = new JButton("Delete " + name);
        this.viewButton = new JButton("View All " + name + "s");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        return buttonPanel;
    }
    private Object[] showInputDialogClient() {
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField ageField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        boolean validity = clientValidity(idField.getText(), nameField.getText(), emailField.getText(), ageField.getText());
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Client Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && validity) {
            return new Object[]{Integer.parseInt(idField.getText()), nameField.getText(), emailField.getText(), Integer.parseInt(ageField.getText())};
        }
        JOptionPane.showMessageDialog(null, "Please complete all fields.");
        return null;
    }
    private Object[] showInputDialogProduct() {
        JTextField idField = new JTextField(5);
        JTextField productNameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField priceField = new JTextField(5);
        JTextField categoryField = new JTextField(21);
        JTextField quantityField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        boolean validity = productValidity(idField.getText(), productNameField.getText(), descriptionField.getText(), priceField.getText(), categoryField.getText(), quantityField.getText());
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Product Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && validity) {
            return new Object[]{Integer.parseInt(idField.getText()), productNameField.getText(), descriptionField.getText(),
                    Float.parseFloat(priceField.getText()), categoryField.getText(), Integer.parseInt(quantityField.getText())};
        }
        JOptionPane.showMessageDialog(null, "Please complete all fields.");
        return null;
    }
    private void addClient(ClientBLL clientBLL)
    {
        Object[] inputData = showInputDialogClient();
        if (inputData != null) {
            int id = (int) inputData[0];
            String name = (String) inputData[1];
            String email = (String) inputData[2];
            int age = (int) inputData[3];

            clientBLL.addClient(id, name, email, age);
        }
    }
    private void editClient(int firstId, ClientBLL clientBLL)
    {
        Object[] inputData = showInputDialogClient();
        if (inputData != null && firstId != -1) {
            int id = (int) inputData[0];
            String name = (String) inputData[1];
            String email = (String) inputData[2];
            int age = (int) inputData[3];

            clientBLL.editClient((Integer) objectsInTable.get(firstId)[0], firstId, id, name, email, age);
        }
        else if(inputData != null)
            JOptionPane.showMessageDialog(null, "Please select an item before editing something.");
    }
    private void deleteClient(int firstId, ClientBLL clientBLL)
    {
        if(firstId != -1)
            clientBLL.deleteClient((int) objectsInTable.get(firstId)[0], firstId);
        else
            JOptionPane.showMessageDialog(null, "Please select an item before deleting something.");
    }
    private void addProduct(ProductBLL productBLL)
    {
        Object[] inputData = showInputDialogProduct();
        if (inputData != null) {
            int id = (int) inputData[0];
            String productName = (String) inputData[1];
            String description = (String) inputData[2];
            float price = (float) inputData[3];
            String category = (String) inputData[4];
            int quantity = (int) inputData[5];

            productBLL.addProduct(id, productName, description, price, category, quantity);
        }
    }
    private void editProduct(int firstId, ProductBLL productBLL)
    {
        Object[] inputData = showInputDialogProduct();
        if (inputData != null && firstId != -1) {
            var id = (int) inputData[0];
            String productName = (String) inputData[1];
            String description = (String) inputData[2];
            float price = (float) inputData[3];
            String category = (String) inputData[4];
            int quantity = (int) inputData[5];

            productBLL.editProduct((Integer) objectsInTable.get(firstId)[0], firstId, id, productName, description, price, category, quantity);
        }
        else if(inputData != null)
            JOptionPane.showMessageDialog(null, "Please select an item before editing something.");
    }
    private void deleteProduct(int firstId, ProductBLL productBLL)
    {
        if(firstId != -1)
            productBLL.deleteProduct((int) objectsInTable.get(firstId)[0], firstId);
        else
            JOptionPane.showMessageDialog(null, "Please select an item before deleting something.");
    }
    public void addOrder(JButton orderTheProductButton, JTextArea clientIdTextArea, JTextArea productIdTextArea, JTextArea quantityTextArea) {
        orderTheProductButton.addActionListener(e -> {
            try {
                int clientId = Integer.parseInt(clientIdTextArea.getText());
                int productId = Integer.parseInt(productIdTextArea.getText());
                int quantity = Integer.parseInt(quantityTextArea.getText());

                new OrderBLL(Controller.this).addOrder(clientId, productId, quantity);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid integers for Client ID, Product ID, and Quantity.");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private boolean clientValidity(String id, String name, String email, String age)
    {
        return !Objects.equals(id, null) && !Objects.equals(name, null) && !Objects.equals(email, null) && !Objects.equals(age, null);
    }
    private boolean productValidity(String id, String productName, String description, String price, String category, String quantity)
    {
        return !Objects.equals(id, null) && !Objects.equals(productName, null) && !Objects.equals(description, null) && !Objects.equals(price, null)
                && !Objects.equals(category, null) && !Objects.equals(quantity, null);
    }
}