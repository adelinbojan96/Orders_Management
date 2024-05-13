package BusinessLogic;

import DataAccess.LogDAO;
import Model.Bill;
import Presentation.Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LogBLL extends BaseBLL{
    private final LogDAO logDAO;
    private final ArrayList<Object[]> elementsFromLog;
    public LogBLL(Controller controller) throws SQLException {
        super(controller);
        this.logDAO = new LogDAO();
        this.elementsFromLog = logDAO.extractLog();
    }
    protected void addToLog(Bill bill) throws SQLException {
        logDAO.addToLog(bill);
    }
    public void viewLog() throws SQLException, NoSuchFieldException, IllegalAccessException {
        Object[] firstItem = elementsFromLog.getFirst();
        updateWithAllElementsFromDB(new Bill( (Integer)firstItem[0], (String) firstItem[1], (String) firstItem[2],
                (Float) firstItem[3], (Timestamp) firstItem[4]), elementsFromLog);

    }
}
