package proyecto_1;

public class Client {
    private int id;
    private String name;
    private String address;
    private int phone;
    private String nit;

    public Client() {
    }

    public Client(int id, String name, String address, int phone, String nit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.nit = nit;
    }

    public int getId() {
        return id;
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

    public String getNit() {
        return nit;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setNit(String nit) {
        this.nit = nit;
    }

}
