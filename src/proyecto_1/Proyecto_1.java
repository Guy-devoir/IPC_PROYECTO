package proyecto_1;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    static Config config = new Config();
    public static int contador=0;
    
    public static void main(String[] args) throws IOException {
        set_config();
        System.out.println("hola ya pasaste");
        try {
            password();
        } catch (Exception e) {
            System.out.println("Usuario no valido");
        }
    }

    private static void set_config() throws IOException {
        Gson gson = new Gson();
        String json = "";
        try {
            //Ruta de acceso de archivo config para luciano comentar la de abjo para poder usar
            //BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Dell\\Documents\\NetBeansProjects\\Proyecto_1\\json_files\\config.json"));
            //Ruta de acceso de archivo connfig para brayan
            BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Messi\\Desktop\\yo\\Proyecto ipc\\Fase 1\\IPC_PROYECTO\\json_files\\config.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        System.out.println(json);
        config = gson.fromJson(json, Config.class);
        leer_usuarios();
        System.out.println(config.getLoad());
        
        if(config.getLoad().equals("json")){
        leer_usuarios();
        leer_productos();
        }
        if(config.getLoad().equals("bin")){
        
        }

    }

    private static void leer_usuarios() throws IOException {
        Usuario[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            //Ruta de acceso de archivo user.json para luciano comentar la de abjo para poder usar
            //BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Dell\\Documents\\NetBeansProjects\\Proyecto_1\\users.json"));
            //Ruta de acceso de archivo user.json para brayan
            BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Messi\\Desktop\\yo\\Proyecto ipc\\Fase 1\\IPC_PROYECTO\\json_files\\users.json"));
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
    
    private static void leer_productos() {
        
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
        while (menu == true) {
            try {
                System.out.println("Menu Principal" + "\n1)Informaci+on del restaurante" + "\n2)Usuarios");
                switch (opt = sc.nextByte()) {
                    case 1:
                        System.out.println("Nombre del local: " + config.getName() + "\nDirección: " + config.getAddress() + "\nTelefono: " + config.getPhone());
                        break;
                    case 2:
                        aux = "Usuarios";
                        Sub_menu(aux);
                        break;
                    case 3:
                        aux = "Poductos";
                        Sub_menu(aux);
                        break;
                    case 4:
                        aux = "Clientes";
                        Sub_menu(aux);
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

    private static void Sub_menu(String kind) {
        Scanner sc = new Scanner(System.in);
        boolean menu = true;
        while (menu != false) {
            try {
                int opc;
                System.out.println("Menu de " + kind);
                switch (opc = sc.nextByte()) {
                    case 1:
                        System.out.println("Introduzca el id");
                        try{
                        if(kind.equals("Usuarios")){
                        
                        }
                        }catch(Exception e){
                        
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
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

}
