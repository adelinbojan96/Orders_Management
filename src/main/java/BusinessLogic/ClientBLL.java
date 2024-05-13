package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;
import Model.ObjectModel;
import Presentation.Controller;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientBLL extends BaseBLL{
    ClientDAO clientDAO;
    Controller controller;
    public ClientBLL(Controller controller) throws SQLException {
        super(controller);
        this.controller = controller;
        this.clientDAO = new ClientDAO();
    }
    public void addClient(int id, String name, String email, int age){
        Client newClient = new Client(id, name, email, age);
        //first of all we need to check uniqueness
        boolean unique = clientDAO.checkUniqueness(newClient.id());
        if(unique)
        {
            clientDAO.addClient(newClient);
            Object[] inputData = new Object[]{id, name, email, age, null};
            try {
                updateTable(newClient, inputData, "Add");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to add client");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The id is not unique, please enter something else");
    }
    public void editClient(int firstIdDB, int firstIdTable, int id, String name, String email, int age)
    {
        Client newClient = new Client(id, name, email, age);
        boolean unique = clientDAO.checkUniqueness(firstIdDB);
        if(!unique)
        {
            clientDAO.editClient(firstIdDB, newClient);
            Object[] inputData = new Object[]{id, name, email, age, firstIdTable};
            try {
                updateTable(newClient, inputData,"Edit");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to edit client");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The user with the id " + firstIdDB + " does not exist in the database");
    }
    public void deleteClient(int firstIdDB, int firstIdTable)
    {
        boolean unique = clientDAO.checkUniqueness(firstIdDB);
        if(!unique)
        {
            clientDAO.deleteClient(firstIdDB);
            Object[] inputData = new Object[]{firstIdTable};
            ObjectModel nullObjectModel = new ObjectModel() {};
            try {
                updateTable(nullObjectModel, inputData, "Delete");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error trying to delete client");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "The user with the id " + firstIdDB + " does not exist in the database");
    }
    public void viewClients()
    {
        try {
            ArrayList<Object[]> items = clientDAO.getAllClients();
            Object[] firstItem = items.getFirst();

            updateWithAllElementsFromDB(new Client((Integer) firstItem[0], (String) firstItem[1],
                    (String) firstItem[2], (int) firstItem[3]), items);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
