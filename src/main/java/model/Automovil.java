package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Automovil {
    private static int id;
    private int idUsuario;
    private int idMarca;
    private static String placa;
    private static DBManager dbManager = new DBManager();

    public Automovil() {
    }

    public Automovil(int idUsuario, int idMarca, String placa) {

        this.idUsuario = idUsuario;
        this.idMarca = idMarca;
        this.placa = placa;
    }

    public Automovil(int idAuto) {
        String query = "SELECT * " + "FROM automoviles " + "WHERE id = ?";
        try (Connection conn = dbManager.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idAuto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.idUsuario = resultSet.getInt("id_usuario");
                    this.idMarca = resultSet.getInt("id_marca");
                    this.placa = resultSet.getString("placa");
                    Log.debug("Se cargó correctamente el objeto automóvil");
                } else {
                    Log.debug("No se encontró ningún automóvil para el usuario con ID: " + idAuto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static LinkedList<String> getPlacas(int id_Usuario) {
        LinkedList<String> placas = new LinkedList<>();
        try {
            Connection conn = dbManager.getConnection();
            String query = "SELECT placa FROM automoviles WHERE id_usuario = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id_Usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String placa = rs.getString("placa");
                placas.add(placa);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return placas;
    }

        public void modificarPlaca() {
            String query = "UPDATE automoviles " + "SET id_marca = ?, " + "placa = ? " + "WHERE id = ?";
            try {
                Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, idMarca);
                pstmt.setString(2, placa);
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    public boolean guardarAutomovil() throws SQLException {
        Connection conn = dbManager.getConnection();
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

    public static boolean eliminarMatricula(String placa) {
        try (Connection conn = dbManager.getConnection()) {
            String query = "DELETE FROM automoviles WHERE placa = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, placa);

                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Matrícula eliminada con éxito.");
                    return true;
                } else {
                    System.out.println("No se encontró ninguna matrícula con la placa especificada.");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la matrícula.", e);
        }
    }

    public void guardarAutomovil(int idUsuario, int idMarca, String placa) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dbManager.getConnection();
            String query = "INSERT INTO automoviles (id_usuario, id_marca, placa) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idUsuario);
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
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getIdConMatricula(String placa) {
        Connection conn;
        PreparedStatement stmt;
        ResultSet rs;
        int idAutomovil = -1;
        try {
            conn = dbManager.getConnection();
            String query = "SELECT id FROM automoviles WHERE placa = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, placa);
            rs = stmt.executeQuery();
            if (rs.next())
                idAutomovil = rs.getInt("id");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAutomovil;
    }

    public static String obtenerMarca(int idMarca) throws SQLException {
        String marca = null;
        Connection conn = dbManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT m.nombre AS marca FROM automoviles a JOIN marcas m ON a.id_marca = m.id WHERE a.id_marca = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMarca);
            rs = stmt.executeQuery();
            if (rs.next()) {
                marca = rs.getString("marca");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marca;
    }

    public static LinkedList<String> obtenerMarcas() {
        LinkedList<String> marcas = new LinkedList<>();
        try {
            Connection conn = dbManager.getConnection();
            String query = "SELECT nombre FROM marcas";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String marca = rs.getString("nombre");
                marcas.add(marca);
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return marcas;
    }

    public static int getId() {
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

    public static String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
