package Connection;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Establish a valid connection
 */
public class ConnectionFactory {
    private Properties properties;
    public ConnectionFactory() {
        loadProperties();
    }

    /**
     * loads properties of the file db.properties situated in the same package
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/java/Connection/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error trying to connect to the database");
        }
    }

    /**
     * Gets connection by asking for db.url, db. Username, db. Password
     * @return returns the details needed for connecting to the database
     * @throws SQLException In case of a failed connection an exception is thrown
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }
}