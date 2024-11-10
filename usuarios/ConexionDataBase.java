
package usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDataBase {
     private static final String URL = "jdbc:mysql://localhost:3306/tequilera";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection conectar(){
        Connection conexion = null;
        try{
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("conexion exitosa");
        }catch (SQLException e) {
        }
        return conexion;
    }
}
