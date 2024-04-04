package controller;

import model.Log;
import model.Sesion;
import model.TDC;
import model.Usuario;

import java.sql.SQLException;

public class CtrlTDC {
    private TDC tdc;
    private Sesion sesion;
    private Usuario usuario;

    public boolean registrarTDC(String numero_tarjeta, String fecha_expiracion, String cvv, String nombre_titular, String direccion_facturacion){
        sesion = Sesion._instance();
        usuario = sesion.getUsuario();
        TDC tarjetaNueva = new TDC(usuario.getId(), numero_tarjeta, fecha_expiracion, cvv, nombre_titular, direccion_facturacion);

        try {
            if (tarjetaNueva.registrar()) {
                Log.success("Registro de la tarjeta de credito exitoso");
                return true;
            } else {
                Log.warn("Registro de la tarjeta de credito fallido");
            }
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
        return false;
    }
}
