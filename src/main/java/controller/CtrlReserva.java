package controller;

import model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

public class CtrlReserva {
    Reserva reserva;
    Reserva fusion;

    /**
     * @return
     * @author: Josue Santamaria
     */
    public Reserva crearReserva(int dia, int mes, String horaInicio, String horaFin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, mes, dia);
        Date date = calendar.getTime();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(date);
        reserva = new Reserva(0, idAutomovil, fecha, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        return reserva;
    }

    public void guardar() {
        reserva.guardar();
    }

    //Esta funcion la uso en cancelar reserva para obtener una reserva por el indice de la opcion seleccionada
    public Reserva obtenerReservaPorIndice(int index) throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservas.get(index);
    }

    public LinkedList<String> obtenerReservas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservasString(reservas);
    }

    public LinkedList<Reserva> getList() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<Reserva> reservas = Reserva.getReservas(idUsuario);
        return reservas;
    }

    public LinkedList<String> reservasString(LinkedList<Reserva> reservas) {
        LinkedList<String> reservasStrings = new LinkedList<>();

        for (Reserva reserva : reservas) {
            String reservaString = "-Id: " + reserva.getId() +
                    "  ,-Automovil: " + Automovil.obtenerMarca(reserva.getIdAutomovil()) +
                    "  ,-Fecha: " + reserva.getFecha() +
                    "  ,-Fecha Inicio: " + reserva.getHoraInicio() +
                    "  ,-Fecha Fin:" + reserva.getHoraFin() +
                    "  ,-Cajon: " + reserva.getIdCajon() +
                    "  ,-Usuario: " + reserva.getIdUsuario();
            reservasStrings.add(reservaString);
        }
        return reservasStrings;
    }

    public void eliminarReservaSelccionada(int id) {
        Reserva reserva = new Reserva(id);
        Automovil automovil = new Automovil(reserva.getIdAutomovil());
        enviarNotificacion("Reserva cancelada", "Reserva cancelada", "Tu reserva con No. " + reserva.getId() + " con fecha "+reserva.getFecha()+ " para el vehiculo con matricula: "+ automovil.getPlaca() +" ha sido cancelada");
        reserva.eliminar(id);
    }

    public boolean modificarReserva(int idReserva, int dia, int mes, String horaInicio, String horaFin, String matricula) {
        String fecha = "2024-" + mes + "-" + dia;
        String hInicio = horaInicio + ":00";
        String hFin = horaFin + ":00";
        Log.debug(fecha);

        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        int idAutomovil = Automovil.getIdConMatricula(matricula);

        reserva = new Reserva(idReserva);
        if (reserva != null) {
            reserva.setIdAutomovil(idAutomovil);
            reserva.setFecha(fecha);
            reserva.setHoraInicio(hInicio);
            reserva.setHoraFin(hFin);
            reserva.setIdCajon(idCajon);
            reserva.setIdUsuario(idUsuario);
            reserva.modificar(idUsuario, idReserva);
            enviarNotificacion("Reserva modificada", "Reserva modificada", "Tu reserva con No. " + reserva.getId() + " ha sido modificada");
            return true;
        } else {
            return false;
        }
    }

    public boolean extenderReserva(int idReserva, String horaInicio, String horaFin, String matricula) {
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        int idAutomovil = Automovil.getIdConMatricula(matricula);

        reserva = new Reserva(idReserva);
        if (reserva != null) {
            String fecha = reserva.getFecha(); // Obtener la fecha de la reserva
            // Actualizar la reserva con las nuevas horas
            reserva.setHoraInicio(horaInicio + ":00");
            reserva.setHoraFin(horaFin + ":00");
            reserva.setIdAutomovil(idAutomovil);
            reserva.setIdCajon(idCajon);
            reserva.setIdUsuario(idUsuario);
            // Llamar al método para actualizar la reserva en la base de datos
            reserva.extenderReserva(idUsuario, idReserva);
            Log.success("Salió bien extender tiempo");
            enviarNotificacion("Reserva extendida", "Reserva extendida", "Tu reserva con No. " + reserva.getId() + " ha sido extendida");
            return true;
        } else {
            return false;
        }
    }

    public Reserva crearReservaImprevista(String horaFin, String matricula) {
        int idAutomovil = 0;
        CtrlCajon ctrlCajon = new CtrlCajon();
        Cajon cajon = ctrlCajon.getCajonDisponible();
        int idCajon = cajon.getId();
        int idUsuario = Sesion._instance().getUsuario().getId();
        idAutomovil = Automovil.getIdConMatricula(matricula);

        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        String horaInicio = horaActual.format(DateTimeFormatter.ofPattern("HH:mm"));

        LocalDate fechaActual = LocalDate.now();
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        reserva = new Reserva(0, idAutomovil, fechaFormateada, horaInicio + ":00", horaFin + ":00", idCajon, idUsuario);
        reserva.guardar();
        return reserva;
    }

    public int esFusionable(Reserva reservaNueva) {
        LinkedList<Reserva> reservas = getList();
        Log.trace("Hora de inicio de la nueva reserva: " + reservaNueva.getHoraInicio());
        for (Reserva reservaAntigua : reservas) {
            if (reservaAntigua.getFecha().equals(reservaNueva.getFecha())) {
                Log.warn("La fecha es la misma");
                Log.trace("Hora fin de la reserva antigua: " + reservaAntigua.getHoraFin());
                if (reservaAntigua.getHoraFin().equals(reservaNueva.getHoraInicio())) {
                    Log.info("Es fusionable con la reserva con " + reservaAntigua.getId());
                    fusionAfter(reservaAntigua, reservaNueva);
                    return 3;
                } else if (reservaAntigua.getHoraInicio().equals(reservaNueva.getHoraFin())) {
                    Log.info("Es fusionable con la reserva con " + reservaAntigua.getId());
                    fusionBefore(reservaAntigua, reservaNueva);
                    return 3;
                }
            }
        }
        return 4;
    }

    public void fusionBefore(Reserva reservaAntigua, Reserva reservaNueva) {
        reservaAntigua.setIdAutomovil(reservaNueva.getIdAutomovil());
        reservaAntigua.setFecha(reservaNueva.getFecha());
        reservaAntigua.setHoraInicio(reservaNueva.getHoraInicio());
        reservaAntigua.setHoraFin(reservaAntigua.getHoraFin());
        reservaAntigua.setIdCajon(reservaAntigua.getIdCajon());
        reservaAntigua.setIdUsuario(reservaAntigua.getIdUsuario());
        reservaAntigua.modificar(reservaAntigua.getIdUsuario(), reservaAntigua.getId());
        fusion = reservaAntigua;
        Log.info("Reserva fusionada");
    }

    public void fusionAfter(Reserva reservaAntigua, Reserva rn) {
        reservaAntigua.setIdAutomovil(rn.getIdAutomovil());
        reservaAntigua.setFecha(rn.getFecha());
        reservaAntigua.setHoraInicio(reservaAntigua.getHoraInicio());
        reservaAntigua.setHoraFin(rn.getHoraFin());
        reservaAntigua.setIdCajon(reservaAntigua.getIdCajon());
        reservaAntigua.setIdUsuario(reservaAntigua.getIdUsuario());
        reservaAntigua.modificar(reservaAntigua.getIdUsuario(), reservaAntigua.getId());
        fusion = reservaAntigua;
        Log.info("Reserva fusionada");
    }

    public Reserva getFusion() {
        return fusion;
    }

    public int esDuplicada(Reserva rn) {
        LinkedList<Reserva> reservas = getList();

        for (Reserva reservaAntigua : reservas) {
            if (reservaAntigua.getFecha().equals(rn.getFecha())) {

                if (reservaAntigua.getHoraInicio().equals(rn.getHoraInicio()) && reservaAntigua.getHoraFin().equals(reserva.getHoraFin())) {
                    Log.info("La reserva ya existe con el ID: " + reservaAntigua.getId());
                    reserva = null;
                    return 2;
                }
            }
        }
        return 4;
    }

    public void enviarNotificacion(String asunto, String titulo, String mensaje) {
        String cuerpo = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>\n" +
                "    <div style='width: 80%; margin: auto; overflow: hidden;'>\n" +
                "      <div style='padding: 20px; background-color: #fff; color: #333;'>\n" +
                "        <h1 style='color: #1d3557; text-align: left;'>LA PARADA PERFECTA</h1>\n" +
                "        <hr />\n" +
                "        <h2 style='color: #e63946; text-align: center;'>" + titulo + "</h2>\n" +
                "        <p style='font-size: 1.1em; line-height: 1.6; color: #666;'>Hola, " + Sesion._instance().getUsuario().nombreCompleto(Sesion._instance().getUsuario().getId()) + "</p>\n" +
                "        <p style='color: white; text-align: center; background-color: #457b9d; padding: 10px; border-radius: 5px;'>" + mensaje + "</p>\n" +
                "        <p style='font-size: 1.1em; line-height: 1.6; color: #666;'>Fecha: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(SimulatedTime.getInstance().getDate().getTime()) + "</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div style='background-color: white; padding: 10px; margin-top: 10px; border-radius: 5px;'>\n" +
                "        <p style='font-size: 1.1em; line-height: 1.6; color: #666;'>\n" +
                "          Si tienes alguna duda, por favor contáctanos a través de nuestro\n" +
                "          correo electrónico\n" +
                "        </p>\n" +
                "        <hr />\n" +
                "        <p style='font-size: 1.1em; line-height: 1.6; color: #666;'>Gracias por confiar en nosotros</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div style='color: white; text-align: center; background-color: #457b9d; padding: 10px; border-radius: 5px; margin-top: 10px;'>\n" +
                "        <p style='color: white;'>\n" +
                "          Correo:\n" +
                "          <a style='color: white; text-decoration: none;' href=\"mailto:josuexsanta@gmail.com\">laparadaperfecta@gmail.com</a>\n" +
                "        </p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        String destinatario = Sesion._instance().getUsuario().getCorreoElectronico();
        String remitente = "jfqc120@gmail.com";
        String claveemail = "qxoo cxzf txli toxg";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(cuerpo, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
