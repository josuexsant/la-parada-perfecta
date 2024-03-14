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

    //Esta funcion la uso en cancelar reserva para obtener una reserva por el indice de la opcion seleccionada
    public Reserva obtenerReservaPorIndice(int index) throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservas.get(index);
    }

    public LinkedList<String> obtenerReservas() throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservasString(reservas);
    }

    public LinkedList<String> reservasString(LinkedList<Reserva> reservas) throws SQLException {
        LinkedList<String> reservasStrings = new LinkedList<>();

        for (Reserva reserva : reservas) {
            String reservaString = "-Id: " + reserva.getId() +
                    "  ,-Automovil: " +  Automovil.obtenerMarca(reserva.getIdAutomovil()) +
                    "  ,-Fecha: " + reserva.getFecha() +
                    "  ,-Fecha Inicio: " + reserva.getHoraInicio() +
                    "  ,-Fecha Fin:" + reserva.getHoraFin() +
                    "  ,-Cajon: " + reserva.getIdCajon() +
                    "  ,-Usuario: " + reserva.getIdUsuario() ;
            reservasStrings.add(reservaString);
        }
        return reservasStrings;
    }
    public void eliminarReservaSelccionada(int id){
        Reserva reserva = new Reserva(id);
        reserva.eliminarReserva(id);
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
                reserva.guardarReservaModificada(idUsuario, idReserva);
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
