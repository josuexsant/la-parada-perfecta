package controller;
import model.*;
import java.sql.*;


public class CtrlReservaGarantizada {
    ReservaGaranti ReservaGarantizada;
    Sesion sesion;
    Automovil automovil;
    public void CrearReservaGarantizada(int diainicio,int mesinicio,String matricula ){
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        try {
            idAutomovil = Automovil.getIdConMatricula(matricula);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String fecha = "2024-" + "-" + mesinicio + "-" + diainicio;

        ReservaGarantizada = new ReservaGaranti(0, idAutomovil, fecha, idCajon, idUsuario);

        try {
            ReservaGarantizada.guardarReserva();
            Log.success("Se guardo la reserva");
        }catch (SQLException e){
            e.printStackTrace();
        }





    }


}
