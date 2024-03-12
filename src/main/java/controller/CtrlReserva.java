package controller;

import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.sql.Timestamp;
import java.util.LinkedList;

public class CtrlReserva {
    Sesion sesion;
    Reserva reserva;
    Automovil automovil;


    /**
     * @author: Josue Santamaria
     *
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
        String fecha = "2024-"+"-"+mes+"-"+dia;
        reserva = new Reserva(0, idAutomovil,fecha, horaInicio + ":00", horaFin +":00", idCajon, idUsuario);
        try {
            reserva.guardarReserva();
            Log.success("Se guardo la reserva");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public LinkedList<String> obtenerReservas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservasString(reservas);
    }

    public LinkedList<String> reservasString(LinkedList<Reserva> reservas) {
        LinkedList<String> reservasStrings = new LinkedList<>();

        for (Reserva reserva : reservas) {
            String reservaString = "-Id=" + reserva.getId() +
                    ", -Automovil: " + reserva.getIdAutomovil() +
                    ", -Fecha: " + reserva.getFecha() +
                    ", -Fecha Inicio: " + reserva.getHoraInicio() +
                    ", -Fecha Fin:" + reserva.getHoraFin() +
                    ", -Cajon: " + reserva.getIdCajon() +
                    ", -Usuario: " + reserva.getIdUsuario() ;
            reservasStrings.add(reservaString);
        }
        return reservasStrings;
    }

    public void eliminarReservaSelccionada(int id){

    }
}
