package controller;

import model.Automovil;

import java.util.LinkedList;

public class CtrlAutomovil {
    private static Automovil automovil;

    public void registrarAutomovil(){

    }

    public static Automovil getAutomovil() {
        return automovil;
    }

    /**
     * Metodo para cuando el usuario tiene varios autos asociados a su cuenta
     * @return: Una lista de objetos tipo auto
     */
    public LinkedList<Automovil> automovilesRegistrados(){
        return null;
    }
}
