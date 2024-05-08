package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Client;

import javax.swing.*;

public class ClientDAO {
    private Connection connection;
    public ClientDAO()
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
            String query = "SELECT COUNT(*) FROM client WHERE id = ?";
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
    public void addClient(Client client)
    {
        PreparedStatement statement = null;
        try {
            String insertQuery = "INSERT INTO client (id, name, email, age) VALUES (?, ?, ?, ?)";
            statement = updateClient(insertQuery, client);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "A new client was inserted successfully!");
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
    public void editClient(int id, Client newClient)
    {
        PreparedStatement updateStatement = null;
        try {
            String updateQuery = "UPDATE client SET id = ?, name = ?, email = ?, age = ? WHERE id = ?";
            updateStatement = updateClient(updateQuery, newClient);
            updateStatement.setInt(5, id);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Client with ID " + id + " was updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No client found with ID " + id + ". No update performed.");
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
    public void deleteClient(int id)
    {
        PreparedStatement statement = null;
        try {
            String deleteQuery = "DELETE FROM client WHERE id = ?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Client with ID " + id + " was deleted successfully!");
            } else
                JOptionPane.showMessageDialog(null, "No client found with ID " + id + ". No deletion performed.");
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
    private PreparedStatement updateClient(String updateQuery, Client newClient) throws SQLException {
        PreparedStatement updateStatement;
        updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setInt(1, newClient.id());
        updateStatement.setString(2, newClient.name());
        updateStatement.setString(3, newClient.email());
        updateStatement.setInt(4, newClient.age());

        return updateStatement;
    }
}

