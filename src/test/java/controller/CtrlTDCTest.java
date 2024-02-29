package controller;

import model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.TDC;

import java.sql.Date;

class CtrlTDCTest {
    CtrlTDC ctrlTDC = new CtrlTDC();
    TDC tdc = new TDC(1);

    @Test
    @DisplayName("Registro de tarjetas de credito")
    void registrarTDC() {
        // Registro de usuario exitoso
        assertEquals(true, ctrlTDC.registrarTDC( "35263266677",    Date.valueOf("2022-12-31"),"123", "Alexis", "Ejemplo de direccion"));
        System.out.println("+ Tarjeta de credito registrada con exito");
    }
}