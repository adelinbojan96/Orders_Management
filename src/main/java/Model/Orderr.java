package Model;

public class Orderr implements ObjectModel{
    private final int id;
    private final int id_client;
    private final int id_product;
    private final int quantity;
    public Orderr(Integer id, Integer id_client, Integer id_product, Integer quantity)
    {
        this.id = id;
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
    }
    public int id()
    {
        return id;
    }
    public int id_client()
    {
        return id_client;
    }
    public int id_product()
    {
        return id_product;
    }
    public int quantity()
    {
        return quantity;
    }
}
