package controller;

import model.*;

import java.sql.*;
import java.util.LinkedList;

public class CtrlReserva {
    Sesion sesion;
    Reserva reserva;
    Automovil automovil;

    /**
     * @author: Josue Santamaria
     */

    public void crearReserva(int dia, int mes, String horaInicio, String horaFin, String matricula) {
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
        String fecha = "2024-" + "-" + mes + "-" + dia;
        reserva = new Reserva(0, idAutomovil, fecha, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        try {
            reserva.guardarReserva();
            Log.success("Se guardo la reserva");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean modificarReserva(int idReserva, int dia, int mes, String horaInicio, String horaFin, String matricula) {
        String fecha = "2024-" + dia + "-" + mes;
        String hInicio = horaInicio + ":00";
        String hFin = horaFin + ":00";

        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();

        int idUsuario = Sesion._instance().getUsuario().getId();

        int idAutomovil = 0;
        try {
            idAutomovil = Automovil.getIdConMatricula(matricula);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            // Objeto de tipo reserva (existente)
            reserva = new Reserva(idReserva);
            if(reserva != null){
                reserva.setIdAutomovil(idAutomovil);
                reserva.setFecha(fecha);
                reserva.setHoraInicio(hInicio);
                reserva.setHoraFin(hFin);
                reserva.setIdCajon(idCajon);
                reserva.setIdUsuario(idUsuario);
                reserva.guardarReservaModificada();
            }else{
                return false;
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
}
