package proyecto_1;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int price;
    private ArrayList<Ingredient> ingredientes = new ArrayList<Ingredient>();

    public Product() {
    }

    public Product(int id, String name, String description, int cost, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getPrice() {
        return price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<Ingredient> getIngredientes() {
        return ingredientes;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost + ", price=" + price + ", " + ingredientes.toString() + '}';
    }




}
