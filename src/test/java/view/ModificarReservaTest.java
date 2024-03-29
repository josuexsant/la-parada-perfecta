package view;

import controller.CtrlUsuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModificarReservaTest {

    @Test
    void mostrarInterfaz() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@gmail.com","1234");

        ModificarReserva view = new ModificarReserva();
        view.mostrarInterfaz();
    }
}