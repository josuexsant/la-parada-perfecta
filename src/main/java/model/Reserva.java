package model;

import java.sql.*;
import java.util.LinkedList;

public class Reserva {
    private int id;
    private int idAutomovil;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private int idCajon;
    private int idUsuario;
    private static DBManager dbManager;

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
        try {
            Connection conn = dbManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idReserva);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.idAutomovil = resultSet.getInt("id_automovil");
                this.fecha = String.valueOf(resultSet.getDate("fecha"));
                this.horaInicio = String.valueOf(resultSet.getTimestamp("fecha_inicio"));
                this.horaFin = String.valueOf(resultSet.getTimestamp("fecha_fin"));
                this.idCajon = resultSet.getInt("id_cajon");
                this.idUsuario = resultSet.getInt("id_usuario");
            }
        } catch (SQLException e) {
            Log.error(e.getMessage());
        }
    }

    public static LinkedList<Reserva> getReservas(int id_Usuario) {
        String query = "SELECT * FROM reservaciones WHERE id_usuario = ?";
        LinkedList<Reserva> reservaciones = new LinkedList<>();
        try {
            dbManager = new DBManager();
            Connection conn = dbManager.getConnection();
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

                Reserva reserva = new Reserva(idReserva, idAutomovil, fecha, fechaInicio, fechaFin, idCajon, id_Usuario);
                reservaciones.add(reserva);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservaciones;
    }

    /**
     * @author: Fernando Quiroz
     * Este metodo guarda un objeto Reserva dentro de la base de datos
     * @return: true si se logro hacer el INSERT y false si no se logro hacer.
     */
    public boolean guardar() {
        try {
            dbManager = new DBManager();
            Connection conn = dbManager.getConnection();
            PreparedStatement stmt;
            ResultSet generatedKeys;

            String query = "INSERT INTO reservaciones (id_automovil, fecha, fecha_inicio, fecha_fin, id_cajon, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idAutomovil);
            stmt.setString(2, fecha);
            stmt.setString(3, horaInicio);
            stmt.setString(4, horaFin);
            stmt.setInt(5, idCajon);
            stmt.setInt(6, idUsuario);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0)
                Log.error("No se guardo la reserva");

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next())
                id = generatedKeys.getInt(1);

            Log.success("Se guardo la reserva con el ID: " + this.id);
            conn.close();
            return true; // Se pudo guardar la reserva exitosamente
        } catch (SQLException e) {
            Log.error(e.getMessage());
            return false; // Hubo un error al guardar la reserva
        }
    }

    public static boolean eliminar(int idReserva) {
        dbManager = new DBManager();
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
            dbManager = new DBManager();
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

    public void modificar(int idUsuario, int idReserva) {
        String query = "UPDATE reservaciones " +
                "SET id_automovil = ?, " +
                "fecha = ?, " +
                "fecha_inicio = ?, " +
                "fecha_fin = ?, " +
                "id_cajon = ? " +
                "WHERE id_usuario = ? AND id = ?";
        try {
            dbManager = new DBManager();
            PreparedStatement pstmt;
            Connection conn = dbManager.getConnection();

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idAutomovil);
            pstmt.setString(2, fecha);
            pstmt.setString(3, horaInicio);
            pstmt.setString(4, horaFin);
            pstmt.setInt(5, idCajon);
            pstmt.setInt(6, idUsuario);
            pstmt.setInt(7, idReserva);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            Log.error(ex.getMessage());
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
