package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Existencia de cuenta asociada con correo")
    void usuarioExiste() {
        //Correo electronico asociado a cuenta
        assertEquals(true,Usuario.usuarioExiste("josuexsanta@example.com"));
        //Correo electronico no asociado a cuenta
        assertEquals(false,Usuario.usuarioExiste("un_correp_no_registrado@gmail.com"));
    }

    @Test
    @DisplayName("Obtencion de id por correo electronico")
    void obtenerIdUsuario() {
        try {

            //Correo electronico asociado a cuenta
            assertEquals(2,Usuario.obtenerIdUsuario("josuexsanta@example.com"));
            //Correo electronico no asociado a cuenta
            assertEquals(-1,Usuario.obtenerIdUsuario("un_correp_no_registrado@gmail.com"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}