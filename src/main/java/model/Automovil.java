package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Automovil {
    private int id;
    private int idUsuario;
    private int idMarca;
    private String placa;

    private static CreateConnection createConn = new CreateConnection();

    // Constructor
    public Automovil(int id, int idUsuario, int idMarca, String placa) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idMarca = idMarca;
        this.placa = placa;
    }
        public void guardarAutomovil() {
            try (Connection conn = createConn.getConnection()) {
                String query = "INSERT INTO automoviles (id_usuario, id_marca, placa) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, idUsuario);
                    pstmt.setInt(2, idMarca);
                    pstmt.setString(3, placa);
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    // Getters y setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }



}
