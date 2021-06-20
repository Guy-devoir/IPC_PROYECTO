package proyecto_1;

import java.io.Serializable;
import java.util.ArrayList;

public class Factura implements Serializable {
    private int id;
    private int client;
    private String date;
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

    public Factura() {
    }

    public Factura(int id, int client, String date) {
        this.id = id;
        this.client = client;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", client=" + client + ", date=" + date + '}';
    }
    
    public String toLongString() {
        return "Factura{" + "id=" + id + ", client=" + client + ", date=" + date + '}';
    }
    
}
