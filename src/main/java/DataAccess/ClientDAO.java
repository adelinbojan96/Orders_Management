package DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Client;

import javax.swing.*;

/**
 * Data Access Object for handling logging operations.
 */
public class ClientDAO extends AbstractDAO<Client>{
    public ClientDAO() throws SQLException {
        super(new ConnectionFactory().getConnection());
    }

    /**
     * Used findById to check uniqueness
     * @param id id of the element
     * @return true of false based on the existence or nonexistence
     */
    public boolean checkUniqueness(int id)
    {
        if(id > 0)
            return findById(id) == null;
        else
            return false;
    }
    /**
     * Adds a new client to the database.
     * @param client the client to be added.
     */
    public void addClient(Client client)
    {
        if(client!= null)
            insert(client);
        else
            JOptionPane.showMessageDialog(null, "Client is null");
    }
    /**
     * Edit this client
     * @param id id of the current client
     * @param newClient the edited client
     */
    public void editClient(int id, Client newClient)
    {
        if(newClient!=null)
            edit(newClient,id);
        else
            JOptionPane.showMessageDialog(null, "Client is null");
    }

    /**
     * deletes a client based on id
     * @param id id of the client from database
     */
    public void deleteClient(int id){ delete(id); }

    /**
     * retrieves clients from database
     * @return returns an array of objects
     */
    public ArrayList<Object[]> getAllClients() {
        return getAll();
    }

}