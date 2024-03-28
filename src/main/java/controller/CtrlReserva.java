package controller;

import model.*;

import java.sql.*;
import java.util.LinkedList;

public class CtrlReserva {
    Reserva reserva;

    /**
     * @author: Josue Santamaria
     */
    public void crearReserva(int dia, int mes, String horaInicio, String horaFin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);
        String fecha = "2024-" + "-" + mes + "-" + dia;
        reserva = new Reserva(0, idAutomovil, fecha, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        reserva.guardar();
    }

    //Esta funcion la uso en cancelar reserva para obtener una reserva por el indice de la opcion seleccionada
    public Reserva obtenerReservaPorIndice(int index) throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservas.get(index);
    }

    public LinkedList<String> obtenerReservas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservasString(reservas);
    }

    public LinkedList<String> reservasString(LinkedList<Reserva> reservas){
        LinkedList<String> reservasStrings = new LinkedList<>();
        try {
            for (Reserva reserva : reservas) {
                String reservaString = "-Id: " + reserva.getId() +
                        "  ,-Automovil: " + Automovil.obtenerMarca(reserva.getIdAutomovil()) +
                        "  ,-Fecha: " + reserva.getFecha() +
                        "  ,-Fecha Inicio: " + reserva.getHoraInicio() +
                        "  ,-Fecha Fin:" + reserva.getHoraFin() +
                        "  ,-Cajon: " + reserva.getIdCajon() +
                        "  ,-Usuario: " + reserva.getIdUsuario();
                reservasStrings.add(reservaString);
            }
        }catch (SQLException e){
            Log.error(e.getMessage());
        }
        return reservasStrings;
    }

    public void eliminarReservaSelccionada(int id) {
        Reserva reserva = new Reserva(id);
        reserva.eliminar(id);
    }

    public boolean modificarReserva(int idReserva, int dia, int mes, String horaInicio, String horaFin, String matricula) {
        String fecha = "2024-" + dia + "-" + mes;
        String hInicio = horaInicio + ":00";
        String hFin = horaFin + ":00";
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        int idAutomovil = Automovil.getIdConMatricula(matricula);

        reserva = new Reserva(idReserva);
        if (reserva != null) {
            reserva.setIdAutomovil(idAutomovil);
            reserva.setFecha(fecha);
            reserva.setHoraInicio(hInicio);
            reserva.setHoraFin(hFin);
            reserva.setIdCajon(idCajon);
            reserva.setIdUsuario(idUsuario);
            reserva.modificar(idUsuario, idReserva);
            return true;
        } else {
            return false;
        }
    }

    public void fusionarReservas(Reserva reserva1, Reserva reserva2) {
        if (reserva1.esFusionable(reserva2)) {
            Log.info("Las reservas son fusionables.");
        } else {
            Log.info("Las reservas no son fusionables.");
        }
    }
}
