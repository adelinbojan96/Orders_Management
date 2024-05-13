package Model;

public class Client implements ObjectModel {
    private final int id;
    private final String name;
    private final String email;
    private final int age;
    public Client(Integer id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    public int id() {
        return id;
    }
    public String name() {
        return name;
    }
    public String email() {
        return email;
    }
    public int age() {
        return age;
    }
}