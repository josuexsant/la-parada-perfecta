package controller;

import model.CreateConnection;
import model.Log;
import model.Sesion;
import model.Usuario;
import java.sql.SQLException;

import static model.Usuario.usuarioExiste;

public class CtrlUsuario {
    private static Usuario usuario;

    public boolean registrarUsuario(String nombre, String password, String apellidoPaterno, String apellidoMaterno, String numeroTelefono, String correoElectronico, int idGenero, int idCiudad) throws SQLException {
        Usuario p = new Usuario(nombre, password, apellidoPaterno, apellidoPaterno, numeroTelefono, correoElectronico, idGenero, idCiudad);

        if(usuarioExiste(correoElectronico)){
            Log.warn("El correo electronico ya esta registado");
        }else {

            p.setNombre(nombre);
            p.setPassword(password);
            p.setApellidoPaterno(apellidoPaterno);
            p.setApellidoMaterno(apellidoMaterno);
            p.setNumeroTelefono(numeroTelefono);
            p.setIdGenero(idGenero);
            p.setIdCiudad(idCiudad);
            p.setCorreoElectronico(correoElectronico);

            try {
                if (Usuario.registrar(p)) {
                    Log.success("Registro de usuario exitoso");
                    return true;
                } else {
                    Log.error("Registro de usuario fallido");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean iniciarSesion(String correoElectronico, String password){
        try {
            if(validarCorreoElectronico(correoElectronico)){
                if(validarPassword(password)){
                    Log.success("Inicio de sesión exitoso");
                    return true;
                }else {
                    Log.error("Contraseña incorrecta");
                }
            }else {
                Log.error("Correo electrónico no válido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean validarCorreoElectronico(String correoElectronico) throws SQLException{
        if (usuarioExiste(correoElectronico)){
            int idUsuario = Usuario.obtenerIdUsuario(correoElectronico);
            usuario = new Usuario(idUsuario);
            Sesion sesion = Sesion._instance();
            sesion.setUsuario(usuario);
            Log.trace("Sesion activa: " + sesion.getUsuario().getCorreoElectronico());
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
