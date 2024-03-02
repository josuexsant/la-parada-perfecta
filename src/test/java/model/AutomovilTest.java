package model;
import controller.CtrlUsuario;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AutomovilTest {
    CtrlUsuario ctrlUsuario = new CtrlUsuario();
    Automovil automovil;
    @Test
    void guardarAutomovil() {
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        automovil = new Automovil(Sesion._instance().getUsuario().getId(),1,"ABC-12-12");
        try {
           assertEquals(true, automovil.guardarAutomovil());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}