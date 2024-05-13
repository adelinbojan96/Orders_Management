package DataAccess;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Orderr;

import javax.swing.*;
/**
 * Data Access Object for handling Order operations.
 */
public class OrderDAO extends AbstractDAO<Orderr>{
    public OrderDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());

    }
    /**
     * Adds an order to the database.
     * @param orderr the order object to be added
     */
    public void addOrder(Orderr orderr)
    {
        if(orderr != null)
            insert(orderr);
        else
            JOptionPane.showMessageDialog(null, "Order is null");
    }

    /**
     * Retrieves a valid ID for a new order.
     * @return a valid ID for a new order
     * @throws SQLException if a database access error occurs
     */
    public int getValidId() throws SQLException {
        Connection connection = new ConnectionFactory().getConnection();
        int maxId = 0;
        try {
            String query = "SELECT MAX(id) AS max_id FROM Orderr";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }
            return maxId + 1;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL exception when creating a new id for Order");
        }
        return 1;
    }
}
