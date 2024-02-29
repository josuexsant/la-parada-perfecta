package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Cajon {
    private int id;
    private int piso;
    private Estado estado;
    private static CreateConnection createConn = new CreateConnection();

    // Constructor
    public Cajon(int id, int piso, Estado estado) {
        this.id = id;
        this.piso = piso;
        this.estado = estado;
    }

    public void guardarCajon() throws SQLException {
            Connection conn = createConn.getConnection();
            String query = "INSERT INTO cajones (id, piso, estado) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setInt(2, piso);
            pstmt.setString(3, estado.name()); // Guarda el nombre del enum en lugar del valor ordinal
            pstmt.executeUpdate();
    }

    public static LinkedList<Cajon> getLugaresDisponibles(){
        LinkedList<Cajon> cajonesDisponibles = new LinkedList<>();
        try {
            Connection conn = createConn.getConnection();
            String query = "SELECT id, piso, estado FROM cajones WHERE estado = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, Estado.DISPONIBLE.name()); // Filtra los cajones disponibles
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int piso = rs.getInt("piso");
                Estado estado = Estado.valueOf(rs.getString("estado")); // Convierte el nombre del enum a enum
                Cajon cajon = new Cajon(id, piso, estado);
                cajonesDisponibles.add(cajon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajonesDisponibles;
    }


    public void setPiso(int piso) throws SQLException {
        try (Connection conn = createConn.getConnection()) {
            String query = "UPDATE cajones SET piso = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, piso);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                this.piso = piso;
            }
        }
    }

    public void setEstado(Estado estado) throws SQLException {
        try (Connection conn = createConn.getConnection()) {
            String query = "UPDATE cajones SET estado = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, estado.name());
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                this.estado = estado;
            }
        }
    }

    public int getId() {
        return id;
    }

    public int getPiso() {
        try (Connection conn = createConn.getConnection()) {
            String query = "SELECT piso FROM cajones WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("piso");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // En caso de error
    }

    public Estado getEstado() {
        try (Connection conn = createConn.getConnection()) {
            String query = "SELECT estado FROM cajones WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return Estado.valueOf(rs.getString("estado"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // En caso de error
    }

    public static LinkedList<Cajon> getCajones() {
        LinkedList<Cajon> cajones = new LinkedList<>();
        try (Connection conn = createConn.getConnection()) {
            String query = "SELECT id, piso, estado FROM cajones";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int piso = rs.getInt("piso");
                    Estado estado = Estado.valueOf(rs.getString("estado"));
                    Cajon cajon = new Cajon(id, piso, estado);
                    cajones.add(cajon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajones;
    }
}