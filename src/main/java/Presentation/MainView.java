package Presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainView extends JDialog{
    private JPanel mainPanel;
    private JButton performClientOperationButton;
    private JButton performProductOperationButton;
    private JLabel imageFirst;
    private JLabel imageSecond;

    public MainView()
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(960, 540);
        setTitle("Choose the desired operation");

        ImageIcon iconFirst = new ImageIcon("src/pictures/products.png");
        imageFirst.setIcon(iconFirst);
        ImageIcon iconSecond = new ImageIcon("src/pictures/customers.png");
        imageSecond.setIcon(iconSecond);

        mainPanel.setBackground(Color.decode("#D2E3FC")); //might change later
        setContentPane(mainPanel);

        customizeButton(performClientOperationButton);
        customizeButton(performProductOperationButton);

        performClientOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: create new JTable
                showClientOperations();
            }
        });
        performProductOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: create a new JTable
                showProductOperations();
            }
        });

        setModal(true);
        setVisible(true);
    }
    private void showClientOperations() {
        dispose();

        JFrame clientFrame = new JFrame("Client Operations");
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setSize(600, 400);
        clientFrame.setLayout(new BorderLayout());

        //creating a table for displaying the clients with basic information
        DefaultTableModel clientTableModel = new DefaultTableModel();
        JTable clientTable = new JTable(clientTableModel);
        clientTableModel.addColumn("ID");
        clientTableModel.addColumn("Name");
        clientTableModel.addColumn("Email");
        clientTableModel.addColumn("Location");

        //adding some sample data for testing UI
        clientTableModel.addRow(new Object[]{"John Deer", "john@example.com", "New York"});
        clientTableModel.addRow(new Object[]{"Jane Deer", "jane@example.com", "Los Angeles"});

        JScrollPane clientScrollPane = new JScrollPane(clientTable);
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
        dispose();

        JFrame productFrame = new JFrame("Product Operations");
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setSize(600, 400);
        productFrame.setLayout(new BorderLayout());

        //creating a table for displaying the clients with basic information
        DefaultTableModel productTableModel = new DefaultTableModel();
        JTable productTable = new JTable(productTableModel);
        productTableModel.addColumn("ID");
        productTableModel.addColumn("Product name");
        productTableModel.addColumn("Description");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Category");

        //adding some sample data for testing UI
        productTableModel.addRow(new Object[]{"John Doe", "john@example.com", "New York"});
        productTableModel.addRow(new Object[]{"Jane Smith", "jane@example.com", "Los Angeles"});

        JScrollPane clientScrollPane = new JScrollPane(productTable);
        productFrame.add(clientScrollPane, BorderLayout.CENTER);

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

    private static JPanel getjPanel(String name) {
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

    public static void main(String[] args) {
        new MainView();
    }
    private static void customizeButton(JButton button)
    {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 2, true),
                new EmptyBorder(5, 20, 5, 20)
        ));
    }
}

