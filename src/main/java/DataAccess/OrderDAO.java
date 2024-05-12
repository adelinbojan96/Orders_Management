package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Client;
import Model.Order;

import javax.swing.*;

public class OrderDAO extends AbstractDAO<Order>{
    private final Connection connection;
    public OrderDAO(Connection connection) throws SQLException {
        super(connection);
        this.connection = new ConnectionFactory().getConnection();
    }
    public boolean checkUniqueness(int id)
    {
        if(id > 0)
            return findById(id) == null;
        else
            return false;
    }
    public void addOrder(Order order)
    {
        if(order!= null)
            insert(order);
        else
            JOptionPane.showMessageDialog(null, "Order is null");
    }
    public void editOrder(int id, Order newOrder)
    {
        if(newOrder!=null)
            edit(newOrder,id);
        else
            JOptionPane.showMessageDialog(null, "Order is null");
    }
    public void deleteOrder(int id){ delete(id);}
}
