package Presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;
/**
 * MainView is the main frame in which the user can choose the next operations
 */
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
        setSize(1000, 700);
        setTitle("Choose the desired operation");

        addImages();

        mainPanel.setBackground(Color.decode("#D2E3FC")); //might change later
        setContentPane(mainPanel);

        customizeButton(performClientOperationButton);
        customizeButton(performProductOperationButton);
        customizeButton(performOrderOperationButton);

        clickClient();
        clickProduct();
        clickOrder();

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
    /**
     * function for adding visually images from a certain folder
     */
    private void addImages()
    {
        ImageIcon iconFirst = new ImageIcon("src/pictures/products.png");
        this.imageFirst.setIcon(iconFirst);
        ImageIcon iconSecond = new ImageIcon("src/pictures/customers.png");
        this.imageSecond.setIcon(iconSecond);
        ImageIcon iconThird = new ImageIcon("src/pictures/orders.png");
        this.imageThird.setIcon(iconThird);
    }
    /**
     * action listener for button clicked (client)
     */
    private void clickClient()
    {
        performClientOperationButton.addActionListener(e -> {
            dispose();
            try {
                new Controller("Client");
            } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * action listener for button clicked (product)
     */
    private void clickProduct()
    {
        performProductOperationButton.addActionListener(e -> {
            dispose();
            try {
                new Controller("Product");
            } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * action listener for button clicked (order)
     */
    private void clickOrder()
    {
        performOrderOperationButton.addActionListener(e ->
        {
            dispose();
            try {
                new ProductOrders(new Controller("Orderr"));
            } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

