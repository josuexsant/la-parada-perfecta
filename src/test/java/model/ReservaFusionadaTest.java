package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservaFusionadaTest {

    @Test
    void esFusionable() {
        Reserva reserva1 = new Reserva(1, 1, "2024-03-18", "09:00:00", "11:00:00", 1, 1);
        Reserva reserva2 = new Reserva(2, 1, "2024-03-18", "10:00:00", "12:00:00", 1, 1);

        boolean fusionExitosa= reserva1.esFusionable(reserva2);

        assertTrue(fusionExitosa,"Las reservas se fusionaron");

    }
}