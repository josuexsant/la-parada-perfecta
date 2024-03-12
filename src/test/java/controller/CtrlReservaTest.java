package controller;

import model.Log;
import model.Reserva;
import model.Sesion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CtrlReservaTest {

    CtrlReserva ctrlReserva = new CtrlReserva();
    Reserva reserva;

    @Test
    void crearReserva() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");
        CtrlReserva ctrlReserva = new CtrlReserva();
        ctrlReserva.crearReserva(12,10,"12:00","13:00","ABC-12-12");

    }

    @Test
    @DisplayName("Obtener las reservas de un usuario")
    void getReserva(){
        LinkedList<Reserva> reservas = Reserva.getReservas(69);
        for(Reserva reserva : reservas){
            Log.debug(String.valueOf(reserva.getId()));
            Log.debug(reserva.getFecha());
            Log.debug(String.valueOf(reserva.getIdAutomovil()));
            Log.debug(reserva.getHoraInicio());
            Log.debug(reserva.getHoraFin());
            Log.debug(String.valueOf(reserva.getIdCajon()));
        }
    }

    @Test
    void testGetReservasAsStringList() {
         //Mock de datos de reservas para el usuario con id 69
        Reserva reserva1 = new Reserva(1, 102, "2022-04-01", "10:00", "12:00", 1, 69);
        Reserva reserva2 = new Reserva(2, 102, "2022-04-02", "14:00", "16:00", 2, 69);

        // Crear una lista simulada de reservas
        LinkedList<Reserva> mockReservas = new LinkedList<>();
        mockReservas.add(reserva1);
        mockReservas.add(reserva2);

        // Llamar al método que se va a probar
        LinkedList<String> result = CtrlReserva.getReservasAsStringList(69);

        // Crear la lista esperada de cadenas de reservas
        LinkedList<String> expected = new LinkedList<>();
        expected.add("Reserva[id=39, automovil=6, fecha=1970-01-01, fechaInicio=01:00:00, fechaFin=04:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=43, automovil=6, fecha=1970-01-01, fechaInicio=00:00:00, fechaFin=00:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=44, automovil=6, fecha=1970-01-01, fechaInicio=01:00:00, fechaFin=22:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=45, automovil=12, fecha=1970-01-01, fechaInicio=19:00:00, fechaFin=22:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=49, automovil=6, fecha=1970-01-01, fechaInicio=00:00:00, fechaFin=00:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=50, automovil=12, fecha=1970-01-01, fechaInicio=02:00:00, fechaFin=06:00:00, cajon=1, usuario=69]");
        expected.add("Reserva[id=54, automovil=6, fecha=2024-05-01, fechaInicio=00:00:00, fechaFin=02:00:00, cajon=1, usuario=69]");

        // Verificar si el resultado es igual al esperado
        assertIterableEquals(expected, result);
    }

}