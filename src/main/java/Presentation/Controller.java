package Presentation;

import BusinessLogic.ClientBLL;
import DataAccess.ClientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller {
    MainView mainFrame;
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JButton viewButton;
    DefaultTableModel clientTableModel;
    public Controller(MainView mainFrame, String choice)
    {
        this.mainFrame = mainFrame;
        this.clientTableModel = new DefaultTableModel();
        if(choice.equals("Client"))
            showClientOperations();
        else if(choice.equals("Product"))
            showProductOperations();
    }
    private void showClientOperations()
    {
        ClientBLL clientBLL = new ClientBLL(this);

        JFrame clientFrame = new JFrame("Client Operations");
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setSize(600, 400);
        clientFrame.setLayout(new BorderLayout());

        //creating a table for displaying the clients with basic information
        JScrollPane clientScrollPane = getScrollPaneClient();
        clientFrame.add(clientScrollPane, BorderLayout.CENTER);

        //adding buttons for client operations
        JPanel clientButtonPanel = getButtons("Client");

        addButton.addActionListener(e ->{
            Object[] inputData = showInputDialog();
            if (inputData != null) {
                int id = (int) inputData[0];
                String name = (String) inputData[1];
                String email = (String) inputData[2];
                int age = (int) inputData[3];

                clientBLL.addClient(id, name, email, age);
                clientTableModel.addRow(inputData);
            }
        });

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
    private void showProductOperations()
    {
        JFrame productFrame = new JFrame("Product Operations");
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setSize(600, 400);
        productFrame.setLayout(new BorderLayout());

        JScrollPane productScrollPane = getScrollPaneProduct();
        productFrame.add(productScrollPane, BorderLayout.CENTER);

        JPanel productButtonPanel = getButtons("Product");

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
    private JScrollPane getScrollPaneClient()
    {
        JTable clientTable = new JTable(clientTableModel);
        clientTableModel.addColumn("ID");
        clientTableModel.addColumn("Name");
        clientTableModel.addColumn("e-mail");
        clientTableModel.addColumn("Age");

        return new JScrollPane(clientTable);
    }
    private JScrollPane getScrollPaneProduct()
    {
        DefaultTableModel productTableModel = new DefaultTableModel();
        JTable productTable = new JTable(productTableModel);
        productTableModel.addColumn("ID");
        productTableModel.addColumn("Product name");
        productTableModel.addColumn("Description");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Quantity");

        return new JScrollPane(productTable);
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
    public Object[] showInputDialog() {
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

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Client Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new Object[]{Integer.parseInt(idField.getText()), nameField.getText(), emailField.getText(), Integer.parseInt(ageField.getText())};
        }
        return null;
    }
}
