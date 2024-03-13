package controller;

import model.Log;
import model.Reserva;
import model.Sesion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CtrlReservaTest {

    CtrlReserva ctrlReserva = new CtrlReserva();
    Reserva reserva;

    @Test
    void crearReserva() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReserva ctrlReserva = new CtrlReserva();
        ctrlReserva.crearReserva(12,10,"12:00","13:00","ABC-12-12");

    }

    @Test
    @DisplayName("Obtener las reservas de un usuario")
    void getReserva(){
        LinkedList<Reserva> reservas = Reserva.getReservas(69);
        for(Reserva reserva : reservas){
            Log.debug(String.valueOf(reserva.getId()));
            Log.debug(reserva.getFecha());
            Log.debug(String.valueOf(reserva.getIdAutomovil()));
            Log.debug(reserva.getHoraInicio());
            Log.debug(reserva.getHoraFin());
            Log.debug(String.valueOf(reserva.getIdCajon()));
        }
    }

    @Test
    void testGetReservasAsStringList() throws SQLException {
        CtrlReserva ctrlReserva1 = new CtrlReserva();
        LinkedList<Reserva> reservacioncitas = Reserva.getReservas(69);
        LinkedList<String> vistaReservas = ctrlReserva1.reservasString(reservacioncitas);
        for (String elemento : vistaReservas ) {
            System.out.println(elemento);
        }
    }

    @Test
    void EliminarReserva(){
        CtrlReserva ctrlReserva1 = new CtrlReserva();
        int id = 54;
        ctrlReserva1.eliminarReservaSelccionada(id);
    }





}