package controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class CtrlAutomovilTest {

    @Test
    void crearMatriculaTest(){
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("viridianabenitezg@gmail.com","1234");
        CtrlAutomovil ctrlAutomovil = new CtrlAutomovil();
        ctrlAutomovil.crearMatricula(1, "VIR-12-00");
    }

    @Test
    void eliminarMatricula(){
        CtrlAutomovil ctrlAutomovil = new CtrlAutomovil();
        ctrlAutomovil.eliminarMatricula("VIR-13-07");
    }

    @Test
    void modificarMatricula()  {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("viridianabenitezg@gmail.com","1234");
        CtrlAutomovil ctrlAutomovil = new CtrlAutomovil();
        ctrlAutomovil.modificarAutomovil(1,"VIR-00-00","VIR-13-07");
    }
}
