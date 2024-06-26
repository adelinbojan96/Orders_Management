package DataAccess;

import java.sql.*;
import java.util.ArrayList;

import Connection.ConnectionFactory;
import Model.Bill;

import javax.swing.*;
/**
 * Data Access Object for handling logging operations.
 */
public class LogDAO {
    private final Connection connection;
    public LogDAO() throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
    }

    /**
     * Adds a bill entry to the log table.
     * @param bill the bill object to be added to the log
     * @throws SQLException if a database access error occurs
     */
    public void addToLog(Bill bill) throws SQLException {
        PreparedStatement statement = null;
        try {
            String insertQuery = "INSERT INTO log (id, clientName, productName, totalPrice, timestamp) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(insertQuery);

            // Set the values for the prepared statement
            statement.setInt(1, bill.id());
            statement.setString(2, bill.clientName());
            statement.setString(3, bill.productName());
            statement.setFloat(4, bill.totalPrice());
            statement.setTimestamp(5, bill.timestamp());

            statement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception: " + e.getMessage());
        } finally {
            if(statement!=null) statement.close();
        }
    }

    /**
     * extracts the elements from Log table in the database
     * @return arrayList of objects
     * @throws SQLException in case connection is not well established
     */
    public ArrayList<Object[]> extractLog() throws SQLException {
        ArrayList<Object[]> records = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Log";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String clientName = resultSet.getString("clientName");
                String productName = resultSet.getString("productName");
                float totalPrice = resultSet.getFloat("totalPrice");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                Object[] productData = {id, clientName, productName, totalPrice, timestamp};
                records.add(productData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception " + e.getMessage());
        } finally {
            if(resultSet != null) resultSet.close();
            if(statement != null) statement.close();
        }
        return records;
    }

}
