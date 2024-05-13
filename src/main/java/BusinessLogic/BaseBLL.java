package BusinessLogic;

import Model.ObjectModel;

import Presentation.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseBLL {
    Controller controller;
    private ArrayList<Object[]> objectsInTable;
    private DefaultTableModel tableModel;
    public BaseBLL(Controller controller) {
        this.controller = controller;
    }

    private void initializeFields() throws NoSuchFieldException, IllegalAccessException {
        Class<?> controllerClass = Controller.class;

        Field objectsInTableField = controllerClass.getDeclaredField("objectsInTable");
        objectsInTableField.setAccessible(true);
        this.objectsInTable = (ArrayList<Object[]>) objectsInTableField.get(controller);

        Field tableModelField = controllerClass.getDeclaredField("tableModel");
        tableModelField.setAccessible(true);
        this.tableModel = (DefaultTableModel) tableModelField.get(controller);
    }
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
    private void addToTable(Object[] newItem)
    {
        objectsInTable.add(newItem);
        tableModel.addRow(newItem);
    }
    private void removeFromTable(int id)
    {
        objectsInTable.remove(id);
        tableModel.removeRow(id);
        if(tableModel.getRowCount() == 0) //if empty, delete the header
            tableModel.setColumnCount(0);
    }
    private void deleteContents()
    {
        try {
            objectsInTable.clear();
            tableModel.setRowCount(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not delete the contents so far in order to view the elements: " + e.getMessage());
        }
    }
    protected void updateWithAllElementsFromDB(ObjectModel objectModel, ArrayList<Object[]> items) throws NoSuchFieldException, IllegalAccessException, SQLException {
        initializeFields();

        deleteContents();

        generateTableHeaders(objectModel);

        for (Object[] item : items) {
            addToTable(item);
        }
    }
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
