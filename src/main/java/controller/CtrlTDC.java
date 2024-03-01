package controller;

import model.CreateConnection;
import model.Sesion;
import model.TDC;
import model.Usuario;

import java.sql.Date;
import java.sql.SQLException;

public class CtrlTDC {
    private TDC tdc;
    private Sesion sesion;
    private Usuario usuario;
    private final CreateConnection createConn = new CreateConnection();

    public boolean registrarTDC(String numero_tarjeta, String fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        sesion = Sesion._instance();
        usuario = sesion.getUsuario();
        TDC tarjetaNueva = new TDC(usuario.getId(), numero_tarjeta, fecha_expiracion, cvv, nombre_titular, direccion_facturacion);

        try {
            if (tarjetaNueva.registrar()) {
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
