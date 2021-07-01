package proyecto_1;

import com.google.gson.Gson;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static proyecto_1.Proyecto_1.Listaacciones;

public class CFrame extends JFrame{
private Config config = new Config();
JPanel panel_1 = new JPanel();
JPanel panel_2 = new JPanel();
private String usuarioactual;

    public CFrame(Config config, String usuarioactual) throws HeadlessException {
        //Este constructor no construye ni carga ninguno de los paneles simplemente se encarga de lo básico
        //Esto para evitar la aglomeración de parametros y variables
        this.usuarioactual = usuarioactual;
        this.config = config;
        this.setSize(375, 335);
        this.setResizable(false);
        this.setTitle("Información del restaurante");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1, 10, 10));
        this.add(panel_1);
        this.add(panel_2);
    }

    public void showInfo() {
        JLabel nombre = new JLabel("Nombre: ");
        JTextField nombreField = new JTextField(config.getName());
        JLabel address = new JLabel("Dirección: ");
        JTextField addressField = new JTextField(config.getAddress());
        JLabel phone = new JLabel("Teléfono: ");
        String s = Integer.toString(config.getPhone());
        JTextField phoneField = new JTextField(s);

        panel_1.setLayout(new GridLayout(3, 2, 3, 3));
        panel_1.setBorder(BorderFactory.createEmptyBorder(30, 30, 5, 30));
        panel_1.add(nombre);
        panel_1.add(nombreField);
        panel_1.add(address);
        panel_1.add(addressField);
        panel_1.add(phone);
        panel_1.add(phoneField);

        //El botón save SÍ devuelve un log
        JButton save = new JButton(new AbstractAction("Actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                config.setName(nombreField.getText());
                config.setAddress(addressField.getText());
                int n = Integer.valueOf(phoneField.getText());
                config.setPhone(n);
                /*
                *El botón save serializa directamente e instancia un nuevo 'Dashboard'              
                */
                Gson gson = new Gson();
                String jsonConfig = gson.toJson(config);
                writeOnFile("config.json", jsonConfig, false);
                setVisible(false);
                try {
                    Dashboard newMenu = new Dashboard(usuarioactual);
                    newMenu.setVisible(true);
                    //Los logs como se puede ver serán agregados de la misma manera en la cual se hacian en el programa de consola
                    Listaacciones.add( " "+dtf.format(LocalDateTime.now())+"  "+usuarioactual+": Actualizo la información del restaurante.");                                       
                } catch (IOException ex) {
                    Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //El botón volver NO devuelve un log
        JButton returnMain = new JButton(new AbstractAction("Volver") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    Dashboard newMenu = new Dashboard(usuarioactual);
                    newMenu.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel_2.setLayout(new GridLayout(2, 1, 10, 10));
        panel_2.setBorder(BorderFactory.createEmptyBorder(15, 50, 50, 50));
        panel_2.add(save);
        panel_2.add(returnMain);

        //Cada panel tiene su propio 'layout' y 'border' esto por razones esteticas
        this.add(panel_1);
        this.add(panel_2);
        this.setVisible(true);
        

    }

    public void writeOnFile(String pathname, String content, boolean append) {
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
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
