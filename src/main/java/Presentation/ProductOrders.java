package Presentation;

import BusinessLogic.OrderBLL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProductOrders extends JDialog{
    private JPanel mainPanel;
    private JTextArea clientIdTextArea;
    private JLabel imageOrder;
    private JTextArea productIdTextArea;
    private JTextArea quantityTextArea;
    private JButton orderTheProductButton;
    private JLabel accessLog;
    private static boolean canAccessMain;
    public ProductOrders(Controller controller)
    {
        canAccessMain = true;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(660, 470);
        setTitle("Choose the desired operation");

        ImageIcon iconFirst = new ImageIcon("src/pictures/shake-hands.png");
        this.imageOrder.setIcon(iconFirst);

        mainPanel.setBackground(Color.decode("#D2E3FC")); //might change later
        setContentPane(mainPanel);

        customizeButton(orderTheProductButton);
        controller.addOrder(orderTheProductButton, clientIdTextArea, productIdTextArea, quantityTextArea);

        logClicked();

        setModal(true);
        setVisible(true);
        if(canAccessMain)
            addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                new MainView();
                }
        });
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
    private void logClicked(){
        accessLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    canAccessMain = false;
                    dispose();
                    new Controller("Log");
                } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
