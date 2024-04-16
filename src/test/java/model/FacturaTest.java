package model;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacturaTest {


    @Test
    void editarElemento() {
        Factura factura = new Factura("src/main/xml/templates/factura.xml");
        factura.editarElemento("/factura/cobro/tarjeta/terminacionNumero", "1111");
        factura.guardarCambios("src/main/xml/facturas/factura-folio-001.xml");
        factura.crearPDF("src/main/xml/templates/factura.xsl", "src/main/facturas/factura-folio-001.pdf");
    }
}