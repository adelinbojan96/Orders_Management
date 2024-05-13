package Model;

import java.sql.Timestamp;

/**
 * record for bill
 * @param id id for bill
 * @param clientName client name
 * @param productName product name
 * @param totalPrice total price
 * @param timestamp time
 */
public record Bill(int id, String clientName, String productName, float totalPrice, Timestamp timestamp) implements ObjectModel{
    //total price = quantity * price
}
