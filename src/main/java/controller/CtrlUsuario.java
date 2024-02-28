package controller;

import model.CreateConnection;
import model.Usuario;
import java.sql.SQLException;

public class CtrlUsuario {
    private Usuario usuario;
    private final CreateConnection createConn = new CreateConnection();

    public boolean iniciarSesion(String correoElectronico, String password){
        try {
            if(validarCorreoElectronico(correoElectronico)){
                if(validarPassword(password)){
                    System.out.println("Inicio de sesión exitoso");
                    return true;
                }else {
                    System.out.println("Contraseña incorrecta");
                }
            }else {
                System.out.println("Correo electrónico no válido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean validarCorreoElectronico(String correoElectronico) throws SQLException{
        if (Usuario.usuarioExiste(correoElectronico)){
            int idUsuario = Usuario.obtenerIdUsuario(correoElectronico);
            usuario = new Usuario(2);
            return true;
        }
        return false;
    }
    public boolean validarPassword(String password) throws SQLException{
        if (usuario.getPassword().equals(password))
            return true;
        return false;
    }

    public Usuario getUsuario(){
        return usuario;
    }
}
