package controller;

import model.Operador;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ctrlOperadorTest {
    CtrlOperador ctrlOperador = new CtrlOperador();
    Operador operador = new Operador(1);
    @Test
    void iniciarSesion() throws SQLException {
        CtrlOperador operador = new CtrlOperador();

        boolean resultado =  operador.validarEmail("jfqc@gmail.com");
        assertTrue(resultado);
    }

    @Test
    void validarEmail() throws SQLException {
        CtrlOperador operador = new CtrlOperador();
        boolean resultado =  operador.validarEmail("jfqc@gmail.com");
        assertTrue(resultado);
    }

    @Test
    void validarPassword() {
        // Contraseña correcta
        try{
            ctrlOperador.validarEmail("jfqc@gmail.com");
            assertEquals(true, ctrlOperador.validarPassword("1234"));
            System.out.println("Contraseña correcta validada.");
        }catch (SQLException e){
            e.printStackTrace();
        }

        //Contraseña incorrecta
        try{
            ctrlOperador.validarEmail("jfqc@gmail.com");
            assertEquals(false, ctrlOperador.validarPassword("0000"));
            System.out.println("Contraseña incorrecta no validada");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}