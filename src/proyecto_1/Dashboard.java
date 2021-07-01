package proyecto_1;

import com.google.gson.Gson;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static proyecto_1.Proyecto_1.Listaacciones;

/**
 *
 * @author Luciano Xiquín
 */
public class Dashboard extends JFrame implements ActionListener {

    private final JLabel nombre;
    private final JLabel direc;
    private JLabel telefono;
    private JButton info;
    private JButton usuarios;
    private JButton clientes;
    private JButton productos;
    private JButton facturas;
    private JButton log;
    private JPanel panel;
    private String usuarioactual;
    private Config config = new Config();

    public Dashboard(String usuarioactual) throws IOException, ClassNotFoundException {
        this.usuarioactual = usuarioactual;
        set_config();
        nombre = new JLabel(config.getName());
        direc = new JLabel("Dirección: " + config.getAddress());
        String s = Integer.toString(config.getPhone());
        telefono = new JLabel("Teléfono: " + s);
        panel = new JPanel();

        this.setSize(300, 400);
        this.setTitle("Menu Principal");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        panel.setLayout(new GridLayout(9, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        info = new JButton(new AbstractAction("Información del restaurante") {
            @Override
            public void actionPerformed(ActionEvent e) {
                CFrame frameInfo = new CFrame(config, usuarioactual);
                frameInfo.showInfo();
                setVisible(false);
                
            }
        });
        usuarios = new JButton(new AbstractAction("Usuarios") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        clientes = new JButton("Clientes");
        productos = new JButton("Productos");
        facturas = new JButton("Facturas");
        log = new JButton(new AbstractAction("Logacciones"){
        @Override
            public void actionPerformed(ActionEvent e) {
            try {
                logacciones();
            } catch (IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        });

        panel.add(nombre);
        panel.add(direc);
        panel.add(telefono);

        panel.add(info);
        panel.add(usuarios);
        panel.add(clientes);
        panel.add(productos);
        panel.add(facturas);
        panel.add(log);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("...");
    }

    private void set_config() throws IOException, ClassNotFoundException {
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
    }
    
    private static void logacciones() throws IOException{
                FileWriter file = new FileWriter("log.log");
                for (int a=0; a < Listaacciones.size();a++){
                    file.write(""+ Listaacciones.get(a) +"\n");
                }
                file.close();
    }
}
