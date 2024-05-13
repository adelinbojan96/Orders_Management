package DataAccess;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Orderr;

import javax.swing.*;

public class OrderDAO extends AbstractDAO<Orderr>{
    public OrderDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());

    }
    public void addOrder(Orderr orderr)
    {
        if(orderr != null)
            insert(orderr);
        else
            JOptionPane.showMessageDialog(null, "Order is null");
    }
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
