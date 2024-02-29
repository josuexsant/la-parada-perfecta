import controller.CtrlUsuario;
import model.CreateConnection;
import model.Usuario;
import view.InicioSesion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        InicioSesion sesion = new InicioSesion();
        sesion.mostrarInicio();
    }
}
