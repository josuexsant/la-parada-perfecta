package controller;

import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlTarifa {

    public static Tarifa tarifa;

    private List<Concepto> conceptos;

    public List<Concepto> obtenerConceptos() {
        return Tarifa.getConceptos();
    }


    //visualizar
    public Map<String, Double> verTarifas()  {
        return Tarifa.visualizarTarifas();
    }


    public boolean modificarTarifa(float precio, String concepto)  {
        Tarifa tarifa = new Tarifa(concepto);

        // Verificar si se pudo crear una instancia Tarifa
        if (tarifa != null) {
            try {

                tarifa.guardarTarifa(precio);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Tarifa.class.getName()).log(Level.SEVERE, null, ex);
                // Devolver falso si la modificación falló
                return false;
            }
        } else {
            // Si no se pudo crear la instancia Tarifa, devuelve falso
            return false;
        }
    }
}
