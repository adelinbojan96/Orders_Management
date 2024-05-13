package BusinessLogic;

import DataAccess.ProductDAO;
import Model.ObjectModel;
import Model.Product;
import Presentation.Controller;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class for performing the logic of creating a product and insert it in database
 */
public class ProductBLL extends BaseBLL{
    ProductDAO productDAO;
    Controller controller;
    public ProductBLL(Controller controller) throws SQLException {
        super(controller);
        this.controller = controller;
        this.productDAO = new ProductDAO();
    }

    /**
     * Add a product to database
     * @param id id of the product
     * @param productName name of the product
     * @param description description of the product
     * @param price price of the product
     * @param category category of the product
     * @param quantity quantity of the product
     */
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
    /**
     * edits an existing product in the database.
     * @param firstIdDB    id of the product in the database.
     * @param firstIdTable id of the product in the table.
     * @param id           new ID of the product.
     * @param productName  new name of the product.
     * @param description  new description of the product.
     * @param price        new price of the product.
     * @param category     new category of the product.
     * @param quantity     new quantity of the product.
     */
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
    /**
     * deletes a product from the database.
     * @param firstIdDB    id of the product in the database.
     * @param firstIdTable id of the product in the table.
     */
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

    /**
     * View the products in the table by retrieving everything from the database
     */

    public void viewProducts()
    {
        try {
            ProductDAO productDAO = new ProductDAO();
            ArrayList<Object[]> items = productDAO.getAllProducts();
            Object[] firstItem = items.getFirst();

            updateWithAllElementsFromDB(new Product((Integer) firstItem[0], (String) firstItem[1],
                    (String) firstItem[2], (float) firstItem[3], (String) firstItem[4], (int) firstItem[5]), items);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
