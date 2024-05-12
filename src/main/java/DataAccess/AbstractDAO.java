package DataAccess;

import javax.swing.*;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AbstractDAO<T> {
    private final Class<T> type;
    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    public AbstractDAO(Connection connection) {
        this.connection = connection;
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery() {
        return "SELECT * FROM " + type.getSimpleName() + " WHERE id=?";
    }

    public T findById(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObject(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement);
        }
        return null;
    }
    private T createObject(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }
            List<Object> paramValues = getParamValues(resultSet);
            Constructor<T> constructor = getMatchingConstructor(paramValues);

            return constructor.newInstance(paramValues.toArray());
        } catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException |
                 NoSuchMethodException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:createObject " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }
    private List<Object> getParamValues(ResultSet resultSet) throws SQLException {
        List<Object> paramValues = new ArrayList<>();
        Field[] declaredFields = type.getDeclaredFields();
        for (Field field : declaredFields) {

            String fieldName = field.getName();
            switch (field.getType().getSimpleName()) {
                case "int":
                    paramValues.add(resultSet.getInt(fieldName));
                    break;
                case "String":
                    paramValues.add(resultSet.getString(fieldName));
                    break;
                case "float":
                    paramValues.add(resultSet.getFloat(fieldName));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + field.getType().getSimpleName());
            }
        }
        return paramValues;
    }

    private Constructor<T> getMatchingConstructor(List<Object> paramValues) throws NoSuchMethodException {
        Class<?>[] paramTypes = new Class<?>[paramValues.size()];
        for (int i = 0; i < paramValues.size(); i++) {
            paramTypes[i] = paramValues.get(i).getClass();
        }
        return type.getConstructor(paramTypes);
    }
    public void insert(T entity) {
        PreparedStatement statement = null;
        try {
            String insertQuery = getInsertQuery();
            String className = type.getSimpleName();

            statement = connection.prepareStatement(insertQuery);
            setStatementParameters(statement, entity);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "A new " + className + " was inserted successfully!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception: " + e.getMessage());
        } finally {
            closeResources(null, statement);
        }
    }
    public void edit(T entity, int id) {
        PreparedStatement statement = null;
        try {
            String editQuery = getEditQuery();
            String className = type.getSimpleName();
            int numberOfColumns = getNumberOfColumns();

            statement = connection.prepareStatement(editQuery);
            setStatementParameters(statement, entity);
            statement.setInt(numberOfColumns + 1, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, className + " was updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No " + className + " found. No update performed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception: " + e.getMessage());
        } finally {
            closeResources(null, statement);
        }
    }
    public void delete(int id)
    {
        PreparedStatement statement = null;
        try {
            String deleteQuery = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, type.getSimpleName() + " with ID " + id + " was deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No " + type.getSimpleName() + " found with ID " + id + ". No deletion performed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Exception: " + e.getMessage());
        } finally {
            closeResources(null, statement);
        }
    }
    public ArrayList<Object[]> getAll() {
        ArrayList<Object[]> records = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + type.getSimpleName();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                List<Object> paramValues = getParamValues(resultSet);
                Object[] record = paramValues.toArray(new Object[0]);
                records.add(record);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(resultSet, statement);
        }
        return records;
    }
    private int getNumberOfColumns() {
        Field[] fields = type.getDeclaredFields();
        return fields.length;
    }
    private String getInsertQuery() {
        String tableName = type.getSimpleName();
        Field[] declaredFields = type.getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : declaredFields) {
            columns.append(field.getName()).append(", ");
            values.append("?, ");
        }

        //remove the last comma or question mark
        String columnsString = columns.substring(0, columns.length() - 2);
        String valuesString = values.substring(0, values.length() - 2);

        return "INSERT INTO " + tableName + " (" + columnsString + ") VALUES (" + valuesString + ")";
    }
    private String getEditQuery() {
        String tableName = type.getSimpleName();
        Field[] declaredFields = type.getDeclaredFields();

        StringBuilder setClause = new StringBuilder();

        for (Field field : declaredFields) {
            String fieldName = field.getName();
            setClause.append(fieldName).append(" = ?, ");
        }

        String setClauseString = setClause.substring(0, setClause.length() - 2);
        return "UPDATE " + tableName + " SET " + setClauseString + " WHERE id = ?";
    }
    private void setStatementParameters(PreparedStatement statement, T entity) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                statement.setObject(i + 1, value);
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Failed to set statement parameter: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Failed to set statement parameter: " + e.getMessage());
            }
        }
    }
    protected void closeResources(ResultSet resultSet, PreparedStatement statement) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Failed to close resources: " + e.getMessage());
        }
    }
}