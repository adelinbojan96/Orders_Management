package BusinessLogic;

import DataAccess.ProductDAO;
import Model.ObjectModel;
import Model.Product;
import Presentation.Controller;

import javax.swing.*;
import java.sql.SQLException;

public class ProductBLL extends BaseBLL{
    ProductDAO productDAO;
    Controller controller;
    public ProductBLL(Controller controller) throws SQLException {
        super(controller);
        this.controller = controller;
        this.productDAO = new ProductDAO();
    }
    public void addProduct(int id, String productName, String description, float price, String category, int quantity)
    {
        Product newProduct = new Product(id, productName, description, price, category, quantity);
        boolean unique = productDAO.checkUniqueness(newProduct.id());
        if(unique)
        {
            productDAO.addProduct(newProduct);
            Object[] inputData = new Object[]{id, productName, description, price, category, quantity, null};
            try {
                updateTable(newProduct, inputData, "Add");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to add product");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The id is not unique, please enter something else");
    }
    public void editProduct(int firstIdDB, int firstIdTable, int id, String productName, String description, float price, String category, int quantity)
    {
        Product newProduct = new Product(id, productName, description, price, category, quantity);
        boolean unique = productDAO.checkUniqueness(firstIdDB);
        if(!unique)
        {
            productDAO.editProduct(firstIdDB, newProduct);
            Object[] inputData = new Object[]{id, productName, description, price, category, quantity, firstIdTable};
            try {
                updateTable(newProduct, inputData, "Edit");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to edit product");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The product with the id " + firstIdDB + " does not exist in the database");
    }
    public void deleteProduct(int firstIdDB, int firstIdTable)
    {
        boolean unique = productDAO.checkUniqueness(firstIdDB);
        if(!unique)
        {
            productDAO.deleteProduct(firstIdDB);
            Object[] inputData = new Object[]{firstIdTable};
            ObjectModel nullObjectModel = new ObjectModel() {};
            try {
                updateTable(nullObjectModel, inputData, "Delete");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to delete product");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The product with the id " + firstIdDB + " does not exist in the database");
    }
    public void viewProducts()
    {
        try {
            updateWithAllElementsFromDB("Product");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
