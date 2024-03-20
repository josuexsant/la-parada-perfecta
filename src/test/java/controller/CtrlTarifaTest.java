package controller;
import model.Concepto;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CtrlTarifaTest {

    @Test
    void obtenerConceptos() {
        CtrlTarifa ctrlTarifa = new CtrlTarifa();

        // Obtener la lista de conceptos utilizando el método obtenerConceptos() del controlador
        List<Concepto> conceptosObtenidos = ctrlTarifa.obtenerConceptos();

        // Crear una lista esperada de conceptos
        List<Concepto> conceptosEsperados = Arrays.asList(Concepto.values());

        // Verificar si la lista de conceptos obtenidos es igual a la lista de conceptos esperados
        assertEquals(conceptosEsperados, conceptosObtenidos);
    }

    @Test
    void modificarTarifa() throws SQLException {
        CtrlOperador ctrlOperador = new CtrlOperador();
        ctrlOperador.iniciarSesion("jfqc@gmail.com", "1234");
        float precio =  120;
        String concepto = "RESERVADO";
        CtrlTarifa tarifa = new CtrlTarifa();
        boolean resultado = tarifa.modificarTarifa(precio, concepto);
        assertTrue(resultado);

    }

    @Test
    void verTarifas() throws SQLException {
        CtrlTarifa ctrlTarifa = new CtrlTarifa();

        Map<String, Double> tarifas = ctrlTarifa.verTarifas();

        // Verifica que el map de tarifas no sea nula
        assertNotNull(tarifas);

        // Verifica que se hayan obtenido todos los datos
        assertEquals(3, tarifas.size());

        // Imprimir el contenido
        System.out.println("Contenido de las tarifas:");
        for (Map.Entry<String, Double> entry : tarifas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}