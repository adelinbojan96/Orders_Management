package BusinessLogic;

import DataAccess.LogDAO;
import Model.Bill;
import Presentation.Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * Class for displaying the log of orders
 */
public class LogBLL extends BaseBLL{
    private final LogDAO logDAO;
    private final ArrayList<Object[]> elementsFromLog;
    public LogBLL(Controller controller) throws SQLException {
        super(controller);
        this.logDAO = new LogDAO();
        this.elementsFromLog = logDAO.extractLog();
    }
    /**
     * Adds element to log
     * @param bill Bill is the element resulted from creating an order
     * @throws SQLException In case connection can not be established => error
     */
    protected void addToLog(Bill bill) throws SQLException {
        logDAO.addToLog(bill);
    }
    /**
     * view all bills
     * @throws NoSuchFieldException In case there is no such field found
     * @throws IllegalAccessException In case there is illegal access for a field
     */
    public void viewLog() throws NoSuchFieldException, IllegalAccessException {
        Object[] firstItem = elementsFromLog.getFirst();
        updateWithAllElementsFromDB(new Bill( (Integer)firstItem[0], (String) firstItem[1], (String) firstItem[2],
                (Float) firstItem[3], (Timestamp) firstItem[4]), elementsFromLog);
    }
}
