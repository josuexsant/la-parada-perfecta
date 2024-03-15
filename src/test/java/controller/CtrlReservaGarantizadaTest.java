package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CtrlReservaGarantizadaTest {
    CtrlUsuario ctrlUsuario;
    CtrlReservaGarantizada ctrlReservaGarantizada;
    @Test
    void crearReservaGarantizada() {
        ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@gmail.com","1234");
        ctrlReservaGarantizada = new CtrlReservaGarantizada();
        ctrlReservaGarantizada.CrearReservaGarantizada(5,5,6,7,"ABC-12-12");
    }
}