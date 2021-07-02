package proyecto_1;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private double cost;
    private double price;
    private ArrayList<Ingredient> ingredientes = new ArrayList<Ingredient>();

    public Product() {
    }

    public Product(int id, String name, String description, double cost, double price, ArrayList<Ingredient> ingredientes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.ingredientes = ingredientes;
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

    public double getCost() {
        return cost;
    }

    public double getPrice() {
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

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Ingredient> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingredient> ingredientes) {
     this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost + ", price=" + price + '}';
    }




}
