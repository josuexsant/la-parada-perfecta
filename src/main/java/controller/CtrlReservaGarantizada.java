package controller;

import model.*;
import java.sql.*;

public class CtrlReservaGarantizada {
    ReservaGarantizada ReservaGarantizada;

    public void crear(int diainicio, int mesinicio, int diafin, int mesfin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);
        String fecha_inicio = "2024-" + mesinicio + "-" + diainicio;
        String fecha_fin = "2024-" + mesfin + "-" + diafin;

        ReservaGarantizada = new ReservaGarantizada(0, idAutomovil, fecha_inicio, fecha_fin, idCajon, idUsuario);

        try {
            ReservaGarantizada.guardarReserva();
            Log.success("Se guardo la reserva");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
