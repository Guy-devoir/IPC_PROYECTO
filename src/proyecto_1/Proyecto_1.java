package proyecto_1;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Luciano Xiquín
 * @author Oscar Hernández
 * @author Oscar Hernández
 * @author Fernando Mendoza
 **/
public class Proyecto_1 {
    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    static ArrayList<Product> productos = new ArrayList<Product>();
    static ArrayList<Client> clientes = new ArrayList<Client>();
    static ArrayList<Factura> facturas = new ArrayList<Factura>();
    static Config config = new Config();
    public static int contador = 0;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        set_config();
        System.out.println("============================");
        try {
            password();
        } catch (Exception e) {
            System.out.println("Usuario no valido");
        }
    }

    private static void set_config() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        String json = "";
        try {
            //Ruta de acceso de archivo config para luciano comentar la de abjo para poder usar
            BufferedReader buffer = new BufferedReader(new FileReader("config.json"));
            //Ruta de acceso de archivo connfig para brayan
            //BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Messi\\Desktop\\yo\\Proyecto ipc\\Fase 1\\IPC_PROYECTO\\json_files\\config.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        //System.out.println(json);
        config = gson.fromJson(json, Config.class);
        leer_usuarios();
        System.out.println("Archivos a leer: " + config.getLoad());
        
        if(config.getLoad().equals("json")){
        leer_usuarios();
        leer_productos();
        leer_clientes();
        leer_facturas();
        }
        if(config.getLoad().equals("bin")){
        usuarios = (ArrayList<Usuario>) deserialize("users.ipcrm");
        productos = (ArrayList<Product>) deserialize("products.ipcrm");
        clientes = (ArrayList<Client>) deserialize("clients.ipcrm");
        facturas = (ArrayList<Factura>) deserialize("invoices.ipcrm");
        }

    }

    private static void leer_usuarios() throws IOException {
        Usuario[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            //Ruta de acceso de archivo user.json para brayan
            BufferedReader buffer = new BufferedReader(new FileReader("users.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        //System.out.println(json);

        aux = gson.fromJson(json, Usuario[].class);

        //System.out.println(aux[0].getUsername()+aux[0].getPassword());
        usuarios.addAll(Arrays.asList(aux));
        //System.out.println(usuarios.get(0));
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
    }

    private static void password() {
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
        for (int i = 0; i < usuarios.size(); i++) {
            if(user.equals(usuarios.get(i).getUsername())&& pass.equals(usuarios.get(i).getPassword())){
            Menu();
            break;
            }
       
        }
            System.out.println("El usuario o la contraseña estan mal escritos");
            System.out.println("Porfavor vulve a ingresarlos \n");
            password();
     
    }

    private static void Menu() {
        Scanner sc = new Scanner(System.in);
        int opt;
        String aux = ""; 
        boolean menu = true;
        boolean submenu = true;
        while (menu == true) {
            try {
                int cutre;
                System.out.println("Menu Principal" + "\n1)Informaci+on del restaurante" + "\n2)Usuarios" + "\n3)Productos" + "\n4)Clientes" + "\n5)Facturas" + "\n6)Salir y guardar");
                switch (opt = sc.nextByte()) {
                    case 1:
                        System.out.println("Nombre del local: " + config.getName() + "\nDirección: " + config.getAddress() + "\nTelefono: " + config.getPhone());
                        break;
                    case 2:
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 1;
                                    print_object(cutre);
                                    submenu = false;
                                    break;
                                case 2:
                                    submenu = false;
                                    break;
                                case 3:
                                    submenu = false;
                                    break;
                                case 4:
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 3:
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    //Para un if interno que determina que operación realizar.
                                    //La razón por la que se llama cutre es por que es un truco poco elegante
                                    cutre = 2;
                                    print_object(cutre);
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 4:
                        //Sub_menu
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 3;
                                    print_object(cutre);
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    submenu = false;
                                    break;

                            }
                        }
                        break;
                    case 5:
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    cutre = 4;
                                    print_object(cutre);
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    submenu = false;
                                    break;
                            }
                        }
                        break;
                    case 6:
                        System.out.println("1)Guardar en Json" + "\n2)Guardar en binario" + "\n3)Salir sin guardar");
                        submenu = true;
                        while (submenu != false) {
                            switch (opt = sc.nextByte()) {
                                case 1:
                                    break;
                                case 2:
                                    Serializable_binario("users.ipcrm", usuarios);
                                    Serializable_binario("products.ipcrm", productos);
                                    Serializable_binario("clients.ipcrm", clientes);
                                    Serializable_binario("invoices.ipcrm", facturas);
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

    private static void serializable_json() throws IOException {
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

    private static void print_object(int cutre) {
        if(cutre == 1){
            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println(usuarios.get(i).toString());
            }
        }if(cutre == 2){
        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).toString());
        }
        }if(cutre == 3){
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(clientes.get(i).toString());
        }
        }if(cutre == 4){
        for (int i = 0; i < facturas.size(); i++) {
            System.out.println(facturas.get(i).toString());
        }
        }
    }
}
