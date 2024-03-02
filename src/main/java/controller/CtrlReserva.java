package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;
import java.sql.Timestamp;

public class CtrlReserva {
    Sesion sesion;
    Reserva reserva;
    Automovil automovil;

    /**
     * @author: Josue Santamaria
     *
     */
    public void  crearReserva(int dia, int mes, String horaLlegada, String horaSalida,String matricula){
        Log.debug(Integer.toString(dia));
        Log.debug(Integer.toString(mes));
        Log.debug(horaLlegada);
        Log.debug(horaSalida);
        Log.debug(matricula);
    }
}
