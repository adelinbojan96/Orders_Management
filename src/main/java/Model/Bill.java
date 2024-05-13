package Model;

import java.sql.Timestamp;

public record Bill(int id, String clientName, String productName, float totalPrice, Timestamp timestamp) implements ObjectModel{
    //total price = quantity * price
}
