package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class TDC {

    private int id_usuario;
    private String numero_tarjeta;
    private String fecha_expiracion;
    private String cvv;
    private String nombre_titular;
    private String direccion_facturacion;

    private static CreateConnection createConn = new CreateConnection();

    public TDC(int id_usuario, String numero_tarjeta, String fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        this.id_usuario=id_usuario;
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
                    this.id_usuario = resultSet.getInt("id_usuario");
                    this.numero_tarjeta= resultSet.getString("numero_tarjeta");
                    this.fecha_expiracion = resultSet.getString("fecha_expiracion");
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

    public void setFechaExpiracion(String fecha_expiracion){
        this.fecha_expiracion = fecha_expiracion;
    }

    public String getFechaExpiracion(){
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

    public void setIdUsuario(int id_usuario){
        this.id_usuario=id_usuario;
    }

    public int getIdUsuario(){
        return id_usuario;
    }

    public boolean registrar() throws SQLException{
        String query = "INSERT INTO informacion_TDC (id_usuario, numero_tarjeta, fecha_expiracion, cvv, nombre_titular, direccion_facturacion) VALUES (?,?,?,?,?,?);";
        Connection conn = createConn.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try{
            pstmt.setInt(1, this.id_usuario);  // Corregido el índice a 1
            pstmt.setString(2, this.numero_tarjeta);  // Corregido el índice a 2
            pstmt.setString(3, this.fecha_expiracion);
            pstmt.setString(4, this.cvv);
            pstmt.setString(5, this.nombre_titular);
            pstmt.setString(6, this.direccion_facturacion);
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

