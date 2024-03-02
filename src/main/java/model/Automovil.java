package model;

import java.sql.*;

public class Automovil {
    private int id;
    private int idUsuario;
    private int idMarca;
    private String placa;
    private CreateConnection createConn = new CreateConnection();

    public Automovil(int idUsuario, int idMarca, String placa) {

        this.idUsuario = idUsuario;
        this.idMarca = idMarca;
        this.placa = placa;
    }

    public Automovil(int idUsuario) {
        String query = "SELECT" +
                "id," +
                "id_usuario," +
                "id_marca," +
                "placa" +
                "FROM automoviles" +
                "WHERE id_usuario = ?";
        try (Connection conn = createConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.idUsuario = resultSet.getInt("id_usuario");
                    this.idMarca = resultSet.getInt("id_marca");
                    this.placa = resultSet.getString("placa");
                    this.id = resultSet.getInt("id");
                    Log.debug("Se cargo correctamente el objeto automovil");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean guardarAutomovil() throws SQLException {
        Connection conn = createConn.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO automoviles (id_usuario, id_marca, placa) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idUsuario); // Suponiendo que getId() devuelve el id del usuario
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
            return true; // Se pudo guardar el automóvil exitosamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Hubo un error al guardar el automóvil
        } finally {
            conn.close();
        }
    }


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
