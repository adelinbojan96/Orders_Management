package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Client;

import javax.swing.*;

public class ClientDAO extends AbstractDAO<Client>{
    private final Connection connection;
    public ClientDAO() throws SQLException {
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
    public void addClient(Client client)
    {
        if(client!= null)
            insert(client);
        else
            JOptionPane.showMessageDialog(null, "Client is null");
    }
    public void editClient(int id, Client newClient)
    {
        if(newClient!=null)
            edit(newClient,id);
        else
            JOptionPane.showMessageDialog(null, "Client is null");
    }
    public void deleteClient(int id){ delete(id); }
    public ArrayList<Object[]> getAllClients() {
        /*
        ArrayList<Object[]> clients = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM client";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");

                Object[] clientData = {id, name, email, age};
                clients.add(clientData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            closeResources(resultSet, statement);
        }
        return clients;
         */
        return getAll();
    }

}