package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller {
    MainView mainFrame;
    public Controller(MainView mainFrame, String choice)
    {
        this.mainFrame = mainFrame;
        if(choice.equals("Client"))
            showClientOperations();
        else
            showProductOperations();
    }
    private void showClientOperations()
    {
        JFrame clientFrame = new JFrame("Client Operations");
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setSize(600, 400);
        clientFrame.setLayout(new BorderLayout());

        //creating a table for displaying the clients with basic information
        JScrollPane clientScrollPane = getScrollPaneClient();
        clientFrame.add(clientScrollPane, BorderLayout.CENTER);

        //adding buttons for client operations
        JPanel clientButtonPanel = getjPanel("Client");

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

        //creating a table for displaying the clients with basic information
        JScrollPane productScrollPane = getScrollPaneProduct();
        productFrame.add(productScrollPane, BorderLayout.CENTER);

        //adding buttons for client operations
        JPanel productButtonPanel = getjPanel("Product");

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
    private static JScrollPane getScrollPaneClient()
    {
        DefaultTableModel clientTableModel = new DefaultTableModel();
        JTable clientTable = new JTable(clientTableModel);
        clientTableModel.addColumn("ID");
        clientTableModel.addColumn("Name");
        clientTableModel.addColumn("e-mail");
        clientTableModel.addColumn("Location");

        return new JScrollPane(clientTable);
    }
    private static JScrollPane getScrollPaneProduct()
    {
        DefaultTableModel productTableModel = new DefaultTableModel();
        JTable productTable = new JTable(productTableModel);
        productTableModel.addColumn("ID");
        productTableModel.addColumn("Product name");
        productTableModel.addColumn("Description");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Category");

        return new JScrollPane(productTable);
    }

    private static JPanel getjPanel(String name)
    {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add New " + name);
        JButton editButton = new JButton("Edit " + name);
        JButton deleteButton = new JButton("Delete " + name);
        JButton viewButton = new JButton("View All " + name + "s");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        return buttonPanel;
    }
}
