package proyecto_1;

public class Config {

    private String name;
    private String address;
    private int phone;
    private String load;

    public Config() {
    }

    public Config(String name, String address, int phone, String load) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.load = load;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public String getLoad() {
        return load;
    }

}
