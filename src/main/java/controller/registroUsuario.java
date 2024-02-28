package controller;

import model.CreateConnection;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class registroUsuario{

    public boolean registrar(Usuario usr) throws SQLException {

        CreateConnection createConn = new CreateConnection();
        try (Connection conn = createConn.getConnection()) {
            String sql;

            sql = "INSERT INTO informacion_usuario (nombre, password, apellido_paterno, apellido_materno, numero_telefono, correo_electronico, id_genero, id_ciudades) VALUES(1,2,3,4,5,6,7,8)";

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, usr.getNombre());
                ps.setString(2, usr.getPassword());
                ps.setString(3, usr.getApellidoPaterno());
                ps.setString(4, usr.getApellidoMaterno());
                ps.setInt(5, usr.getNumeroTelefono());
                ps.setString(6, usr.getCorreoElectronico());
                ps.setInt(7, usr.getIdGenero());
                ps.setInt(8, usr.getIdCiudad());
                ps.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(registroUsuario.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    }


}
