package controller;

import model.Sesion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CtrlReservaTest {

    @Test
    void crearReserva() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReserva ctrlReserva = new CtrlReserva();
        ctrlReserva.crearReserva(12,10,"12:00","13:00","ABC-12-12");

    }
}