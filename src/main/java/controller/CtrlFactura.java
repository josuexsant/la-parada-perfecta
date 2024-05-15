package controller;

import model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CtrlFactura {


    public static void enviarConGMail(String destinatario, String asunto, String cuerpo, String rutaArchivo) {
        String remitente = "jfqc120@gmail.com";
        String claveemail = "qxoo cxzf txli toxg";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");  // Forzar el uso de TLS 1.2

        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(cuerpo);

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(rutaArchivo);
            pdfAttachment.setFileName(new File(rutaArchivo).getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(pdfAttachment);

            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }


    // xml
    public void generarFacturaPDF(String llegada, String salida, String matricula, int dia,int mes) throws ParseException {
        //values
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


        //inserting
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


        //-----
        Tarifa tarifa = new Tarifa("RESERVADO");
        double precioD = tarifa.getPrecio();
        String precio = String.valueOf(precioD);
        double precioRedondeado = Math.round(precioD * 100.0) / 100.0;
        factura.editarElemento("/factura/servicio/precioHora", String.valueOf( precioRedondeado));


        //-----
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hLlegada;
        Date hSalida;
        try {
            hLlegada = sdf.parse(llegada);
            hSalida = sdf.parse(salida);

            // Calcular la diferencia en milisegundos
            long diferenciaMs = hSalida.getTime() - hLlegada.getTime();

            // Convertir la diferencia a horas
            double horas = (double) diferenciaMs / (1000 * 60 * 60);

            // Calcular el costo
            double costoTotal = Math.round(( horas * precioRedondeado)*100.0)/100.0; // $20 por hora
            // Calcular el costo total sin IVA

            double costoTotalIVA = (costoTotal*116)/100;
            double total = Math.round(costoTotalIVA * 100.0) / 100.0;
            factura.editarElemento("/factura/cobro/subTotal", String.valueOf( costoTotal));
            factura.editarElemento("/factura/cobro/total", String.valueOf( total));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        //-----
        Automovil automovil = new Automovil(Automovil.getIdConMatricula(matricula));
        String marca = automovil.obtenerMarca(automovil.getIdMarca());

        factura.editarElemento("/factura/servicio/automovil/matricula", marca);
        factura.editarElemento("/factura/servicio/automovil/marca", matricula);

        // Guardar cambios en el XML
        factura.guardarCambios("src/main/xml/facturas/factura-"+ usuario.getId() +"-"+ usuario.getNombre()+".xml");

        // Generar el PDF
        String pdfPath = "src/main/facturas/factura-" + usuario.getId() +"-"+ usuario.getNombre()+ ".pdf";
        factura.crearPDF("src/main/xml/templates/factura.xsl", pdfPath);

        String destinatario = usuario.getCorreoElectronico();
        String asunto = "Su Factura de Servicio";
        String cuerpo = "Estimado cliente, le enviamos su factura en formato PDF.";

        enviarConGMail(destinatario, asunto, cuerpo, pdfPath);
    }
}