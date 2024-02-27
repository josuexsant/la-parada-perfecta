package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CtrlUsuarioTest {
    CtrlUsuario ctrlUsuario = new CtrlUsuario();

    @Test
    void iniciarSesion() {

    }

    @Test
    @DisplayName("Validación de correos")
    void validarCorreoElectronico() {
        //Un correo registrado en la base de datos:
        try {
            assertEquals(true, ctrlUsuario.validarCorreoElectronico("josuexsanta@example.com"));
            System.out.println("Correo registrado validado");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Un correo no registrado:
        try {
            assertEquals(false, ctrlUsuario.validarCorreoElectronico("un_correo_no_registrado@example.com"));
            System.out.println("Correo no registrado no validado");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Un correo sin el dominio:
        try {
            assertEquals(false, ctrlUsuario.validarCorreoElectronico("josuexsanta"));
            System.out.println("Correo sin dominio validado");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void validarPassword() {
    }
}