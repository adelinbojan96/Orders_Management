package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Product;

import javax.swing.*;

public class ProductDAO {
    private Connection connection;
    public ProductDAO()
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connection = connectionFactory.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        }
    }
    public boolean checkUniqueness(int id)
    {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT COUNT(*) FROM product WHERE id_product = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
            }
        }
        return false;
    }
    public void addProduct(Product product)
    {
        PreparedStatement addStatement = null;
        try {
            String insertQuery = "INSERT INTO product (id_product, name, description, price, category, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            addStatement = updateProduct(insertQuery, product);

            int rowsInserted = addStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "A new product was inserted successfully!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            try {
                if (addStatement != null) addStatement.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
            }
        }
    }
    public void editProduct(int id, Product newProduct)
    {
        PreparedStatement updateStatement = null;
        try {
            String updateQuery = "UPDATE product SET id_product = ?, name = ?, description = ?, price = ?, category = ?, quantity = ? WHERE id_product = ?";
            updateStatement = updateProduct(updateQuery, newProduct);
            updateStatement.setInt(7, newProduct.id());

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Product with ID " + id + " was updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No product found with ID " + id + ". No update performed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            try {
                if (updateStatement != null) updateStatement.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
            }
        }
    }
    public void deleteProduct(int id)
    {
        PreparedStatement statement = null;
        try {
            String deleteQuery = "DELETE FROM product WHERE id_product = ?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Product with ID " + id + " was deleted successfully!");
            } else
                JOptionPane.showMessageDialog(null, "No product found with ID " + id + ". No deletion performed.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
            }
        }
    }
    private PreparedStatement updateProduct(String updateQuery, Product newProduct) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setInt(1, newProduct.id());
        updateStatement.setString(2, newProduct.productName());
        updateStatement.setString(3, newProduct.description());
        updateStatement.setFloat(4, newProduct.price());
        updateStatement.setString(5, newProduct.category());
        updateStatement.setInt(6, newProduct.quantity());
        return updateStatement;
    }

}
