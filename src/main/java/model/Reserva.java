package model;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Reserva {
    private int id;
    private int idAutomovil;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private int idCajon;
    private int idUsuario;
    private static DBManager dbManager = new DBManager();

    public Reserva(int id, int idAutomovil, String fecha, String fechaInicio, String fechaFin, int idCajon, int idUsuario) {
        this.id = id;
        this.idAutomovil = idAutomovil;
        this.fecha = fecha;
        this.horaInicio = fechaInicio;
        this.horaFin = fechaFin;
        this.idCajon = idCajon;
        this.idUsuario = idUsuario;
    }

    public Reserva(int idReserva) {
        String query = "SELECT " +
                "    r.id," +
                "    r.id_automovil, " +
                "    r.fecha, " +
                "    r.fecha_inicio, " +
                "    r.fecha_fin, " +
                "    r.id_cajon, " +
                "    r.id_usuario " +
                "FROM " +
                "    reservaciones r " +
                "    JOIN informacion_usuario iu ON r.id_usuario = iu.id " +
                "    JOIN automoviles au ON r.id_automovil = au.id " +
                "    JOIN cajones ca ON r.id_cajon = ca.id " +
                "WHERE " +
                "    r.id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idReserva);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.idAutomovil = resultSet.getInt("id_automovil");
                    this.fecha = String.valueOf(resultSet.getDate("fecha"));
                    this.horaInicio = String.valueOf(resultSet.getTimestamp("fecha_inicio"));
                    this.horaFin = String.valueOf(resultSet.getTimestamp("fecha_fin"));
                    this.idCajon = resultSet.getInt("id_cajon");
                    this.idUsuario = resultSet.getInt("id_usuario");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: Fernando Quiroz
     * Con esta función deberia ser capaz de mostrar todas las placas del usuario en la interfaz
     * @return
     */

    public List<String> mostrarPlacasPorIdUsuario(int idUsuario) throws SQLException {
        List<String> placas = new ArrayList<>();
        String query = "SELECT placa FROM automoviles WHERE id_usuario = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                placas.add(rs.getString("placa"));
            }
        }
        return placas;
    }

    /**
     * @author: Fernando Quiroz
     * Este metodo guarda un objeto Reserva dentro de la base de datos
     * @return: true si se logro hacer el INSERT y false si no se logro hacer.
     */
    public static LinkedList<Reserva> getReservas(int id_Usuario){
        LinkedList<Reserva> reservaciones = new LinkedList<>();
        try {
            Connection conn = dbManager.getConnection();
            String query = "SELECT * FROM reservaciones WHERE id_usuario = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id_Usuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idReserva = rs.getInt("id");
                int idAutomovil = rs.getInt("id_automovil");
                String fecha = rs.getString("fecha");
                String fechaInicio = rs.getString("fecha_inicio");
                String fechaFin = rs.getString("fecha_fin");
                int idCajon = rs.getInt("id_cajon");

                // Crear una instancia de Reserva con los datos obtenidos de la base de datos
                Reserva reserva = new Reserva(idReserva, idAutomovil, fecha, fechaInicio, fechaFin, idCajon, id_Usuario);

                // Agregar la reserva a la lista
                reservaciones.add(reserva);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reservaciones;
    }

    public boolean guardarReserva() throws SQLException {
        Connection conn = dbManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO reservaciones (id_automovil, fecha, fecha_inicio, fecha_fin, id_cajon, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idAutomovil);
            stmt.setString(2, fecha);
            stmt.setString(3, horaInicio);
            stmt.setString(4, horaFin);
            stmt.setInt(5, idCajon);
            stmt.setInt(6, idUsuario);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reserva failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating reserva failed, no ID obtained.");
                }
            }
            Log.success("Se guardo la reserva con el ID: " + this.id);
            return true; // Se pudo guardar la reserva exitosamente
        } catch (SQLException e) {
            e.printStackTrace();
            Log.error("No se pudo guarda la reserva");
            return false; // Hubo un error al guardar la reserva
        } finally {
            conn.close();
        }
    }

    public void guardarReservaModificada() throws SQLException{
        String query = "UPDATE reservaciones " +
                "SET id_automovil = ?, " +
                "fecha = ?, " +
                "fecha_inicio = ?, " +
                "fecha_fin = ?, " +
                "id_cajon = ?, " +
                "id_usuario = ? " +
                "WHERE id = ?";
        Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {
            pstmt.setInt(1, idAutomovil);
            pstmt.setString(2, fecha);
            pstmt.setString(3, horaInicio);
            pstmt.setString(4, horaFin);
            pstmt.setInt(5, idCajon);
            pstmt.setInt(6, idUsuario);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    public static boolean eliminarReserva(int idReserva) {
        try (Connection conn = dbManager.getConnection()) {
            String query = "DELETE FROM reservaciones WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, idReserva);

                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Reserva eliminada con éxito.");
                    return true;
                } else {
                    System.out.println("No se encontró ninguna reserva con el ID especificado.");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la reserva.", e);
        }
    }

    public boolean eliminar() {
        try {
            Connection conn = dbManager.getConnection();
            String query = "DELETE FROM reservaciones WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, this.id);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Reserva eliminada con éxito.");
                return true;
            } else {
                System.out.println("No se encontró ninguna reserva con el ID especificado.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la reserva.", e);
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(int idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdCajon() {
        return idCajon;
    }

    public void setIdCajon(int idCajon) {
        this.idCajon = idCajon;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}

