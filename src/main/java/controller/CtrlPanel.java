package controller;

import model.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

//tengo que checar 2 cosas, numero de socio valido, y si existe alguna reserva a esa hora para poder darle indicaciones
public class CtrlPanel {
    private static Usuario usuario = new Usuario();
    private static Reserva reserva;
    SimulatedTime simulatedTime = SimulatedTime.getInstance();


    public String verificarMembresiaUsuario(int idUsuario) throws SQLException {
        if (usuario.usuarioExiste(idUsuario)) {
            return "Bienvenido " + usuario.nombreCompleto(idUsuario);
        } else {
            return "Usuario NO RECONOCIDO. Retroceda de la plataforma";
        }
    }

}