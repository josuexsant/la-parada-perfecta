package controller;

import model.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

public class CtrlFactura {
        String template = "src/main/xml/templates/factura.xml";
        Factura factura = new Factura(template);
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();


        //<cliente>
        int membresia = usuario.getId();
        String nombre = usuario.getNombre();
        String numero = usuario.getNumeroTelefono();
        String email = usuario.getCorreoElectronico();


        //<cobro>
        String idUsuario = String.valueOf(membresia);
        TDC tdc = new TDC(idUsuario);
        String Titular = tdc.getNombreTitular();
        String terminacionNumero = tdc.getNumeroTarjeta().substring(Math.max(0, tdc.getNumeroTarjeta().length() - 4));
        String direccion = tdc.getDireccionFacturacion();


        // xml
        public void generarFacturaPDF(String llegada, String salida, String matricula, int dia,int mes) {
            // Editar elementos del XML con los valores obtenidos
            //<fecha>

            factura.editarElemento("/factura/fecha/mes", String.valueOf(mes));
            factura.editarElemento("/factura/fecha/dia", String.valueOf(dia));

            //<cliente>
            factura.editarElemento("/factura/cliente/membresia", idUsuario);
            factura.editarElemento("/factura/cliente/nombre", nombre);
            factura.editarElemento("/factura/cliente/numero", numero);
            factura.editarElemento("/factura/cliente/email", email);

            //<cobro>
            factura.editarElemento("/factura/cobro/tarjeta/titular", Titular);
            factura.editarElemento("/factura/cobro/tarjeta/terminacionNumero", terminacionNumero);
            factura.editarElemento("/factura/cobro/tarjeta/direccionFacturacion", direccion);

            //<servicio>
            factura.editarElemento("/factura/servicio/llegada", llegada);
            factura.editarElemento("/factura/servicio/salida", salida);


            Automovil automovil = new Automovil(Automovil.getIdConMatricula(matricula));
            String marca = automovil.obtenerMarca(automovil.getIdMarca());

            factura.editarElemento("/factura/servicio/automovil/matricula", marca);
            factura.editarElemento("/factura/servicio/automovil/marca", matricula);

            // Guardar cambios en el XML
            factura.guardarCambios("src/main/xml/facturas/factura-"+ usuario.getId() +"-"+ usuario.getNombre()+".xml");

            // Generar el PDF
            factura.crearPDF("src/main/xml/templates/factura.xsl", "src/main/facturas/factura-" + usuario.getId() +"-"+ usuario.getNombre()+ ".pdf");
        }
}


