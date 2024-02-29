package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class TDC {
    private String numero_tarjeta;
    private Date fecha_expiracion;
    private String cvv;
    private String nombre_titular;
    private String direccion_facturacion;

    private static CreateConnection createConn = new CreateConnection();

    public TDC(String numero_tarjeta, Date fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        this.numero_tarjeta = numero_tarjeta;
        this.fecha_expiracion = fecha_expiracion;
        this.cvv = cvv;
        this.nombre_titular = nombre_titular;
        this.direccion_facturacion = direccion_facturacion;
    }

    public TDC(int idTDC) {
        String query = "SELECT " +
                "    tc.id, " +
                "    tc.id_usuario, " +
                "    tc.numero_tarjeta, " +
                "    tc.fecha_expiracion, " +
                "    tc.cvv, " +
                "    tc.nombre_titular, " +
                "    tc.direccion_facturacion " +
                "FROM " +
                "    informacion_TDC tc " +
                "    JOIN informacion_usuario iu ON tc.id_usuario = iu.id " +
                "WHERE " +
                "    tc.id = ?";
        try (Connection conn = createConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idTDC);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.numero_tarjeta= resultSet.getString("numero_tarjeta");
                    this.fecha_expiracion = resultSet.getDate("fecha_expiracion");
                    this.cvv = resultSet.getString("cvv");
                    this.nombre_titular = resultSet.getString("nombre_titular");
                    this.direccion_facturacion = resultSet.getString("direccion_facturacion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNumeroTarjeta(String numero_tarjeta){
        this.numero_tarjeta = numero_tarjeta;
    }

    public String getNumeroTarjeta(){
        return numero_tarjeta;
    }

    public void setFechaExpiracion(Date fecha_expiracion){
        this.fecha_expiracion = fecha_expiracion;
    }

    public Date getFechaExpiracion(){
        return fecha_expiracion;
    }

    public void setCvv(String cvv){
        this.cvv = cvv;
    }

    public String getCvv(){
        return cvv;
    }

    public void setNombreTitular(String nombre_titular){
        this.nombre_titular = nombre_titular;
    }

    public String getNombreTitular(){
        return nombre_titular;
    }

    public void setDireccionFacturacion(String direccion_facturacion){
        this.direccion_facturacion = direccion_facturacion;
    }

    public String getDireccionFacturacion(){
        return direccion_facturacion;
    }

    public static boolean registrar(TDC tc) throws SQLException{
        String query = "INSERT INTO informacion_TDC (numero_tarjeta, fecha_expiracion, cvv, nombre_titular, direccion_facturacion) VALUES (?,?,?,?,?,?,?);";
        String idUsuarioQuery = "SELECT id FROM informacion_usuario WHERE  = ?;";

        Connection conn = createConn.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        PreparedStatement idPstmt = conn.prepareStatement(idUsuarioQuery);

        try{
            pstmt.setString(3, tc.getNumeroTarjeta());
            pstmt.setDate(4, tc.getFechaExpiracion());
            pstmt.setString(5, tc.getCvv());
            pstmt.setString(6, tc.getNombreTitular());
            pstmt.setString(7, tc.getDireccionFacturacion());
            pstmt.executeUpdate();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Cierra los PreparedStatements y la conexión en el bloque finally
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
}

