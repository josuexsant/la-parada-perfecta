package model;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Reserva {
    private int id;
    private int idAutomovil;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private int idCajon;
    private int idUsuario;
    private static CreateConnection createConn = new CreateConnection();

    public Reserva(int id, int idAutomovil, String fecha, String fechaInicio, String fechaFin, int idCajon, int idUsuario) {
        this.id = id;
        this.idAutomovil = idAutomovil;
        this.fecha = fecha;
        this.horaInicio = fechaInicio;
        this.horaFin = fechaFin;
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

    public boolean esFusionable(Reserva otraReserva){
        if (this.idUsuario != otraReserva.getIdUsuario()){
            return false;
        }
        if (this.idAutomovil != otraReserva.getIdAutomovil()){
            return false;
        }

        if (seSuperpone(this,otraReserva)){
            fusionarReserva(otraReserva);
            return true;
        }
        return false;
    }

    private boolean seSuperpone(Reserva reserva1,Reserva reserva2){
        Timestamp inicio1 = Timestamp.valueOf(reserva1.getFecha() + " " + reserva1.getHoraInicio());
        Timestamp fin1 =   Timestamp.valueOf(reserva1.getFecha() + " " + reserva1.getHoraFin());
        Timestamp inicio2 = Timestamp.valueOf(reserva2.getFecha() + " " + reserva2.getHoraInicio());
        Timestamp fin2 = Timestamp.valueOf(reserva2.getFecha() + " " + reserva2.getHoraFin());

        return inicio1.before(fin2) && inicio2.before(fin1);
    }


    private void fusionarReserva(Reserva otraReserva){
        Timestamp nuevaHoraInicio = Timestamp.valueOf(this.getFecha() + " " + this.getHoraInicio());
        Timestamp nuevaHoraFin = Timestamp.valueOf(this.getFecha() + " " + otraReserva.getHoraInicio());

        CreateConnection createConnection = new CreateConnection();

        try (Connection conn = createConnection.getConnection()) {
            String sql = "UPDATE reservaciones SET fecha_inicio = ?, fecha_fin = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setTimestamp(1,nuevaHoraInicio);
                pstmt.setTimestamp(2,nuevaHoraFin);
                pstmt.setInt(3,this.getId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    Log.success("la reserva se fusiono extosamente.");
                }else {
                    Log.error("La reserva no se pudo fusionar.");
                }



            }
        }catch (SQLException e){
            e.printStackTrace();
            Log.error("Error al fusionar reservas");

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
