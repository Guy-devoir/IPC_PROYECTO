
package proyecto_1;

import java.io.Serializable;

/**
 *
 * @author Dell
 */
public class Ingredient implements Serializable {
    private String name;
    private int quantity;
    private String units;

    public Ingredient() {
    }

    public Ingredient(String name, int quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    
    @Override
    public String toString() {
        return "Ingredient{" + "name=" + name + ", quantity=" + quantity + ", units=" + units + '}';
    }
    
    
}
