package controller;

import model.Sesion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import model.Usuario;

import java.sql.SQLException;

class CtrlReservaTest {

    CtrlUsuario ctrlUsuario = new CtrlUsuario();

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

        assertTrue(resultado);
    }



}