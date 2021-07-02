package proyecto_1;

import com.google.gson.Gson;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import javax.swing.table.DefaultTableModel;
import static proyecto_1.Proyecto_1.Listaacciones;

public class Tablas extends JFrame {

    private final JPanel panel_1 = new JPanel();
    private final JPanel panel_2 = new JPanel();
    private final String usuarioactual;
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    private Usuario[] arregloUsuarios;
    private ArrayList<Client> clientes = new ArrayList<Client>();
    private Client[] arregloClientes;
    private ArrayList<Product> productos = new ArrayList<Product>();
    private Product[] arregloProductos;
    private ArrayList<Ingredient> ingredientes = new ArrayList<Ingredient>();
    private ArrayList<Factura> facturas = new ArrayList<Factura>();
    private Factura[] arregloFacturas;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    int d;
    int d2;
    int d3;

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

    public Tablas(String usuarioactual) {
        this.usuarioactual = usuarioactual;
        this.setSize(375, 345);
        this.setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1, 10, 10));
        this.add(panel_1);
        this.add(panel_2);
    }

    public void usuarios() throws IOException {
        leer_usuarios();
        this.setTitle("CRUD Usuarios");
        JComboBox<String> opciones;
        opciones = new JComboBox<String>();
        opciones.addItem("");
        for (int i = 0; i < usuarios.size(); i++) {
            opciones.addItem(usuarios.get(i).getUsername());
        }

        //Componentes panel 1 
        JLabel userLabel = new JLabel("Nombre de usuario:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contraseña:");
        JTextField passField = new JTextField();

        //Componenetes del panel dos
        JButton agregar = new JButton(new AbstractAction("Agregar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario n = new Usuario(userField.getText(), passField.getText());

                //No duplicados
                boolean varU = validación(n);
                if (varU != true) {
                    usuarios.add(n);
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Agregó un nuevo usuario");
                } else {
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Intentó agregar un usuario duplicado");
                }
                //serializar
                Gson gson = new Gson();
                String json = gson.toJson(usuarios);
                writeOnFile("users.json", json, false);
            }

            boolean validación(Usuario n) {
                boolean aux = false;
                for (int i = 0; i <= usuarios.size() - 1; i++) {
                    if (n.equals(usuarios.get(i).getUsername()) || n.equals(usuarios.get(i).getPassword())) {
                        aux = true;
                    }
                }
                return aux;
            }
        });
        JButton eliminar = new JButton(new AbstractAction("Eliminar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aux = (String) opciones.getSelectedItem();
                for (int i = 0; i < usuarios.size(); i++) {
                    if (aux.equals(usuarios.get(i).getUsername())) {
                        usuarios.remove(i);
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Eliminó al usuario '" + aux + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(usuarios);
                writeOnFile("users.json", json, false);
            }
        });
        JButton table = new JButton(new AbstractAction("Tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    allUsers();
                } catch (IOException ex) {
                    Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton update = new JButton(new AbstractAction("Actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                for (int i = 0; i < usuarios.size(); i++) {
                    if (selectedUser.equals(usuarios.get(i).getUsername())) {
                        usuarios.get(i).setUsername(userField.getText());
                        usuarios.get(i).setPassword(passField.getText());
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": actualizo al usuario '" + selectedUser + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(usuarios);
                writeOnFile("users.json", json, false);
            }
        });
        JButton get = new JButton(new AbstractAction("Datos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                userField.setText(selectedUser);
                for (int i = 0; i < usuarios.size(); i++) {
                    if (selectedUser.equals(usuarios.get(i).getUsername())) {
                        passField.setText(usuarios.get(i).getPassword());
                    }
                }

            }
        });
        JButton volver = new JButton(new AbstractAction("Volver") {
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

        //agregar los componentes
        panel_1.add(opciones);
        panel_1.add(userLabel);
        panel_1.add(userField);
        panel_1.add(passLabel);
        panel_1.add(passField);
        panel_1.setLayout(new GridLayout(5, 1, 3, 3));
        panel_1.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 30));

        panel_2.add(agregar);
        panel_2.add(eliminar);
        panel_2.add(table);
        panel_2.add(update);
        panel_2.add(get);
        panel_2.add(volver);
        panel_2.setLayout(new GridLayout(2, 3, 10, 10));
        panel_2.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        this.add(panel_1);
        this.add(panel_2);
        this.setVisible(true);

    }

    private void leer_usuarios() throws IOException {
        usuarios.clear();
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
        arregloUsuarios = aux;
        usuarios.addAll(Arrays.asList(aux));
        Set<Usuario> set = new HashSet<>(usuarios);
        usuarios.clear();
        usuarios.addAll(set);
    }

    private void allUsers() throws IOException {
        JFrame newFrame = new JFrame("Todos los usuarios");
        JPanel panel = new JPanel();

        leer_usuarios();
        String[] col = {"Usuario", "Contraseña"};
        //Tabla y su modelo
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        for (int i = 0; i < arregloUsuarios.length; i++) {
            String username = arregloUsuarios[i].getUsername();
            String password = arregloUsuarios[i].getPassword();
            Object[] data = {username, password};
            tableModel.addRow(data);
        }

        JTable tabla = new JTable(tableModel);
        panel.add(new JScrollPane(tabla));
        newFrame.add(panel);
        newFrame.setVisible(true);
    }

    public void clientes() throws IOException {
        this.setSize(375, 450);
        leer_clientes();
        this.setTitle("CRUD Clientes");
        JComboBox<String> opciones;
        opciones = new JComboBox<String>();
        opciones.addItem("");
        for (int i = 0; i < clientes.size(); i++) {
            String idCombo = String.valueOf(clientes.get(i).getId());
            opciones.addItem(idCombo);
        }

        //Componentes panel 1 
        JLabel nameLabel = new JLabel("Cliente:");
        JTextField nameField = new JTextField();
        JLabel adressLabel = new JLabel("Dirección:");
        JTextField adressField = new JTextField();
        JLabel phoneLabel = new JLabel("Telefono:");
        JTextField phoneField = new JTextField();
        JLabel nitLabel = new JLabel("NIT:");
        JTextField nitField = new JTextField();

        //Componenetes del panel dos
        JButton agregar = new JButton(new AbstractAction("Agregar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int t = Integer.valueOf(phoneField.getText());
                d = clientes.size() + 1;
                Client n = new Client(d, nameField.getText(), adressField.getText(), t, nitField.getText());
                //No duplicados
                boolean varU = validación(n);
                if (varU != true) {
                    clientes.add(n);
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Agregó un nuevo cliente");
                    d++;
                } else {
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Intentó agregar un cliente duplicado");
                }
                //serializar
                Gson gson = new Gson();
                String json = gson.toJson(clientes);
                writeOnFile("clients.json", json, false);
            }

            boolean validación(Client n) {
                boolean aux = false;
                for (int i = 0; i <= clientes.size() - 1; i++) {
                    if (n.equals(clientes.get(i).getName()) || n.equals(clientes.get(i).getNit())) {
                        aux = true;
                    }
                }
                return aux;
            }
        });
        //Cambiar
        JButton eliminar = new JButton(new AbstractAction("Eliminar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < clientes.size(); i++) {
                    if (t == clientes.get(i).getId()) {
                        clientes.remove(i);
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Eliminó al cliente con id: '" + t + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(clientes);
                writeOnFile("clients.json", json, false);
            }
        });
        JButton table = new JButton(new AbstractAction("Tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    allClients();
                } catch (IOException ex) {
                    Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Cambiar
        JButton update = new JButton(new AbstractAction("Actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < clientes.size(); i++) {
                    if (t == clientes.get(i).getId()) {
                        clientes.get(i).setName(nameField.getText());
                        clientes.get(i).setAddress(adressField.getText());
                        int aux = Integer.valueOf(phoneField.getText());
                        clientes.get(i).setPhone(aux);
                        clientes.get(i).setNit(nitField.getText());
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": actualizo al cliente con id '" + selectedUser + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(clientes);
                writeOnFile("clients.json", json, false);
            }
        });
        JButton get = new JButton(new AbstractAction("Datos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < clientes.size(); i++) {
                    if (t == clientes.get(i).getId()) {
                        nameField.setText(clientes.get(i).getName());
                        adressField.setText(clientes.get(i).getAddress());
                        String aux = String.valueOf(clientes.get(i).getPhone());
                        phoneField.setText(aux);
                        nitField.setText(clientes.get(i).getNit());
                    }
                }

            }
        });
        JButton volver = new JButton(new AbstractAction("Volver") {
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

        //agregar los componentes
        panel_1.add(opciones);
        panel_1.add(nameLabel);
        panel_1.add(nameField);
        panel_1.add(adressLabel);
        panel_1.add(adressField);
        panel_1.add(phoneLabel);
        panel_1.add(phoneField);
        panel_1.add(nitLabel);
        panel_1.add(nitField);
        panel_1.setLayout(new GridLayout(9, 1, 3, 3));
        panel_1.setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 30));

        panel_2.add(agregar);
        panel_2.add(eliminar);
        panel_2.add(table);
        panel_2.add(update);
        panel_2.add(get);
        panel_2.add(volver);
        panel_2.setLayout(new GridLayout(2, 3, 10, 10));
        panel_2.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        this.add(panel_1);
        this.add(panel_2);
        this.setVisible(true);

    }

    private void leer_clientes() throws IOException {
        clientes.clear();
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
        arregloClientes = aux;
        clientes.addAll(Arrays.asList(aux));
        Set<Client> set = new HashSet<>(clientes);
        clientes.clear();
        clientes.addAll(set);
    }

    private void allClients() throws IOException {
        JFrame newFrame = new JFrame("Todos los Clientes");
        JPanel panel = new JPanel();

        leer_clientes();
        String[] col = {"id", "Nombre", "Dirección", "Telefono", "NIT"};
        //Tabla y su modelo
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        for (int i = 0; i < arregloClientes.length; i++) {
            int id = arregloClientes[i].getId();
            String name = arregloClientes[i].getName();
            String adress = arregloClientes[i].getAddress();
            int phone = arregloClientes[i].getPhone();
            String nit = arregloClientes[i].getNit();
            Object[] data = {id, name, adress, phone, nit};
            tableModel.addRow(data);
        }

        JTable tabla = new JTable(tableModel);
        panel.add(new JScrollPane(tabla));
        newFrame.add(panel);
        newFrame.setVisible(true);
    }

    public void productos() throws IOException {
        this.setSize(450, 600);
        leer_productos();
        this.setTitle("CRUD Productos");
        JComboBox<String> opciones;
        opciones = new JComboBox<String>();
        opciones.addItem("");
        for (int i = 0; i < productos.size(); i++) {
            String idCombo = String.valueOf(productos.get(i).getId());
            opciones.addItem(idCombo);
        }
        //Componentes panel 1 
        JLabel nameLabel = new JLabel("Nombre del producto:");
        JTextField nameField = new JTextField();
        JLabel adressLabel = new JLabel("Descripción:");
        JTextField adressField = new JTextField();
        JLabel costLabel = new JLabel("Costo:");
        JTextField costField = new JTextField();
        JLabel priceLabel = new JLabel("Precio:");
        JTextField priceField = new JTextField();
                

        //Componenetes del panel dos
        JButton agregar = new JButton(new AbstractAction("Agregar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                double c = Double.valueOf(costField.getText());
                double p = Double.valueOf(priceField.getText());
                d2 = productos.size() + 1;
                Product n = new Product(d2, nameField.getText(), adressField.getText(), c, p,ingredientes);
                //No duplicados
                boolean varU = validación(n);
                if (varU != true) {
                    productos.add(n);
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Agregó un nuevo producto");
                    d2++;
                } else {
                    Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Intentó agregar un prodcuto duplicado");
                }
                //serializar
                Gson gson = new Gson();
                String json = gson.toJson(productos);
                writeOnFile("products.json", json, false);
            }

            boolean validación(Product n) {
                boolean aux = false;
                for (int i = 0; i <= productos.size() - 1; i++) {
                    if (n.getName().equals(productos.get(i).getName())) {
                        aux = true;
                    }
                }
                return aux;
            }
        });
        JButton eliminar = new JButton(new AbstractAction("Eliminar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < productos.size(); i++) {
                    if (t == productos.get(i).getId()) {
                        productos.remove(i);
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Eliminó un producto con id: '" + t + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(productos);
                writeOnFile("products.json", json, false);
            }
        });
        JButton table = new JButton(new AbstractAction("Tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    allProducts();
                } catch (IOException ex) {
                    Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });        
        JButton update = new JButton(new AbstractAction("Actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < productos.size(); i++) {
                    if (t == productos.get(i).getId()) {
                        productos.get(i).setName(nameField.getText());
                        productos.get(i).setDescription(adressField.getText());
                        double aux1 = Double.valueOf(costField.getText());
                        productos.get(i).setCost(aux1);
                        double aux2 = Double.valueOf(priceField.getText());
                        productos.get(i).setPrice(aux2);
                        Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": actualizo el producto con id '" + selectedUser + "'");
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(productos);
                writeOnFile("products.json", json, false);;
            }
        });
        JButton get = new JButton(new AbstractAction("Datos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < productos.size(); i++) {
                    if (t == productos.get(i).getId()) {
                        nameField.setText(productos.get(i).getName());
                        adressField.setText(productos.get(i).getDescription());
                        String a = String.valueOf(productos.get(i).getCost());
                        costField.setText(a);
                        String b = String.valueOf(productos.get(i).getPrice());
                        priceField.setText(b);
                    }
                }

            }
        }); 
        JButton inge = new JButton(new AbstractAction("Ingredientes") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                int t = Integer.valueOf(selectedUser);
                for (int i = 0; i < productos.size(); i++) {
                    if (t == productos.get(i).getId()) {
                        try {
                            ingredientes(productos.get(i));
                        } catch (IOException ex) {
                            Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        });
        JButton volver = new JButton(new AbstractAction("Volver") {
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
        
        //agregar los componentes
        panel_1.add(opciones);
        panel_1.add(nameLabel);
        panel_1.add(nameField);
        panel_1.add(adressLabel);
        panel_1.add(adressField);
        panel_1.add(costLabel);
        panel_1.add(costField);
        panel_1.add(priceLabel);
        panel_1.add(priceField);
        panel_1.setLayout(new GridLayout(9, 1, 3, 3));
        panel_1.setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 30));

        panel_2.add(agregar);
        panel_2.add(eliminar);
        panel_2.add(table);
        panel_2.add(update);
        panel_2.add(get);
        panel_2.add(inge);
        panel_2.add(volver);
        panel_2.setLayout(new GridLayout(3, 3, 10, 10));
        panel_2.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        this.add(panel_1);
        this.add(panel_2);
        this.setVisible(true);

    }
    
    private void ingredientes(Product product) throws IOException {
        JFrame newFrame = new JFrame("Ingredientes de " + product.getName());
        newFrame.setSize(400, 450);
        newFrame.setLayout(new GridLayout(2, 1, 10, 10));
        JComboBox<String> opciones;
        opciones = new JComboBox<String>();
        opciones.addItem("");
        for (int i = 0; i < product.getIngredientes().size(); i++) {
            String idCombo = String.valueOf(product.getIngredientes().get(i).getName());
            opciones.addItem(idCombo);
        }
        //Componentes para el array de ingredientes
        JLabel nombreIn = new JLabel("Nombre del Producto");
        JTextField nombreInField = new JTextField();
        JLabel quantityIn = new JLabel("Cantidad");
        JTextField quantityInField = new JTextField();
        JLabel unitsIn = new JLabel("Unidades");
        JTextField unitsInField = new JTextField();

        JButton agregar = new JButton(new AbstractAction("Agregar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int c = Integer.valueOf(quantityInField.getText());
                Ingredient n = new Ingredient(nombreInField.getText(), c, unitsInField.getText());
                ingredientes.add(n);               
                product.getIngredientes().add(n);
                
                Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": modificó un producto");

            }

        });
        //Cambiar
        JButton eliminar = new JButton(new AbstractAction("Eliminar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                
                for (int i = 0; i < product.getIngredientes().size(); i++) {
                    if (selectedUser.equals(product.getIngredientes().get(i).getName())) {
                        product.getIngredientes().remove(i);
                    }
                }
            }
        });
        JButton table = new JButton(new AbstractAction("Tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    verIngredientes(product);
                } catch (IOException ex) {
                    Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        //Cambiar
        JButton update = new JButton(new AbstractAction("Actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();                
                for (int i = 0; i < product.getIngredientes().size(); i++) {
                    if (selectedUser.equals(product.getIngredientes().get(i).getName())) {
                        product.getIngredientes().get(i).setName(nombreInField.getText());
                        int aux = Integer.valueOf(quantityInField.getText());
                        product.getIngredientes().get(i).setQuantity(aux);
                        product.getIngredientes().get(i).setUnits(unitsInField.getText());                     
                    }
                }
            }
        });
        JButton get = new JButton(new AbstractAction("Datos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) opciones.getSelectedItem();
                for (int i = 0; i < product.getIngredientes().size(); i++) {
                    if (selectedUser.equals(product.getIngredientes().get(i).getName())) {
                        nombreInField.setText(product.getIngredientes().get(i).getName());
                        String aux = String.valueOf(product.getIngredientes().get(i).getQuantity());
                        quantityInField.setText(aux);
                        unitsInField.setText(product.getIngredientes().get(i).getUnits());
                    }
                }

            }
        }); 
        
        JPanel panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel3.setLayout(new GridLayout(7,1,5,5));
        
        JPanel panel4 = new JPanel();
        panel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel4.setLayout(new GridLayout(3,2,5,5));
        
        panel3.add(opciones);
        panel3.add(nombreIn);
        panel3.add(nombreInField);
        panel3.add(quantityIn);
        panel3.add(quantityInField);
        panel3.add(unitsIn);
        panel3.add(unitsInField);
        
        panel4.add(agregar);
        panel4.add(eliminar);
        panel4.add(table);
        panel4.add(update);
        panel4.add(get);
        
        newFrame.add(panel3);
        newFrame.add(panel4);
        
        newFrame.setVisible(true);
        
    }
    
    private void leer_productos() throws IOException {
        productos.clear();
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
        arregloProductos = aux;
        Set<Product> set = new HashSet<>(productos);
        productos.clear();
        productos.addAll(set);
        ingredientes = productos.get(0).getIngredientes();
        //System.out.println(usuarios.get(0));
    }

    private void allProducts() throws IOException {
        JFrame newFrame = new JFrame("Todos los Productos");
        JPanel panel = new JPanel();

        leer_productos();
        String[] col = {"id", "Nombre", "Descripción", "Costo", "Precio"};
        //Tabla y su modelo
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        for (int i = 0; i < arregloProductos.length; i++) {
            int id = arregloProductos[i].getId();
            String name = arregloProductos[i].getName();
            String description = arregloProductos[i].getDescription();
            double cost = arregloProductos[i].getCost();
            double price = arregloProductos[i].getPrice();
            Object[] data = {id, name, description, cost, price};
            tableModel.addRow(data);
        }
        JTable tabla = new JTable(tableModel);
        panel.add(new JScrollPane(tabla));
        newFrame.add(panel);
        newFrame.setVisible(true);
        
    }
    
    private void verIngredientes(Product product) throws IOException {
        JFrame newFrame = new JFrame("Ingredientes");
        JPanel panel = new JPanel();

        leer_productos();
        String[] col = {"Nombre", "Cantidad", "Unidades"};
        //Tabla y su modelo
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        for (int i = 0; i < arregloProductos.length; i++) {
            String name = product.getIngredientes().get(i).getName();
            int a = product.getIngredientes().get(i).getQuantity();
            String units = product.getIngredientes().get(i).getUnits();
            Object[] data = { name, a, units};
            tableModel.addRow(data);
        }
        JTable tabla = new JTable(tableModel);
        panel.add(new JScrollPane(tabla));
        newFrame.add(panel);
        newFrame.setVisible(true);
        
    }
  
    public void facturas() throws IOException {
        this.setSize(450, 600);
        leer_productos();
        leer_clientes();
        leer_facturas();
        this.setTitle("Facturas");
        ArrayList<Product> auxProducts = new ArrayList<Product>();
        
        //Componentes panel 1         
        JLabel clienteLabel = new JLabel("Cliente:");
        JComboBox opcionesClientes;
        opcionesClientes = new JComboBox<String>();
        opcionesClientes.addItem(" ");
        for (int i = 0; i < clientes.size(); i++) {
            String idCombo = String.valueOf(clientes.get(i).getId());
            opcionesClientes.addItem(idCombo);
        }
                        
        JLabel productsLabel = new JLabel("Productos:");        
        JComboBox<String> opcionesProductos;
        opcionesProductos = new JComboBox<String>();        
        for (int i = 0; i < productos.size(); i++) {
            String idCombo = productos.get(i).getName();
            opcionesProductos.addItem(idCombo);
        }

        //Componenetes del panel dos
        JButton agregar = new JButton(new AbstractAction("Agregar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                d3 = facturas.size() + 1;
                String aux = (String) opcionesClientes.getSelectedItem();
                int idCliente = Integer.valueOf(aux);

                Factura n = new Factura(d3, idCliente, dtf.format(LocalDateTime.now()),auxProducts);
                facturas.add(n);
                Listaacciones.add(" " + dtf.format(LocalDateTime.now()) + "  " + usuarioactual + ": Creo una factura");

                Gson gson = new Gson();
                String json = gson.toJson(facturas);
                writeOnFile("invoices.json", json, false);
            }
        });
        JButton productButton = new JButton(new AbstractAction("Agregrar P") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < productos.size(); i++) {
                    String og = productos.get(i).getName();
                    String internal = (String) opcionesProductos.getSelectedItem();
                    if (internal.equals(og)) {
                        auxProducts.add(productos.get(i));
                    }
                }
            }

        });
        JButton table = new JButton(new AbstractAction("Tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    allInvoices();
                } catch (IOException ex) {
                    Logger.getLogger(Tablas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
            
        });
        JButton volver = new JButton(new AbstractAction("Volver") {
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
        //agregar los componentes

        panel_1.add(clienteLabel);
        panel_1.add(opcionesClientes);
        panel_1.add(productsLabel);
        panel_1.add(opcionesProductos);
        panel_1.setLayout(new GridLayout(4, 1, 3, 3));
        panel_1.setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 30));
        
        panel_2.add(agregar);
        panel_2.add(productButton);
        panel_2.add(table);
        panel_2.add(volver);
        panel_2.setLayout(new GridLayout(2, 2, 10, 10));
        panel_2.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        this.add(panel_1);
        this.add(panel_2);
        this.setVisible(true);

    }
    
    private void leer_facturas() throws IOException {
        facturas.clear();
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
        arregloFacturas = aux;
        facturas.addAll(Arrays.asList(aux));
        Set<Factura> set = new HashSet<>(facturas);
        facturas.clear();
        facturas.addAll(set);
    }

    private void allInvoices() throws IOException {
    JFrame newFrame = new JFrame("Todos las facturas");
        JPanel panel = new JPanel();

        leer_facturas();
        String[] col = {"id", "id Cliente", "Fecha", "Producto", "Precio"};
        //Tabla y su modelo
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        for (int i = 0; i < arregloFacturas.length; i++) {
            int id = arregloFacturas[i].getId();
            int idClient = arregloFacturas[i].getClient();
            String date = arregloFacturas[i].getDate();
            String nameProduct = arregloFacturas[i].getProducts().get(0).getName();
            double price = arregloFacturas[i].getProducts().get(0).getPrice();
            Object[] data = {id, idClient, date, nameProduct, price};
            tableModel.addRow(data);
        }
        JTable tabla = new JTable(tableModel);
        panel.add(new JScrollPane(tabla));
        newFrame.add(panel);
        newFrame.setVisible(true);

    }
}