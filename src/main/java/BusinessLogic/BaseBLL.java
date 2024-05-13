package BusinessLogic;

import Model.ObjectModel;

import Presentation.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * BaseBLL is a class which is parent to all the classes from the BusinessLogic package: ClientBLL, LogBLL, OrderBLL,ProductBLL
 */
public class BaseBLL {
    Controller controller;
    private ArrayList<Object[]> objectsInTable;
    private DefaultTableModel tableModel;
    public BaseBLL(Controller controller) {
        this.controller = controller;
    }

    /**
     * Uses reflection to get access for the objectsInTable array from the Controller class
     * @throws NoSuchFieldException In case it does not find a field (objectsInTable or tableModel) => returns an error
     * @throws IllegalAccessException In case it is impossible to access (they are not set to be accessed) => returns an error
     */
    private void initializeFields() throws NoSuchFieldException, IllegalAccessException {
        Class<?> controllerClass = Controller.class;

        Field objectsInTableField = controllerClass.getDeclaredField("objectsInTable");
        objectsInTableField.setAccessible(true);
        this.objectsInTable = (ArrayList<Object[]>) objectsInTableField.get(controller);

        Field tableModelField = controllerClass.getDeclaredField("tableModel");
        tableModelField.setAccessible(true);
        this.tableModel = (DefaultTableModel) tableModelField.get(controller);
    }

    /**
     * Updates the tableModel and objectsInTable according to parameters
     * @param objectModel Either Bill, Client or Product
     * @param item objectModel in Object[] form
     * @param chooser A string used to access add, edit or delete
     * @throws NoSuchFieldException  In case it does not find a field => returns an error
     * @throws IllegalAccessException  In case it is impossible to access => returns an error
     */
    protected void updateTable(ObjectModel objectModel, Object[] item, String chooser) throws NoSuchFieldException, IllegalAccessException {
        initializeFields();

        Object[] newItem = new Object[item.length - 1];
        System.arraycopy(item, 0, newItem, 0, item.length - 1);

        generateTableHeaders(objectModel); //if empty, create header

        switch (chooser) {
            case "Add" -> addToTable(newItem);
            case "Edit" -> {
                int firstId = (int) item[item.length - 1];
                addToTable(newItem);
                removeFromTable(firstId);
            }
            case "Delete" -> {
                int newId = (int) item[item.length - 1];
                removeFromTable(newId);
            }
        }
    }

    /**
     * Adds object item to table
     * @param newItem Client, product or bill in Object[] form
     */
    private void addToTable(Object[] newItem)
    {
        objectsInTable.add(newItem);
        tableModel.addRow(newItem);
    }

    /**
     * Removes element from table according to id
     * @param id id for element in table (to be removed)
     */
    private void removeFromTable(int id)
    {
        objectsInTable.remove(id);
        tableModel.removeRow(id);
        if(tableModel.getRowCount() == 0) //if empty, delete the header
            tableModel.setColumnCount(0);
    }

    /**
     * Deletes everything from table
     */
    private void deleteContents()
    {
        try {
            objectsInTable.clear();
            tableModel.setRowCount(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not delete the contents so far in order to view the elements: " + e.getMessage());
        }
    }

    /**
     *
     * @param objectModel Client, product or bill
     * @param items  Client, product or bill in Object[] form
     * @throws NoSuchFieldException no such field found
     * @throws IllegalAccessException no access
     */
    protected void updateWithAllElementsFromDB(ObjectModel objectModel, ArrayList<Object[]> items) throws NoSuchFieldException, IllegalAccessException {
        initializeFields();

        deleteContents();

        generateTableHeaders(objectModel);

        for (Object[] item : items) {
            addToTable(item);
        }
    }

    /**
     * Generate header in case one element exists in table
     * @param firstItem first item (if it exists) from the table from which (through reflection) we find the properties(needed for columns)
     */
    protected void generateTableHeaders(ObjectModel firstItem) {
        if (tableModel.getColumnCount() == 0 && firstItem != null) {

            Class<?> itemClass = firstItem.getClass();

            Field[] fields = itemClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                tableModel.addColumn(fieldName);
            }
        }
    }
}
