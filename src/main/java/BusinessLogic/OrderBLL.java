package BusinessLogic;

import DataAccess.ClientDAO;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Bill;
import Model.Orderr;
import Model.Product;
import Presentation.Controller;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderBLL extends BaseBLL{
    private final OrderDAO orderDAO = new OrderDAO();
    private final ClientDAO clientDAO = new ClientDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final LogBLL logBLL = new LogBLL(controller);
    public OrderBLL(Controller controller) throws SQLException {
        super(controller);
    }
    public void addOrder(int idClient, int idProduct, int quantity) throws SQLException {

        boolean clientExist = clientDAO.findById(idClient) != null;
        boolean productExist = productDAO.findById(idProduct) != null;
        if(quantity > 0 && clientExist && productExist)
        {
            //check if current quantity is lower than what product has
            if(quantity < productDAO.findById(idProduct).quantity()){
                //perform update for product
                Product currentProduct = productDAO.findById(idProduct);
                int updatedQuantity = currentProduct.quantity() - quantity;
                productDAO.editProduct(idProduct, new Product(idProduct, currentProduct.name(), currentProduct.description(), currentProduct.price(), currentProduct.category(), updatedQuantity));

                addInLogTable(orderDAO.getValidId(), idClient, idProduct, quantity);
            }
            else
                JOptionPane.showMessageDialog(null, "The quantity demanded is greater than the quantity supplied.");
        }
        else
            JOptionPane.showMessageDialog(null, "Fields are not completed correctly. Please make sure the client and product exist and quantity is greater than 0");
    }
    private void addInLogTable(int id_order, int id_client, int id_product, int quantity) throws SQLException {
        Orderr orderr = new Orderr(id_order, id_client, id_product, quantity);
        orderDAO.addOrder(orderr);

        String clientName = clientDAO.findById(id_client).name();
        String productName = productDAO.findById(id_product).name();
        float totalPrice = quantity * productDAO.findById(id_product).price();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Bill newBillForLog = new Bill(id_order, clientName, productName, totalPrice,currentTime);

        logBLL.addToLog(newBillForLog);
    }
}
