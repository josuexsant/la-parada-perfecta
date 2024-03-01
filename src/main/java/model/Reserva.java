package model;
import controller.CtrlAutomovil;
import controller.CtrlUsuario;

import java.sql.Date;
import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Reserva(int id, int idAutomovil, Date fecha, Timestamp fechaInicio, Timestamp fechaFin, int idCajon, int idUsuario) {
        this.id = id;
        this.idAutomovil = idAutomovil;
        this.fecha = fecha;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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
     * Con esta función deberia ser capaz de crear la reserva
     * @return
     */
    public boolean guardarReserva(Date fecha, Timestamp fechaInicio, Timestamp fechaFin, String placa) throws SQLException {
        String query = "INSERT INTO Reservas (fecha_inicio, fecha_fin, fecha) VALUES (?, ?, ?)";
        Connection conn = createConn.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setTimestamp(1, fechaInicio);
        pstmt.setTimestamp(2, fechaFin);
        pstmt.setDate(3, fecha);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
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
