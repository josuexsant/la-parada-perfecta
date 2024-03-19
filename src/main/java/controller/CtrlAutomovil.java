package controller;

import model.Automovil;
import model.Sesion;
import model.Usuario;

import java.util.LinkedList;

public class CtrlAutomovil {

    public LinkedList<String> getMatriculas(){
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<String> matriculas = Automovil.getPlacas(idUsuario);
        return matriculas;
    }

    public String obtenerNombre(){
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        String nombre = usuario.getNombre();
        String apellidoP = usuario.getApellidoPaterno();
        String apellidoM = usuario.getApellidoMaterno();
        String nombreCompleto = nombre + " " + apellidoP + " " + apellidoM;
        return nombreCompleto;
    }
}
