package model;
import java.util.Date;

public class TDC {
    private int id;
    private int id_usuario;
    private int numero_tarjeta;
    private Date fecha_expiracion;
    private String cvv;
    private String nombre_titular;
    private String direccion_facturacion;


    public TDC(int id, int id_usuario, int numero_tarjeta, Date fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        this.id = id;
        this.id_usuario = id_usuario;
        this.numero_tarjeta = numero_tarjeta;
        this.fecha_expiracion = fecha_expiracion;
        this.cvv = cvv;
        this.nombre_titular = nombre_titular;
        this.direccion_facturacion = direccion_facturacion;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setIdUsuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public int getIdUsuario(){
        return id_usuario;
    }

    public void setNumeroTarjeta(int numero_tarjeta){
        this.numero_tarjeta = numero_tarjeta;
    }

    public int getNumeroTarjeta(){
        return numero_tarjeta;
    }

    public void setFechaExpiracion(Date fecha_expiracion){
        this.fecha_expiracion = fecha_expiracion;
    }

    public Date getFechaexpiracion(){
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
}

