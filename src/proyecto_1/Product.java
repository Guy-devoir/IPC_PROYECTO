package proyecto_1;

import java.util.ArrayList;

public class Product {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int price;
    //private Ingredient[] ingredients;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Product() {
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

    public ArrayList<Ingredient> getIngredients() {
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




}
