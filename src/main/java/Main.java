import controller.CtrlTDC;
import controller.CtrlUsuario;
import model.Sesion;
import model.Usuario;
import view.InicioSesion;
import view.RegistroUsuario;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        InicioSesion sesion = new InicioSesion();
        sesion.mostrarInicio();
    }
}
