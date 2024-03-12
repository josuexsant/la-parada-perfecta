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
    void modificarReserva() throws SQLException {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReserva ctrlReserva = new CtrlReserva();
        assertEquals(true, ctrlReserva.modificarReserva( 55, 01, 03, "05", "09", "ABC-12-12"));
    }


}