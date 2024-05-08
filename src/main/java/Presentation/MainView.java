package Presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainView extends JDialog{
    private JPanel mainPanel;
    private JButton performClientOperationButton;
    private JButton performProductOperationButton;
    private JLabel imageFirst;
    private JLabel imageSecond;
    private JLabel imageThird;
    private JButton performOrderOperationButton;

    public MainView()
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(1000, 720);
        setTitle("Choose the desired operation");

        addImages();

        mainPanel.setBackground(Color.decode("#D2E3FC")); //might change later
        setContentPane(mainPanel);

        customizeButton(performClientOperationButton);
        customizeButton(performProductOperationButton);
        customizeButton(performOrderOperationButton);

        performClientOperationButton.addActionListener(e -> {
            dispose();
            new Controller(MainView.this, "Client");
        });
        performProductOperationButton.addActionListener(e -> {
            dispose();
            new Controller(MainView.this, "Product");
        });

        setModal(true);
        setVisible(true);
    }
    public static void main(String[] args) {
        new MainView();
    }
    private void customizeButton(JButton button)
    {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 2, true),
                new EmptyBorder(5, 20, 5, 20)
        ));
    }
    private void addImages()
    {
        ImageIcon iconFirst = new ImageIcon("src/pictures/products.png");
        this.imageFirst.setIcon(iconFirst);
        ImageIcon iconSecond = new ImageIcon("src/pictures/customers.png");
        this.imageSecond.setIcon(iconSecond);
        ImageIcon iconThird = new ImageIcon("src/pictures/orders.png");
        this.imageThird.setIcon(iconThird);
    }
}

