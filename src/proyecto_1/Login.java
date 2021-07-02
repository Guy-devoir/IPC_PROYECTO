package proyecto_1;

/**
 * @author Brayan Mica
 * @author Luciano Xiquín
 * @author Oscar Emilio
 */
import com.google.gson.Gson;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static proyecto_1.Proyecto_1.Listaacciones;

public class Login implements ActionListener{
    /*las logaciones siguen siendo posibles solo es de importar la lista creada 
    *directamente desde la clase principal
    */
    JFrame frame;
    JPanel panel;
    JLabel userLabel;
    JTextField user;
    JLabel passwordLabel;
    JPasswordField password;
    JButton validacion;
    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    String usuarioactual;

    public Login() throws IOException {
        leer_usuarios();
        frame = new JFrame();        
        panel = new JPanel();        
        userLabel = new JLabel();
        userLabel.setText("Usuario");
        user = new JTextField();
        passwordLabel = new JLabel();
        passwordLabel.setText("Contraseña");
        password = new JPasswordField();
        validacion = new JButton("ACEPTAR");
        validacion.addActionListener(this);

        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Login");

        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.add(userLabel);
        userLabel.setLocation(30, 30);
        panel.add(user);
        user.setLocation(30, 60);
        panel.add(passwordLabel);
        passwordLabel.setLocation(30, 90);
        panel.add(password);
        password.setLocation(30, 120);
        panel.add(validacion);
        validacion.setLocation(30, 150);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        frame.add(panel);
        frame.setVisible(true);
    }

    
    //Se usa el actionPerformed y no el abstractAction porque es único botón
    @Override
    public void actionPerformed(ActionEvent e) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String userText = user.getText();
        String pass = password.getText();
        usuarioactual = userText;
        boolean valid = validacion(userText, pass);
        
        /*En lugar de que el 'for' este en el 'actionPerformed' estará en un procedimiento aparte
        *Esto evitara la producción de un log error por accidente
        */
        if (valid == true) {
            Dashboard menu = null;
            try {
                menu = new Dashboard(usuarioactual);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            menu.setVisible(true);
            frame.setVisible(false);
            //Los logs como se puede ver serán agregados de la misma manera en la cual se hacian en el programa de consola
            Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Inicio de sesión exitoso");
        } else {
            System.out.println("El usuario o la contraseña estan mal escritos");
            System.out.println("Porfavor vulve a ingresarlos \n");
            //Agregando usuarios fallidos al logacciones
            Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + userText + ": Inicio de sesión fallido");
        }        
        try {
            logacciones();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void leer_usuarios() throws IOException {
        Usuario[] aux;
        Gson gson = new Gson();
        String json = "";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("users.json"));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                json += linea;
            }
            buffer.close();
        } catch (FileNotFoundException e) {

        }
        aux = gson.fromJson(json, Usuario[].class);

        usuarios.addAll(Arrays.asList(aux));
        Set<Usuario> set = new HashSet<>(usuarios);
        usuarios.clear();
        usuarios.addAll(set);
    }
    
    private boolean validacion(String userText, String pass) {
        boolean aux = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (userText.equals(usuarios.get(i).getUsername()) && pass.equals(usuarios.get(i).getPassword())) {
                aux = true;               
            }
        }
        return aux;
    }
    
    private static void logacciones() throws IOException{
                FileWriter file = new FileWriter("log.log");
                for (int a=0; a < Listaacciones.size();a++){
                    file.write(""+ Listaacciones.get(a) +"\n");
                }
                file.close();
    }
}
