package controller;

import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class CtrlReserva {
    Reserva reserva;
    Reserva fusion;

    /**
     * @return
     * @author: Josue Santamaria
     */
    public Reserva crearReserva(int dia, int mes, String horaInicio, String horaFin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, mes, dia);
        Date date = calendar.getTime();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(date);
        reserva = new Reserva(0, idAutomovil, fecha, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        return reserva;
    }

    public void guardar() {
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

    public LinkedList<Reserva> getList() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservas;
    }

    public LinkedList<String> reservasString(LinkedList<Reserva> reservas) {
        LinkedList<String> reservasStrings = new LinkedList<>();

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
        return reservasStrings;
    }

    public void eliminarReservaSelccionada(int id) {
        Reserva reserva = new Reserva(id);
        reserva.eliminar(id);
    }

    public boolean modificarReserva(int idReserva, int dia, int mes, String horaInicio, String horaFin, String matricula) {
        String fecha = "2024-" + mes + "-" + dia;
        String hInicio = horaInicio + ":00";
        String hFin = horaFin + ":00";
        Log.debug(fecha);

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
    public boolean extenderReserva(int idReserva, String horaInicio, String horaFin, String matricula) {
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        int idAutomovil = Automovil.getIdConMatricula(matricula);

        reserva = new Reserva(idReserva);
        if (reserva != null) {
            String fecha = reserva.getFecha(); // Obtener la fecha de la reserva
            // Actualizar la reserva con las nuevas horas
            reserva.setHoraInicio(horaInicio + ":00");
            reserva.setHoraFin(horaFin + ":00");
            reserva.setIdAutomovil(idAutomovil);
            reserva.setIdCajon(idCajon);
            reserva.setIdUsuario(idUsuario);
            // Llamar al método para actualizar la reserva en la base de datos
            reserva.extenderReserva(idUsuario, idReserva);
            Log.success("Salió bien extender tiempo");
            return true;
        } else {
            return false;
        }
    }

    public Reserva crearReservaImprevista(String horaFin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);

        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        String horaInicio = horaActual.format(DateTimeFormatter.ofPattern("HH:mm"));

        LocalDate fechaActual = LocalDate.now();
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        reserva = new Reserva(0, idAutomovil, fechaFormateada, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        reserva.guardar();
        return reserva;
    }

    public int esFusionable(Reserva reservaNueva) {
        LinkedList<Reserva> reservas = getList();
        Log.trace("Hora de inicio de la nueva reserva: " + reservaNueva.getHoraInicio());
        for (Reserva reservaAntigua : reservas) {
            if (reservaAntigua.getFecha().equals(reservaNueva.getFecha())) {
                Log.warn("La fecha es la misma");
                Log.trace("Hora fin de la reserva antigua: " + reservaAntigua.getHoraFin());
                if (reservaAntigua.getHoraFin().equals(reservaNueva.getHoraInicio())) {
                    Log.info("Es fusionable con la reserva con " + reservaAntigua.getId());
                    fusionAfter(reservaAntigua, reservaNueva);
                    return 3;
                } else if (reservaAntigua.getHoraInicio().equals(reservaNueva.getHoraFin())) {
                    Log.info("Es fusionable con la reserva con " + reservaAntigua.getId());
                    fusionBefore(reservaAntigua, reservaNueva);
                    return 3;
                }
            }
        }
        return 4;
    }

    public void fusionBefore(Reserva reservaAntigua, Reserva reservaNueva) {
        reservaAntigua.setIdAutomovil(reservaNueva.getIdAutomovil());
        reservaAntigua.setFecha(reservaNueva.getFecha());
        reservaAntigua.setHoraInicio(reservaNueva.getHoraInicio());
        reservaAntigua.setHoraFin(reservaAntigua.getHoraFin());
        reservaAntigua.setIdCajon(reservaAntigua.getIdCajon());
        reservaAntigua.setIdUsuario(reservaAntigua.getIdUsuario());
        reservaAntigua.modificar(reservaAntigua.getIdUsuario(), reservaAntigua.getId());
        fusion = reservaAntigua;
        Log.info("Reserva fusionada");
    }

    public void fusionAfter(Reserva reservaAntigua, Reserva rn) {
        reservaAntigua.setIdAutomovil(rn.getIdAutomovil());
        reservaAntigua.setFecha(rn.getFecha());
        reservaAntigua.setHoraInicio(reservaAntigua.getHoraInicio());
        reservaAntigua.setHoraFin(rn.getHoraFin());
        reservaAntigua.setIdCajon(reservaAntigua.getIdCajon());
        reservaAntigua.setIdUsuario(reservaAntigua.getIdUsuario());
        reservaAntigua.modificar(reservaAntigua.getIdUsuario(), reservaAntigua.getId());
        fusion = reservaAntigua;
        Log.info("Reserva fusionada");
    }

    public Reserva getFusion() {
        return fusion;
    }

    public int esDuplicada(Reserva rn) {
        LinkedList<Reserva> reservas = getList();

        for (Reserva reservaAntigua : reservas) {
            if (reservaAntigua.getFecha().equals(rn.getFecha())) {

                if (reservaAntigua.getHoraInicio().equals(rn.getHoraInicio()) && reservaAntigua.getHoraFin().equals(reserva.getHoraFin())) {
                    Log.info("La reserva ya existe con el ID: " + reservaAntigua.getId());
                    reserva = null;
                    return 2;
                }
            }
        }
        return 4;
    }
}
