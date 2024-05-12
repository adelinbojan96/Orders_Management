package Model;

public record Bill(int id, String clientName, String productName, float totalPrice) {
    //total price = quantity * price
}
