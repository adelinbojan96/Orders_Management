package BusinessLogic;

import Presentation.Controller;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BaseBLL {
    Controller controller;
    private ArrayList<Object[]> objectsInTable;
    private DefaultTableModel tableModel;
    public BaseBLL(Controller controller) {
        this.controller = controller;
    }

    protected void updateTable(Object[] item, String chooser) throws NoSuchFieldException, IllegalAccessException {

        Class<?> controllerClass = Controller.class;

        Field objectsInTableField = controllerClass.getDeclaredField("objectsInTable");
        objectsInTableField.setAccessible(true);
        this.objectsInTable = (ArrayList<Object[]>) objectsInTableField.get(controller);

        Field tableModelField = controllerClass.getDeclaredField("tableModel");
        tableModelField.setAccessible(true);
        this.tableModel = (DefaultTableModel) tableModelField.get(controller);

        Object[] newItem = new Object[item.length - 1];
        System.arraycopy(item, 0, newItem, 0, item.length - 1);

        switch (chooser) {
            case "Add" -> addToTable(newItem);
            case "Edit" -> {
                try {
                    int firstId = (int) item[item.length - 1];
                    addToTable(newItem);
                    removeFromTable(firstId);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            case "Delete" -> {
                int newId = (int) item[item.length - 1];
                removeFromTable(newId);
            }
            case "View" ->{
                //nothing now xd
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
    }
}
