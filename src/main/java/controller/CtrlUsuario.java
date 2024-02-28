package controller;

import model.CreateConnection;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CtrlUsuario {
    private CreateConnection createConn = new CreateConnection();

    public boolean registrarUsuario(int id, String nombre, String password, String apellidoPaterno, String apellidoMaterno, int numeroTelefono, String correoElectronico, int idGenero, int idCiudad) {
        registroUsuario psql = new registroUsuario();
        Usuario p = new Usuario(id, nombre, password, apellidoPaterno, apellidoPaterno, numeroTelefono, correoElectronico, idGenero, idCiudad);

        p.setId(id);
        p.setNombre(nombre);
        p.setPassword(password);
        p.setApellidoPaterno(apellidoPaterno);
        p.setApellidoMaterno(apellidoMaterno);
        p.setNumeroTelefono(numeroTelefono);
        p.setCorreoElectronico(correoElectronico);
        p.setIdGenero(idGenero);
        p.setIdCiudad(idCiudad);

        try {
            if(psql.registrar(p)){
                System.out.println("Registro de usuario exitoso");
                return true;
            }else{
                System.out.println("Registro de usuario fallido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean iniciarSesion(String correoElectronico, String password){
        try {
            if(validarCorreoElectronico(correoElectronico)){
                if(validarPassword(correoElectronico, password)){
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
        String query = "SELECT id from informacion_usuario WHERE correo_electronico =" + "?";
        Connection conn = createConn.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, correoElectronico);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
    public boolean validarPassword(String correoElectronico, String password) throws SQLException{
        String query = "SELECT id_usuario FROM passwords JOIN informacion_usuario ON passwords.id_usuario = informacion_usuario.id WHERE informacion_usuario.correo_electronico = ? AND passwords.password = ?";
        Connection conn = createConn.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, correoElectronico);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}
