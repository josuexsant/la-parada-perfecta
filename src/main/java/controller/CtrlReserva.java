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

    public static LinkedList<String> getReservasAsStringList(int idUsuario) {
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return convertReservasToStringList(reservas);
    }

    private static LinkedList<String> convertReservasToStringList(LinkedList<Reserva> reservas) {
        LinkedList<String> reservasStrings = new LinkedList<>();

        for (Reserva reserva : reservas) {
            String reservaString = "Reserva[id=" + reserva.getId() +
                    ", automovil=" + reserva.getIdAutomovil() +
                    ", fecha=" + reserva.getFecha() +
                    ", fechaInicio=" + reserva.getHoraInicio() +
                    ", fechaFin=" + reserva.getHoraFin() +
                    ", cajon=" + reserva.getIdCajon() +
                    ", usuario=" + reserva.getIdUsuario() + "]";
            reservasStrings.add(reservaString);
        }

        return reservasStrings;
    }




}
