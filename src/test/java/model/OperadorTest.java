package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OperadorTest {

    @Test
    void operadorExiste() {
        try{
            //Correo electronico asociado a cuenta
            assertEquals(true,Operador.operadorExiste("jfqc@gmail.com"));
            //Correo electronico no asociado a cuenta
            assertEquals(false,Operador.operadorExiste("un_correp_no_registrado@gmail.com"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void obtenerIdOperador() {
        try {

            //Correo electronico asociado a cuenta
            assertEquals(1,Operador.obtenerIdOperador("jfqc@gmail.com"));
            //Correo electronico no asociado a cuenta
            assertEquals(-1,Operador.obtenerIdOperador("un_correp_no_registrado@gmail.com"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}