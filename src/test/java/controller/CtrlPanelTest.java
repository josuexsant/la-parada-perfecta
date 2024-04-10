package controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;



class CtrlPanelTest {

    @Test
    //Metodo que verifica si la "membresia del usuario" existe
    void verificarMembresiaUsuario_UsuarioExiste_ReturnsNull() {
        int idUsuario = 1; // ID de usuario existente
        CtrlPanel membresiaUsuario = new CtrlPanel();
        String resultado = null;
        try {
            resultado = membresiaUsuario.verificarMembresiaUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNull(resultado);
    }

    //Metodo que verifica si la "membresia del usuario" no existe
    @Test
    void verificarMembresiaUsuario_UsuarioNoExiste_ReturnsMensajeError() {
        int idUsuario = 1000; // ID de usuario no existente
        CtrlPanel membresiaUsuario = new CtrlPanel();
        String resultado = null;
        try {
            resultado = membresiaUsuario.verificarMembresiaUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals("Usuario NO RECONOCIDO. Retroceda de la plataforma", resultado);
    }
}