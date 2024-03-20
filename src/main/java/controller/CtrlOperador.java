package controller;
import model.Log;
import model.Operador;
import model.Sesion;
import java.sql.SQLException;
import static model.Operador.operadorExiste;

public class CtrlOperador {
    private static Operador operador;
    public Sesion sesion;


    public boolean iniciarSesion(String correoElectronico, String password) {
        try {
            if (validarEmail(correoElectronico)) {
                if (validarPassword(password)) {
                    sesion = Sesion._instance();
                    sesion.setOperador(operador); //SESION ACTIVA
                    Log.success("Inicio de sesión exitoso");
                    Log.trace("Sesion activa: " + sesion.getOperador().getCorreoElectronico());
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public boolean validarEmail(String correoElectronico) throws SQLException {
        if (operadorExiste(correoElectronico)) {
            int idOperador = Operador.obtenerIdOperador(correoElectronico);
            operador = new Operador(idOperador);
            Log.trace("Usuario a validar: " + operador.getCorreoElectronico());
            Log.info("Correo valido");
            return true;
        }
        Log.error("Correo no registrado");
        return false;
    }


    public boolean validarPassword(String password) throws SQLException {
        if (operador.getPassword().equals(password)){
            Log.info("Contraseña valida");
            return true;
        }
        operador = null; // Si no se puede validar la contraseña en usuario vuelve a null
        Log.error("Contraseña incorrecta");
        Log.trace("Usuario vuelve a null");
        return false;
    }

}