package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Client;

import javax.swing.*;

public class ClientDAO extends AbstractDAO<Client>{
    public ClientDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());
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
        return getAll();
    }

}