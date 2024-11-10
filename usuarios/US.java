package usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US {
    String url = "jdbc:mysql://localhost:3306/tequilera";  
    String usuario = "root";  
    String contraseña = ""; 

    Connection conexion = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Usuarios log(String Correo, String Contraseña) {
        Usuarios U = null; // Inicializamos en null para identificar si no se encontró
        String sql = "SELECT * FROM usuario WHERE Correo = ? AND Contraseña = ?";
        try {
            // Establecemos la conexión
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            // Inicializamos el PreparedStatement con la consulta SQL
            ps = conexion.prepareStatement(sql);  // <-- Aquí se inicializa `ps`
            
            // Asignamos los valores a los parámetros de la consulta
            ps.setString(1, Correo);
            ps.setString(2, Contraseña);
            // Ejecutamos la consulta
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // Si encontramos un registro, creamos un objeto Usuarios y lo llenamos
                U = new Usuarios();
                U.setCorreo(rs.getString("Correo")); // Columna "Correo" debe coincidir con el nombre en BD
                U.setContraseña(rs.getString("Contraseña")); // Columna "Contraseña" en BD
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Imprimir errores para diagnóstico
        } finally {
            // Cerrar recursos en el bloque finally para evitar fugas de recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return U; // Retornará null si no se encontró ningún usuario
    }
}
