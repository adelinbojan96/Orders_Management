package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;
import Presentation.Controller;

import javax.swing.*;

public class ProductBLL {
    ProductDAO productDAO;
    Controller controller;
    public ProductBLL(Controller controller)
    {
        this.productDAO = new ProductDAO();
        this.controller = controller;
    }
    public void addProduct(int id, String productName, String description, float price, String category, int quantity)
    {
        Product newProduct = new Product(id, productName, description, price, category, quantity);
        boolean unique = productDAO.checkUniqueness(newProduct.id());
        if(unique)
            productDAO.addProduct(newProduct);
        else
            JOptionPane.showMessageDialog(null, "The id is not unique, please enter something else");
    }
    public void editProduct(int firstId, int id, String productName, String description, float price, String category, int quantity)
    {
        Product newProduct = new Product(id, productName, description, price, category, quantity);
        boolean unique = productDAO.checkUniqueness(firstId);
        if(!unique)
            productDAO.editProduct(firstId, newProduct);
        else
            JOptionPane.showMessageDialog(null, "The product with the id " + firstId + " does not exist in the database");
    }
    public void deleteProduct(int firstId)
    {
        boolean unique = productDAO.checkUniqueness(firstId);
        if(!unique)
            productDAO.deleteProduct(firstId);
        else
            JOptionPane.showMessageDialog(null, "The product with the id " + firstId + " does not exist in the database");
    }
}
