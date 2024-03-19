package controller;

import model.Cajon;
import java.util.LinkedList;

public class CtrlCajon {
    private LinkedList<Cajon> cajonesDisponibles;

    public Cajon getCajonDisponible(){
        cajonesDisponibles = Cajon.getLugaresDisponibles();
        return cajonesDisponibles.getFirst();
    }
}
