package controller;

import model.CreateConnection;
import model.TDC;
import java.sql.Date;
import java.sql.SQLException;

public class CtrlTDC {
    private TDC tdc;
    private final CreateConnection createConn = new CreateConnection();

    public boolean registrarTDC(String numero_tarjeta, Date fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        TDC p = new TDC(numero_tarjeta, fecha_expiracion, cvv, nombre_titular, direccion_facturacion);

        p.setNumeroTarjeta(numero_tarjeta);
        p.setFechaExpiracion(fecha_expiracion);
        p.setCvv(cvv);
        p.setNombreTitular(nombre_titular);
        p.setDireccionFacturacion(direccion_facturacion);

        try {
            if (TDC.registrar(p)) {
                System.out.println("Registro de la tarjeta de credito exitoso");
                return true;
            } else {
                System.out.println("Registro de la tarjeta de credito fallido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
