package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CtrlUsuarioTest {
    CtrlUsuario ctrlUsuario = new CtrlUsuario();

    @Test
    @DisplayName("Validación de cuentas")
    void iniciarSesion() {
        // Correo registrado y contraseña correcta:
        assertEquals(true, ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234"));
        System.out.println("+ Correo registrado y contraseña correcta");

        // Correo registrado y contraseña incorrecta:
        assertEquals(false, ctrlUsuario.iniciarSesion("josuexsanta@example.com","1111"));
        System.out.println("+ Correo registrado y contraseña incorrecta");

        // Correo no registrado y contraseña correcta:
        assertEquals(false, ctrlUsuario.iniciarSesion("un_correo_no_registrado@example.com","1234"));
        System.out.println("+ Correo no registrado y contrasela correcta");

        // Correo no registrado y contraseña incorrecta:
        assertEquals(false, ctrlUsuario.iniciarSesion("un_correo_no_registrado@example.com","1111"));
        System.out.println("+ Correo no registrado y contraseña incorrecta");
    }

    @Test
    @DisplayName("Validación de correos")
    void validarCorreoElectronico() {
        //Un correo registrado en la base de datos:
        try {
            assertEquals(true, ctrlUsuario.validarCorreoElectronico("josuexsanta@example.com"));
            System.out.println("Correo registrado validado. (josuexsanta@example.com)");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Un correo no registrado:
        try {
            assertEquals(false, ctrlUsuario.validarCorreoElectronico("un_correo_no_registrado@example.com"));
            System.out.println("Correo no registrado no validado. (un_correo_no_registrado@example.com)");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Un correo sin el dominio:
        try {
            assertEquals(false, ctrlUsuario.validarCorreoElectronico("josuexsanta"));
            System.out.println("Correo sin dominio no validado. (josuexsanta)");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Validación de password")
    void validarPassword() {
        // Contraseña correcta
        try{
            assertEquals(true, ctrlUsuario.validarPassword("josuexsanta@example.com","1234"));
            System.out.println("Contraseña correcta validada.");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Contraseña incorrecta
        try{
            assertEquals(false, ctrlUsuario.validarPassword("josuexsanta@example.com","0000"));
            System.out.println("Contraseña incorrecta no validada");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}