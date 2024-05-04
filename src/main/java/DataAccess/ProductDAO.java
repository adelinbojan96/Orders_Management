package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Product;

public class ProductDAO {
    private Connection connection;
    public ProductDAO()
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connection = connectionFactory.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkUniqueness(int id)
    {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT COUNT(*) FROM product WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public void addProduct(Product product)
    {
        PreparedStatement statement = null;
        try {
            String insertQuery = "INSERT INTO product (id_product, name, description, price, category) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, product.id());
            statement.setString(2, product.productName());
            statement.setString(3, product.description());
            statement.setFloat(4, product.price());
            statement.setString(5, product.category());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new product was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void editProduct(int id, Product newProduct)
    {
        PreparedStatement updateStatement = null;
        try {
            String updateQuery = "UPDATE product SET name = ?, description = ?, price = ?, category = ? WHERE id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newProduct.productName());
            updateStatement.setString(2, newProduct.description());
            updateStatement.setFloat(3, newProduct.price());
            updateStatement.setString(4, newProduct.category());
            updateStatement.setInt(5, newProduct.id());

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product with ID " + id + " was updated successfully!");
            } else {
                System.out.println("No product found with ID " + id + ". No update performed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (updateStatement != null) updateStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteProduct(int id)
    {
        PreparedStatement statement = null;
        try {
            String deleteQuery = "DELETE FROM product WHERE id = ?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product with ID " + id + " was deleted successfully!");
            } else
                System.out.println("No product found with ID " + id + ". No deletion performed.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
