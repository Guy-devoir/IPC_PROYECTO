package proyecto_1;

import java.util.ArrayList;

public class Factura {
    private int id;
    private int client;
    private String date;
    //private Product[] products;
    private ArrayList<Product> products = new ArrayList<Product>();

    public int getId() {
        return id;
    }

    public int getClient() {
        return client;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    
}
