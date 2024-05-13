package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Product;

import javax.swing.*;

/**
 * Data Access Object for handling Product operations.
 */
public class ProductDAO  extends AbstractDAO<Product>{
    public ProductDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());
    }

    /**
     * Used findById to check uniqueness
     * @param id id of the element
     * @return true of false based on the existence or nonexistence
     */
    public boolean checkUniqueness(int id)
    {
        if(id > 0)
            return findById(id) == null;
        else
            return false;
    }

    /**
     * adds a product in database
     * @param product product type
     */
    public void addProduct(Product product)
    {
        if(product!= null)
            insert(product);
        else
            JOptionPane.showMessageDialog(null, "Product is null");
    }

    /**
     * edits a product in database
     * @param id id in database
     * @param newProduct newProduct which is going to be added in database
     */
    public void editProduct(int id, Product newProduct)
    {
        if(newProduct!=null)
            edit(newProduct,id);
        else
            JOptionPane.showMessageDialog(null, "Product is null");
    }

    /**
     * Deletes a product from the database based on id
     * @param id id
     */
    public void deleteProduct(int id) { delete(id);}

    /**
     * gets all products
     * @return an array or objects
     */
    public ArrayList<Object[]> getAllProducts() {
        return getAll();
    }
}
