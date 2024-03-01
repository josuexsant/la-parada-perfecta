package controller;

import model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.TDC;

import java.sql.Date;
import java.sql.SQLException;

class CtrlTDCTest {
    CtrlUsuario ctrlUsuario = new CtrlUsuario();
    CtrlTDC ctrlTDC = new CtrlTDC();
    TDC tdc = new TDC(1);

    @Test
    @DisplayName("Registro de tarjetas de credito")
    void registrarTDC() {
        // Registro de usuario exitoso
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        System.out.println(ctrlUsuario.getUsuario().getNombre());
        assertEquals(true, ctrlTDC.registrarTDC( "1234123412341234","1234-12-24", "123","Viri", "Ejemplo de direccion"));
        System.out.println("+ Tarjeta de credito registrada con exito");
    }
}