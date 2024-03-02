package model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int id;
    private int idAutomovil;
    private Date fecha;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private int idCajon;
    private int idUsuario;
    private static CreateConnection createConn = new CreateConnection();

    public Reserva(int id, int idAutomovil, java.util.Date fecha, java.util.Date fechaInicio, java.util.Date fechaFin, int idCajon, int idUsuario) {
        this.id = id;
        this.idAutomovil = idAutomovil;
        this.fecha = new Date(fecha.getTime());
        this.fechaInicio = new Timestamp(fechaInicio.getTime());
        this.fechaFin = new Timestamp(fechaFin.getTime());
        this.idCajon = idCajon;
        this.idUsuario = idUsuario;
    }

    /**
     * @author: Fernando Quiroz
     * Con esta función deberia ser capaz de mostrar todas las placas del usuario en la interfaz
     * @return
     */
    public List<String> mostrarPlacasPorIdUsuario(int idUsuario) throws SQLException {
        List<String> placas = new ArrayList<>();
        String query = "SELECT placa FROM automoviles WHERE id_usuario = ?";

        try (Connection conn = createConn.getConnection();
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
    public boolean guardarReserva() throws SQLException {
        Connection conn = createConn.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO reservaciones (id_automovil, fecha, fecha_inicio, fecha_fin, id_cajon, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idAutomovil);
            stmt.setDate(2, new java.sql.Date(fecha.getTime()));
            stmt.setTimestamp(3, fechaInicio);
            stmt.setTimestamp(4, fechaFin);
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
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
