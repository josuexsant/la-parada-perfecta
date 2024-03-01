package model;

import java.sql.*;

public class Automovil {
    private int id;
    private Usuario usuario;
    private int idMarca;
    private String placa;

    private CreateConnection createConn = new CreateConnection();

    // Constructor
    public Automovil(int id, Usuario usuario, int idMarca, String placa) {
        usuario = Sesion._instance().getUsuario();
        this.id = id;
        this.usuario = usuario;
        this.idMarca = idMarca;
        this.placa = placa;
    }

    public void guardarAutomovil() throws SQLException {
        Connection conn = createConn.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO automoviles (id_usuario, id_marca, placa) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, usuario.getId()); // Suponiendo que getId() devuelve el id del usuario
            stmt.setInt(2, idMarca);
            stmt.setString(3, placa);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating automovil failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating automovil failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }






    // Getters y setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
