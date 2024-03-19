package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CtrlReservaGarantizadaTest {

    @Test
    void crearReservaGarantizada() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReservaGarantizada ctrlReservaGarantizada = new CtrlReservaGarantizada();
        ctrlReservaGarantizada.CrearReservaGarantizada(02,01,03,02,"ABC-12-12");

    }
}