package controller;

import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.sql.Timestamp;

public class CtrlReserva {
    Sesion sesion;
    Reserva reserva;
    Automovil automovil;

    /**
     * @author: Josue Santamaria
     *
     */
    public void crearReserva(int dia, int mes, String horaLlegada, String horaSalida, String matricula) {
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
        Calendar cal = Calendar.getInstance();
        int año = cal.get(Calendar.YEAR);

        // Crear una fecha con el mes, día y año
        cal.set(año, mes - 1, dia);

        // Crear un formato para la hora y el minuto
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dateInicio = null;
        Date dateFin = null;
        try {
            dateInicio = sdf.parse(horaLlegada);
            dateFin = sdf.parse(horaSalida);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Establecer la hora y el minuto en el objeto Calendar
        cal.setTime(dateInicio);
        Timestamp timestampInicio = new Timestamp(cal.getTimeInMillis());

        cal.setTime(dateFin);
        Timestamp timestampFin = new Timestamp(cal.getTimeInMillis());
        reserva = new Reserva(0, idAutomovil, timestampInicio, timestampInicio, timestampFin, idCajon, idUsuario);
        try {

            reserva.guardarReserva();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
