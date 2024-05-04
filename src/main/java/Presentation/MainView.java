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
                dispose();
                new Controller(MainView.this, "Client");
            }
        });
        performProductOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Controller(MainView.this, "Product");
            }
        });

        setModal(true);
        setVisible(true);
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

