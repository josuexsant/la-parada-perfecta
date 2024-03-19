package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservaTest {

    @Test
    void getReservaPorId() throws SQLException {
        Reserva reserva = new Reserva(55);
        assertEquals(55, reserva.getId());
        Log.debug(String.valueOf(reserva.getId()));
        Log.debug(reserva.getHoraInicio());
        Log.debug(reserva.getFecha());
        Log.debug(reserva.getHoraFin());
    }
}

