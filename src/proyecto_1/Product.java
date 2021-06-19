package proyecto_1;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int price;
    private Ingredient[] ingredients;

    public Product() {
    }

    public Product(int id, String name, String description, int cost, int price, Ingredient[] ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.price = price;
        this.ingredients = ingredients;
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

    public Ingredient[] getIngredients() {
        return ingredients;
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

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }



}
