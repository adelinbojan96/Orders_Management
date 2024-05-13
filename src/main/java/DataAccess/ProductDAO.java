package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Product;

import javax.swing.*;

public class ProductDAO  extends AbstractDAO<Product>{
    public ProductDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());
    }
    public boolean checkUniqueness(int id)
    {
        if(id > 0)
            return findById(id) == null;
        else
            return false;
    }
    public void addProduct(Product product)
    {
        if(product!= null)
            insert(product);
        else
            JOptionPane.showMessageDialog(null, "Product is null");
    }
    public void editProduct(int id, Product newProduct)
    {
        if(newProduct!=null)
            edit(newProduct,id);
        else
            JOptionPane.showMessageDialog(null, "Product is null");
    }
    public void deleteProduct(int id) { delete(id);}
    public ArrayList<Object[]> getAllProducts() {
        return getAll();
    }
}
