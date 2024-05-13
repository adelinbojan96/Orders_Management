package Model;

/**
 * Product class implements objectModel
 */
public class Product implements ObjectModel {
    private final int id;
    private final String name;
    private final String description;
    private final float price;
    private final String category;
    private final int quantity;

    public Product(Integer id, String name, String description, Float price, String category, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public float price() {
        return price;
    }

    public String category() {
        return category;
    }

    public int quantity() {
        return quantity;
    }
}
