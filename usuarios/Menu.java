package usuarios;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dell-Pc
 */
public class Menu extends javax.swing.JFrame {

    String url = "jdbc:mysql://localhost:3306/tequilera";
    String usuario = "root";
    String contraseña = "";

    Connection conexion = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Menu() {
        setLayout(null);
        setLocationRelativeTo(null);

        initComponents();

    }

    public void agregar(String Nombre, String Codigo, String Precio, String Cantidad) {

        String url = "jdbc:mysql://localhost:3306/tequilera";
        String usuario = "root";
        String contraseña = "";

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            String querry = "INSERT INTO productos (Nombre, Codigo,Precio, Cantidad) VALUES (?, ?,?,?)";

            ps = conexion.prepareStatement(querry);
            ps.setString(1, Codigo);
            ps.setString(2, Nombre);
            ps.setString(3, Precio);
            ps.setString(4, Cantidad);

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "se a agregado correctamente");
                nombre.setText("");
                codigo.setText("");
                precio.setText("");
                cantidad.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el producto: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminar(String Codigo) {
        // Credenciales de la base de datos
        String url = "jdbc:mysql://localhost:3306/tequilera";
        String usuario = "root";
        String contraseña = "";

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            // Establecer la conexión a la base de datos
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            // Consulta SQL para eliminar la palabra
            String query = "DELETE FROM productos WHERE Codigo= ?";
            // Preparar la declaración
            ps = conexion.prepareStatement(query);
            ps.setString(1, Codigo);  // Establecemos la palabra que queremos eliminar
            // Ejecutar la actualización
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "el Producto no se encontró en la base de datos.");
            }
        } catch (SQLException e) {
            // Manejar errores de la base de datos
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizarp(String Codigo, String Nombre, String Precio, String Cantidad) {
        String url = "jdbc:mysql://localhost:3306/tequilera";
        String usuario = "root";
        String contraseña = "";

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            String query = "UPDATE productos set Codigo = ?,Precio = ?,Cantidad =? where Nombre =? ";
            ps = conexion.prepareStatement(query);
            ps.setString(4, Nombre);
            ps.setString(1, Codigo);
            ps.setString(2, Precio);
            ps.setString(3, Cantidad);
            ps.executeUpdate();
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "el producto no se encontró en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e.getMessage());
        } finally {
            // Cerrar la conexión y el PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrar() {
        String url = "jdbc:mysql://localhost:3306/tequilera";
        String usuario = "root";
        String contraseña = "";

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String[] columnNames = {"Codigo", "Nombre", "Precio", "Cantidad"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            String sql = "SELECT Codigo, Nombre, Precio, Cantidad FROM productos";
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            // Clear the existing rows from the model (if any)
            model.setRowCount(0);

            // Add rows to the model
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("Codigo");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getDouble("Precio");
                fila[3] = rs.getInt("Cantidad");
                model.addRow(fila);
            }

            // Set the model of the JTable to reflect the data
            tabla.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + ex.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void consultar() {

        String url = "jdbc:mysql://localhost:3306/tequilera";
        String usuario = "root";
        String contraseña = "";

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String campo = texto1.getText();
        String where = "";
        if (!"".equals(campo)) {
            where = "WHERE Nombre OR Codigo = '" + campo + "'";
        }

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            DefaultTableModel modelo = new DefaultTableModel();
            tabla.setModel(modelo);

            String sql = "SELECT Codigo,Nombre,Precio,Cantidad FROM productos " + where;
            System.out.println(sql);

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre ");
            modelo.addColumn("Precio ");
            modelo.addColumn("Cantidad");

            int[] anchos = {200, 200, 200, 200,};
            for (int x = 0; x < cantidadColumnas; x++) {
                tabla.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ingresar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        precio = new javax.swing.JTextField();
        cantidad = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        nombre = new javax.swing.JTextField();
        consultar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        texto1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        eliminar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        codigoe = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        actualizar = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        nombrea = new javax.swing.JTextField();
        codigoa = new javax.swing.JTextField();
        precioa = new javax.swing.JTextField();
        cantidada = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        jCheckBox1.setText("jCheckBox1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(16, 68, 49));

        jButton1.setBackground(new java.awt.Color(40, 161, 108));
        jButton1.setForeground(new java.awt.Color(20, 104, 71));
        jButton1.setText("ingresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(40, 161, 108));
        jButton3.setForeground(new java.awt.Color(20, 104, 71));
        jButton3.setText("eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(40, 161, 108));
        jButton4.setForeground(new java.awt.Color(20, 104, 71));
        jButton4.setText("actualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(40, 161, 108));
        jButton5.setForeground(new java.awt.Color(20, 104, 71));
        jButton5.setText("consultar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 500, 110));

        jTabbedPane1.setBackground(new java.awt.Color(16, 68, 49));

        ingresar.setBackground(new java.awt.Color(8, 38, 28));

        jLabel2.setBackground(new java.awt.Color(178, 200, 201));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INFORMACIO DEL TEQUILA");

        jLabel3.setBackground(new java.awt.Color(127, 214, 170));
        jLabel3.setText("NOMBRE:");

        jLabel5.setBackground(new java.awt.Color(127, 214, 170));
        jLabel5.setText("CODIGO:");

        jLabel6.setBackground(new java.awt.Color(127, 214, 170));
        jLabel6.setText("PRECIO:");

        jLabel7.setBackground(new java.awt.Color(127, 214, 170));
        jLabel7.setText("CANTIDAD:");

        codigo.setBackground(new java.awt.Color(16, 68, 49));
        codigo.setForeground(new java.awt.Color(238, 251, 244));

        precio.setBackground(new java.awt.Color(16, 68, 49));
        precio.setForeground(new java.awt.Color(238, 251, 244));
        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });

        cantidad.setBackground(new java.awt.Color(16, 68, 49));
        cantidad.setForeground(new java.awt.Color(238, 251, 244));
        cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(23, 117, 78));
        jButton9.setForeground(new java.awt.Color(127, 214, 170));
        jButton9.setText("INGRESAR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        nombre.setBackground(new java.awt.Color(16, 68, 49));
        nombre.setForeground(new java.awt.Color(238, 251, 244));
        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ingresarLayout = new javax.swing.GroupLayout(ingresar);
        ingresar.setLayout(ingresarLayout);
        ingresarLayout.setHorizontalGroup(
            ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresarLayout.createSequentialGroup()
                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ingresarLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ingresarLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ingresarLayout.createSequentialGroup()
                                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ingresarLayout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(18, 18, 18))
                                        .addGroup(ingresarLayout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(22, 22, 22)))
                                    .addGroup(ingresarLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(27, 27, 27)))
                                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(precio, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(codigo)
                                    .addComponent(nombre, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addGroup(ingresarLayout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jButton9))
                    .addGroup(ingresarLayout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel2)))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        ingresarLayout.setVerticalGroup(
            ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", ingresar);

        consultar.setBackground(new java.awt.Color(8, 38, 28));

        jLabel8.setBackground(new java.awt.Color(127, 214, 170));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CODIGO DEL TEQUILA:");

        texto1.setBackground(new java.awt.Color(16, 68, 49));
        texto1.setForeground(new java.awt.Color(238, 251, 244));

        jLabel9.setBackground(new java.awt.Color(178, 200, 201));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("INFORMACION DEL TEQUILA");

        jButton6.setBackground(new java.awt.Color(23, 117, 78));
        jButton6.setForeground(new java.awt.Color(127, 214, 170));
        jButton6.setText("BUSCAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(23, 117, 78));
        jButton10.setForeground(new java.awt.Color(127, 214, 170));
        jButton10.setText("VER PRODUCTOS");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        tabla.setBackground(new java.awt.Color(16, 68, 49));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Precio", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout consultarLayout = new javax.swing.GroupLayout(consultar);
        consultar.setLayout(consultarLayout);
        consultarLayout.setHorizontalGroup(
            consultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultarLayout.createSequentialGroup()
                .addGroup(consultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(consultarLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel9))
                    .addGroup(consultarLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(consultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(consultarLayout.createSequentialGroup()
                                .addComponent(texto1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6))
                            .addComponent(jButton10))))
                .addContainerGap(77, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        consultarLayout.setVerticalGroup(
            consultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(consultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(texto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", consultar);

        eliminar.setBackground(new java.awt.Color(8, 38, 28));

        jLabel10.setBackground(new java.awt.Color(178, 200, 201));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("DATO DEL TEQUILA A ELIMINAR");

        jLabel11.setBackground(new java.awt.Color(127, 214, 170));
        jLabel11.setText("CODIGO:");

        codigoe.setBackground(new java.awt.Color(16, 68, 49));
        codigoe.setForeground(new java.awt.Color(238, 251, 244));
        codigoe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoeActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(23, 117, 78));
        jButton7.setForeground(new java.awt.Color(127, 214, 170));
        jButton7.setText("ELIMINAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eliminarLayout = new javax.swing.GroupLayout(eliminar);
        eliminar.setLayout(eliminarLayout);
        eliminarLayout.setHorizontalGroup(
            eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eliminarLayout.createSequentialGroup()
                .addGroup(eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eliminarLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel10))
                    .addGroup(eliminarLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(codigoe, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(eliminarLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jButton7)))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        eliminarLayout.setVerticalGroup(
            eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eliminarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(eliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(codigoe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", eliminar);

        actualizar.setBackground(new java.awt.Color(8, 38, 28));

        jLabel12.setBackground(new java.awt.Color(178, 200, 201));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ACTUALIZAR DATOS DEL TEQUILA");

        jLabel13.setBackground(new java.awt.Color(127, 214, 170));
        jLabel13.setText("NOMBRE:");

        jLabel14.setBackground(new java.awt.Color(127, 214, 170));
        jLabel14.setText("NUEVO CODIGO:");

        jLabel15.setBackground(new java.awt.Color(127, 214, 170));
        jLabel15.setText("NUEVO PRECIO:");

        jLabel16.setBackground(new java.awt.Color(127, 214, 170));
        jLabel16.setText("CANTIDADES:");

        nombrea.setBackground(new java.awt.Color(16, 68, 49));
        nombrea.setForeground(new java.awt.Color(238, 251, 244));
        nombrea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreaActionPerformed(evt);
            }
        });

        codigoa.setBackground(new java.awt.Color(16, 68, 49));
        codigoa.setForeground(new java.awt.Color(238, 251, 244));

        precioa.setBackground(new java.awt.Color(16, 68, 49));
        precioa.setForeground(new java.awt.Color(238, 251, 244));
        precioa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioaActionPerformed(evt);
            }
        });

        cantidada.setBackground(new java.awt.Color(16, 68, 49));
        cantidada.setForeground(new java.awt.Color(238, 251, 244));

        jButton8.setBackground(new java.awt.Color(23, 117, 78));
        jButton8.setForeground(new java.awt.Color(127, 214, 170));
        jButton8.setText("ACTUALIZAR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actualizarLayout = new javax.swing.GroupLayout(actualizar);
        actualizar.setLayout(actualizarLayout);
        actualizarLayout.setHorizontalGroup(
            actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizarLayout.createSequentialGroup()
                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actualizarLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel12))
                    .addGroup(actualizarLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel13)
                        .addGap(31, 31, 31)
                        .addComponent(nombrea, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(actualizarLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(actualizarLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codigoa))
                            .addGroup(actualizarLayout.createSequentialGroup()
                                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton8)
                                    .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(precioa, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                        .addComponent(cantidada)))))))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        actualizarLayout.setVerticalGroup(
            actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(nombrea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(codigoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(precioa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cantidada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", actualizar);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 500, 300));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/usuarios/men01.PNG"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 170, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (evt.getSource() == jButton1) {
            jTabbedPane1.setSelectedIndex(0);
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (evt.getSource() == jButton3) {
            jTabbedPane1.setSelectedIndex(2);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (evt.getSource() == jButton5) {
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (evt.getSource() == jButton4) {
            jTabbedPane1.setSelectedIndex(3);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadActionPerformed

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String Codigo = codigoe.getText();
        if (Codigo != null && !Codigo.isEmpty()) {
            eliminar(Codigo);
        } else {
            JOptionPane.showMessageDialog(null, "El codigo no existe");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String Codigo = codigoa.getText();
        String Nombre = nombrea.getText();
        String Precio = precioa.getText();
        String Cantidad = cantidada.getText();
        if (Nombre != null && !Nombre.isEmpty() && Codigo != null && !Codigo.isEmpty() && Precio != null && !Precio.isEmpty() && Cantidad != null && !Cantidad.isEmpty()) {
            actualizarp(Codigo, Nombre, Precio, Cantidad);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese una palabra válida.");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String Codigo = codigo.getText();
        String Nombre = nombre.getText();
        String Precio = precio.getText();
        String Cantidad = cantidad.getText();
        if (Codigo != null && !Codigo.isEmpty() && Nombre != null && !Nombre.isEmpty() && Precio != null && !Precio.isEmpty() && Cantidad != null && !Cantidad.isEmpty()) {
            agregar(Codigo, Nombre, Precio, Cantidad);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese todo los campos.");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void nombreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreaActionPerformed

    private void codigoeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoeActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        mostrar();

    }//GEN-LAST:event_jButton10ActionPerformed

    private void precioaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        consultar();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizar;
    private javax.swing.JTextField cantidad;
    private javax.swing.JTextField cantidada;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField codigoa;
    private javax.swing.JTextField codigoe;
    private javax.swing.JPanel consultar;
    private javax.swing.JPanel eliminar;
    private javax.swing.JPanel ingresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrea;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField precioa;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField texto1;
    // End of variables declaration//GEN-END:variables
}
