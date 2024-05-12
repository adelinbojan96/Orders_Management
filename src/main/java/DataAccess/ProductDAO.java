package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Product;

import javax.swing.*;

public class ProductDAO  extends AbstractDAO<Product>{
    private final Connection connection;
    public ProductDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());
        this.connection = new ConnectionFactory().getConnection();
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
        ArrayList<Object[]> products = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM product";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                String email = resultSet.getString("description");
                float price = resultSet.getFloat("price");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");

                Object[] productData = {id, productName, email, price, category, quantity};
                products.add(productData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            closeResources(resultSet, statement);
        }
        return products;
    }

}
