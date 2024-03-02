package controller;

import model.Cajon;
import model.Log;
import org.junit.jupiter.api.Test;

class CtrlCajonTest {

    @Test
    void getCajonDisponible() {
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        Log.debug("Primer cajor diponible: " + cajon.getId());
    }
}