package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectionFactory;
import Model.Client;

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
            statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, client.id());
            statement.setString(2, client.name());
            statement.setString(3, client.email());
            statement.setInt(4, client.age());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new client was inserted successfully!");
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
            String updateQuery = "UPDATE client SET name = ?, email = ?, age = ? WHERE id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newClient.name());
            updateStatement.setString(2, newClient.email());
            updateStatement.setInt(3, newClient.age());
            updateStatement.setInt(4, id);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client with ID " + id + " was updated successfully!");
            } else {
                System.out.println("No client found with ID " + id + ". No update performed.");
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
                System.out.println("Client with ID " + id + " was deleted successfully!");
            } else
                System.out.println("No client found with ID " + id + ". No deletion performed.");
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

