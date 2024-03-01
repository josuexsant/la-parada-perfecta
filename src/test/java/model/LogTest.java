package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    @Test
    void info() {
        Log.info("hola");
    }

    @Test
    void warn() {
        Log.warn("Cuidado");
    }

    @Test
    void error() {
        Log.error("Fernando");
    }

    @Test
    void debug() {
        Log.debug("Se creo un objeto");
    }

    @Test
    void success() {
        Log.success("Felicidades");
    }

    @Test
    void trace() {
        Log.trace("Por aqui es");
    }
}