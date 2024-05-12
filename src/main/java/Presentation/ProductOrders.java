package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProductOrders extends JDialog{
    private JPanel mainPanel;
    private JTextArea clientIdTextArea;
    private JLabel imageOrder;
    private JTextArea productIdTextArea;
    private JTextArea quantityTextArea;
    private JButton orderTheProductButton;

    public ProductOrders()
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(660, 440);
        setTitle("Choose the desired operation");

        ImageIcon iconFirst = new ImageIcon("src/pictures/shake-hands.png");
        this.imageOrder.setIcon(iconFirst);

        mainPanel.setBackground(Color.decode("#D2E3FC")); //might change later
        setContentPane(mainPanel);

        setModal(true);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                new MainView();
            }
        });
    }
}
