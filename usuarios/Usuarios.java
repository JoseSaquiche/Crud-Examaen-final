
package usuarios;

public class Usuarios{
    private String Correo;
    private String Contraseña;

    public Usuarios() {
    }

    public Usuarios(String Correo, String Contraseña) {
        this.Correo = Correo;
        this.Contraseña = Contraseña;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

}   