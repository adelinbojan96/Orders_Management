package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;
import Presentation.Controller;

import javax.swing.*;

public class ClientBLL {
    ClientDAO clientDAO;
    Controller controller;
    public ClientBLL(Controller controller)
    {
        this.clientDAO = new ClientDAO();
        this.controller = controller;
    }
    public void addClient(int id, String name, String email, int age)
    {
        Client newClient = new Client(id, name, email, age);
        //first of all we need to check uniqueness
        boolean unique = clientDAO.checkUniqueness(newClient.id());
        if(unique)
            clientDAO.addClient(newClient);
        else
            JOptionPane.showMessageDialog(null, "The id is not unique, please enter something else");
    }
    public void editClient(int firstId, int id, String name, String email, int age)
    {
        Client newClient = new Client(id, name, email, age);
        boolean unique = clientDAO.checkUniqueness(firstId);
        if(!unique)
            clientDAO.editClient(firstId, newClient);
        else
            JOptionPane.showMessageDialog(null, "The user with the id " + firstId + " does not exist in the database");
    }
    public void deleteClient(int firstId)
    {
        boolean unique = clientDAO.checkUniqueness(firstId);
        if(!unique)
            clientDAO.deleteClient(firstId);
        else
            JOptionPane.showMessageDialog(null, "The user with the id " + firstId + " does not exist in the database");
    }
}
