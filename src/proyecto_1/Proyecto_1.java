package proyecto_1;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Luciano Xiquín
 * @author Oscar Hernández
 * @author Brayan Mica
 **/
public class Proyecto_1 {
    
    static ArrayList<Product> productos = new ArrayList<Product>();
    static ArrayList<Client> clientes = new ArrayList<Client>();
    static ArrayList<Factura> facturas = new ArrayList<Factura>();
    static Config config = new Config();
    static ArrayList<String> Listaacciones = new ArrayList<String>();
    static String usuarioactual;
    public static int contador = 0;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Login login = new Login();
        login.getClass();      
    }

    private static void set_config() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        String json = "";
        try {
            
            BufferedReader buffer = new BufferedReader(new FileReader("config.json"));            
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        //System.out.println(json);
        config = gson.fromJson(json, Config.class);
        System.out.println("Archivos a leer: " + config.getLoad());

        if (config.getLoad().equals("json")) {
            
            leer_productos();
            leer_clientes();
            leer_facturas();
        }
        if (config.getLoad().equals("bin")) {
            
            productos = (ArrayList<Product>) deserialize("products.ipcrm");
            clientes = (ArrayList<Client>) deserialize("clients.ipcrm");
            facturas = (ArrayList<Factura>) deserialize("invoices.ipcrm");
        }

    }

    private static void leer_productos() throws IOException {
        Product[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("products.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        //System.out.println(json);

        aux = gson.fromJson(json, Product[].class);

        //System.out.println(aux[0].getUsername()+aux[0].getPassword());
        productos.addAll(Arrays.asList(aux));
        Set<Product> set = new HashSet<>(productos);
        productos.clear();
        productos.addAll(set);
        //System.out.println(usuarios.get(0));
    }

    private static void leer_clientes() throws IOException {
        Client[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("clients.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
        } catch (FileNotFoundException e) {

        }

        aux = gson.fromJson(json, Client[].class);

        clientes.addAll(Arrays.asList(aux));
        Set<Client> set = new HashSet<>(clientes);
        clientes.clear();
        clientes.addAll(set);
    }

    private static void leer_facturas() throws IOException {
        Factura[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("invoices.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
        } catch (FileNotFoundException e) {

        }

        aux = gson.fromJson(json, Factura[].class);

        facturas.addAll(Arrays.asList(aux));
        Set<Factura> set = new HashSet<>(facturas);
        facturas.clear();
        facturas.addAll(set);
    }

    private static void password() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String user;
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Usuario:");
        user = sc1.nextLine();
        System.out.println("Contraseña:");

        Console console = System.console();
        char[] password = console.readPassword();
        String pass = "";
        for (int a = 0; a < password.length; a++) {
            pass += String.valueOf(password[a]);;
        }

        //Procedimiento para verificar que la contraseña si la contraseña es la misma probarlo en consola
        //Se utilizo procedimiento de recursividad para cada vuleta hasta ser la correcta
       
        System.out.println("El usuario o la contraseña estan mal escritos");
        System.out.println("Porfavor vulve a ingresarlos \n");
        //Agregando usuarios fallidos al logacciones
        Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+user+": Inicio de sesión fallido");
        password();

    }
/*
    private static void Menu() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Scanner sc = new Scanner(System.in);
        int opt;
        String aux = "";
        boolean menu = true;
        boolean submenu = true;
        while (menu == true) {
            try {
                int cutre;
                System.out.println("Menu Principal" 
                        + "\n1)Informaci+on del restaurante" 
                        + "\n2)Usuarios" 
                        + "\n3)Productos" 
                        + "\n4)Clientes" 
                        + "\n5)Facturas" 
                        + "\n6)Salir y guardar");
                switch (opt = sc.nextByte()) {
                    case 1:
                        System.out.println("Nombre del local: " + config.getName() + "\nDirección: " + config.getAddress() + "\nTelefono: " + config.getPhone());
                        break;
                    case 2:
                        submenu = true;
                        while (submenu != false) {
                            System.out.println( "Menu Usuario"
                                    + "\n1)Listar todos los usuarios"
                                    + "\n2)Eliminar Usuario"
                                    + "\n3)Mostrar usuario <dar id>");
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 1;
                                    print_object(cutre);
                                    submenu = false;
                                    break;
                                case 2:
                                    System.out.println("Usuario a eliminar: ");
                                    Scanner sc1_1 = new Scanner(System.in);
                                    String UserDel = sc1_1.nextLine();
                                    for (int i = 0; i < usuarios.size(); i++) {
                                        if (UserDel.equals(usuarios.get(i).getUsername())) {
                                            System.out.println("Se ha eliminado al usuario"+usuarios.get(i));
                                            Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+usuarioactual+": Elimno al usuario "+usuarios.get(i));
                                            usuarios.remove(i);      
                                        }
                                    }
                                    submenu = false;
                                    break;
                                case 3:
                                    System.out.print("Mostrar contraseña del usuario: ");
                                    Scanner sc1_2 = new Scanner(System.in);
                                    String get_aux = sc1_2.nextLine();
                                    for (int i = 0; i < usuarios.size(); i++) {
                                        if (get_aux.equals(usuarios.get(i).getUsername())) {
                                            System.out.println("Contraseña: " + usuarios.get(i).getPassword());
                                        }
                                    }
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 3:
                        submenu = true;
                        while (submenu != false) {
                            System.out.println( "Menu Productos"
                                    + "\n1)Listar todos los productos"
                                    + "\n2)Eliminar producto"
                                    + "\n3)Mostrar contraseña del producto <dar id>");
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    //Para un if interno que determina que operación realizar.
                                    //La razón por la que se llama cutre es por que es un truco poco elegante
                                    cutre = 2;
                                    print_object(cutre);
                                    submenu = false;
                                    break;
                                case 2:
                                    System.out.println("id del Producto: ");
                                    Scanner sc1_1 = new Scanner(System.in);
                                    int aux1 = sc1_1.nextByte();
                                    for (int i = 0; i < productos.size(); i++) {
                                        if (aux1 == productos.get(i).getId()) {
                                            System.out.println("Se ha eliminado al producto "+usuarios.get(i)+" con el id "+aux1);
                                            Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+usuarioactual+": Elimno al producto "+productos.get(i)+ " con id "+ aux1);
                                            productos.remove(i);
                                        }
                                    }
                                    submenu = false;
                                    break;
                                case 3:
                                    System.out.println("Id del producto:");
                                    Scanner sc1_2 = new Scanner(System.in);
                                    int aux2 = sc1_2.nextByte();
                                    for (int i = 0; i < productos.size(); i++) {
                                        if (aux2 == productos.get(i).getId()) {
                                            System.out.println(productos.get(i).toString() + productos.get(i).getIngredientes().toString());
                                        }
                                    }
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 4:
                        //Sub_menu
                        submenu = true;
                        while (submenu != false) {
                            System.out.println( "Menu Clientes"
                                    + "\n1)Listar todos los clientes"
                                    + "\n2)Eliminar cliente"
                                    + "\n3)Mostrar contraseña del cliente <dar id>");
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 3;
                                    print_object(cutre);
                                    submenu = false;
                                    break;
                                case 2:
                                    System.out.println("id del Cliente: ");
                                    Scanner sc1_1 = new Scanner(System.in);
                                    int aux1 = sc1_1.nextByte();
                                    for (int i = 0; i < clientes.size(); i++) {
                                        if (aux1 == clientes.get(i).getId()) {
                                            Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+usuarioactual+": Elimno al cliente "+ clientes.get(i)+ " con el id "+ aux1);
                                            clientes.remove(i);
                                            
                                        }
                                    }
                                    submenu = false;
                                    break;
                                case 3:
                                    System.out.println("Id de cliente:");
                                    Scanner sc1_2 = new Scanner(System.in);
                                    int aux2 = sc1_2.nextByte();
                                    for (int i = 0; i < clientes.size(); i++) {
                                        if (aux2 == clientes.get(i).getId()) {
                                            System.out.println(clientes.get(i).toString());
                                        }
                                    }
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 5:
                        submenu = true;
                        while (submenu != false) {
                            System.out.println( "Menu Facturas"
                                    + "\n1)Listar todas las facturas"
                                    + "\n2)Eliminar factura"
                                    + "\n3)Mostrar contraseña de la factura <dar id>");
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 4;
                                    print_object(cutre);
                                    submenu = false;
                                    break;
                                case 2:
                                    System.out.println("id de la Factura: ");
                                    Scanner sc1_1 = new Scanner(System.in);
                                    int aux1 = sc1_1.nextByte();
                                    for (int i = 0; i < facturas.size(); i++) {
                                        if (aux1 == facturas.get(i).getId()) {
                                            Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+usuarioactual+": Elimno la factura "+ facturas.get(i)+ " con el id "+ aux1);
                                            facturas.remove(i);
                                        }
                                    }
                                    submenu = false;
                                    break;
                                case 3:
                                    System.out.println("id de la Factura: ");
                                    Scanner sc1_2 = new Scanner(System.in);
                                    int aux2 = sc1_2.nextByte();
                                    for (int i = 0; i < facturas.size(); i++) {
                                        if (aux2 == facturas.get(i).getId()) {
                                            System.out.println(facturas.get(i).toString()+ facturas.get(i).getProducts().toString());
                                         }
                                    }
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 6:
                        //log de acciones
                        logacciones();
                        //Seleccion de serealizacion
                        Gson gson = new Gson();
                        System.out.println("*****Serializar*****"
                                + "\n1)Guardar en Json"
                                + "\n2)Guardar en binario"
                                + "\n3)Salir sin guardar");
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    //Serializar Usuarios
                                    String jsonUsuarios = gson.toJson(usuarios);
                                    writeOnFile("users.json", jsonUsuarios, false);
                                    //Serializar Productos
                                    String jsonProductos = gson.toJson(productos);
                                    writeOnFile("products.json", jsonProductos, false);
                                    //Serializar Clientes
                                    String jsonClientes = gson.toJson(clientes);
                                    writeOnFile("clients.json", jsonClientes, false);
                                    //Serializar Facturas
                                    String jsonFacturas = gson.toJson(facturas);
                                    writeOnFile("invoices.json", jsonClientes, false);
                                    System.exit(0);
                                    break;
                                case 2:
                                    Serializable_binario("users.ipcrm", usuarios);
                                    Serializable_binario("products.ipcrm", productos);
                                    Serializable_binario("clients.ipcrm", clientes);
                                    Serializable_binario("invoices.ipcrm", facturas);
                                    System.exit(0);
                                    break;
                                case 3:
                                    System.exit(0);
                                    break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Entreda no valida");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error, introduzca un número");
            }
        }
        
     
    }
    */
    private static void logacciones() throws IOException{
                FileWriter file = new FileWriter("log.log");
                for (int a=0; a < Listaacciones.size();a++){
                    file.write(""+ Listaacciones.get(a) +"\n");
                }
                file.close();
    }

    private static void Serializable_binario(String path, Object object) {
    // Serializar un objeto    
    //Codigo como el de la clase de 16 de Junio del 2021
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Object deserialize(String pathname) throws ClassNotFoundException {
        // Leer un objeto serializado
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathname));
            Object data = objectInputStream.readObject();
            objectInputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    private static void print_object(int cutre) {
        if(cutre == 1){
            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println(usuarios.get(i).toString());
            }
        }if(cutre == 2){
        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).toString() + productos.get(i).getIngredientes().toString());
        }
        }if(cutre == 3){
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(clientes.get(i).toString());
        }
        }if(cutre == 4){
        for (int i = 0; i < facturas.size(); i++) {
            System.out.println(facturas.get(i).toString()+ facturas.get(i).getProducts().toString());
        }
        }
    }
    */
    public static void writeOnFile(String pathname, String content, boolean append) {
        File file;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            file = new File(pathname);
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
