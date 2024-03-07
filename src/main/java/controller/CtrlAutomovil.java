package controller;

import model.Automovil;
import model.Sesion;
import model.Usuario;
import view.ResgitroReserva;

import java.util.LinkedList;

public class CtrlAutomovil {
    private static ResgitroReserva resgitroReserva;
    private static Automovil automovil;
    // private static Automovil automovil = new Automovil();

    public void registrarAutomovil(){

    }


    public LinkedList<String> getMatriculas(){
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();

        LinkedList<String> placas = Automovil.matriculasPorID(idUsuario);

        for (String placa : placas) {
            System.out.println(placa);
        }

        return placas;
    }

    public String obtenerNombre(){
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        String nombre = usuario.getNombre();
        return nombre;
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
