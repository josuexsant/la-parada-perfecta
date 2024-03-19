package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Operador {
    private int id;
    private String correoElectronico;
    private String password;
    private static DBManager dbManager = new DBManager();

    public Operador(String password, String correoElectronico) {
        this.password = password;
        this.correoElectronico = correoElectronico;

    }

    public Operador(){

    }

    public Operador(int idOperador) {
        String query = "SELECT " +
                "    id, " +
                "    password, " +
                "    correo_electronico " +  // Se agregó un espacio aquí
                "FROM " +
                "    Operador " +            // Se agregó un espacio aquí
                "WHERE " +
                "    id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idOperador);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.password = resultSet.getString("password");
                    this.correoElectronico = resultSet.getString("correo_electronico");
                } else {
                    // Manejar el caso en el que no se encuentra ningún operador con el id proporcionado
                    throw new IllegalArgumentException("No se encontró ningún operador con el ID proporcionado: " + idOperador);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción de manera adecuada según las necesidades de tu aplicación
            throw new RuntimeException("Error al acceder a la base de datos", e);
        }
    }


    public static boolean operadorExiste(String correoElectronico) throws SQLException {
        String query = "SELECT id FROM Operador WHERE correo_electronico = ?";
        Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, correoElectronico);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }


    public static int obtenerIdOperador(String correoElectronico) throws SQLException {
        String query = "SELECT id FROM Operador WHERE correo_electronico = ?";
        Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, correoElectronico);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            // Devolver un valor negativo (por ejemplo, -1) si no se encontró el usuario
            return -1;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


}