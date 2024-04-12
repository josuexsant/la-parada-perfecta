package controller;

import model.Log;
import model.Reserva;
import model.Sesion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import model.Usuario;

import java.sql.SQLException;

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
    public void testModificarReserva() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReserva ctrlReserva = new CtrlReserva();
        int idReserva = 55;
        int dia = 12;
        int mes = 3;
        String horaInicio = "10:00";
        String horaFin = "12:00";
        String matricula = "ABC123";
        CtrlReserva instancia = new CtrlReserva();
        boolean resultado = instancia.modificarReserva(idReserva, dia, mes, horaInicio, horaFin, matricula);
        //assertTrue(resultado);
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

    @Test
    void crearReservaImprevista(){
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("alexis@example.com", "12345678");
        CtrlReserva ctrlReserva = new CtrlReserva();
        ctrlReserva.crearReservaImprevista("04:00", "ABC-1-2-3");

    }

    @DisplayName("Verificar si una reserva es fusionable")
    @Test
    void esFusionable() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@gmail.com","12345678");
        CtrlReserva ctrlReserva = new CtrlReserva();
        Reserva reservaNueva = ctrlReserva.crearReserva(12,9,"13:00","14:00","QET-12-14");

        ctrlReserva.esFusionable(reservaNueva);

    }
}