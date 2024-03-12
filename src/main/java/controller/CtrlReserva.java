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
        String fecha = "2024-" + "-" + mes + "-" + dia;
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
        try {
            // Obtener la reserva existente
            Reserva reservaExistente = new Reserva(idReserva);
            // Verificar si la reserva existe
            if (reservaExistente != null) {
                // Modificar los atributos de la reserva existente
                reservaExistente.setIdAutomovil(idAutomovil);
                reservaExistente.setFecha(fecha);
                reservaExistente.setHoraInicio(hInicio);
                reservaExistente.setHoraFin(hFin);
                reservaExistente.setIdCajon(idCajon);
                reservaExistente.setIdUsuario(idUsuario);
                // Aplicar los cambios en la base de datos
                System.out.println("Reserva modificada de manera exitosa");
                reservaExistente.modificarReserva();
            } else {
                // La reserva no existe
                System.out.println("Reserva NO modificada");
                return false;
            }
        } catch (SQLException ex) {
            // Manejar la excepción según tus necesidades
            ex.printStackTrace();
            return false;
        }
        return false;
    }
}
