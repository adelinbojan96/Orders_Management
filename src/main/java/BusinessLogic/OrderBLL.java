package BusinessLogic;

import DataAccess.ClientDAO;
import DataAccess.ProductDAO;
import Presentation.Controller;

import java.sql.SQLException;

public class OrderBLL extends BaseBLL{

    public OrderBLL(Controller controller) {
        super(controller);
    }
    public void addOrder(int idClient, int idProduct, int quantity) throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();
        boolean clientExist = !clientDAO.checkUniqueness(idClient);
        boolean productExist = !productDAO.checkUniqueness(idProduct);
        if(quantity > 0 && clientExist && productExist)
        {
            //check if current quantity is lower than what product has

        }
    }
}
