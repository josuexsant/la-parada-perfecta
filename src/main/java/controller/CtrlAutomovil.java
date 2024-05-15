package controller;

import model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Properties;

public class CtrlAutomovil {
    private static Automovil automovil;

    public LinkedList<String> getMatriculas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<String> matriculas = Automovil.getPlacas(idUsuario);
        return matriculas;
    }

    public String obtenerNombre() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        String nombre = usuario.getNombre();
        String apellidoP = usuario.getApellidoPaterno();
        String apellidoM = usuario.getApellidoMaterno();
        String nombreCompleto = nombre + " " + apellidoP + " " + apellidoM;
        return nombreCompleto;
    }

    public static Automovil getAutomovil() {
        return automovil;
    }

    /**
     * Metodo para cuando el usuario tiene varios autos asociados a su cuenta
     *
     * @return: Una lista de objetos tipo auto
     */
    public LinkedList<Automovil> automovilesRegistrados() {
        return null;
    }

    public LinkedList<String> obtenerMatriculas() throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<String> matriculas = Automovil.getPlacas(idUsuario);
        return matriculasString(matriculas);
    }

    public LinkedList<String> matriculasString(LinkedList<String> matriculas) throws SQLException {
        LinkedList<String> matriculasStrings = new LinkedList<>();

        for (String automovil : matriculas) {
            String matriculaString = "-Id: " + Automovil.getId() +
                    "  ,-Automovil: " + Automovil.obtenerMarca(Automovil.getId()) +
                    "  ,-Placa: " + Automovil.getPlaca();
            matriculasStrings.add(matriculaString);
        }
        return matriculasStrings;
    }

    public LinkedList<String> getMarcas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        automovil = new Automovil();
        int idMarca = automovil.getIdMarca();


        LinkedList<String> marcas = Automovil.obtenerMarcas();

        for (String marca : marcas) {
            System.out.println(marca);
        }
        return marcas;
    }


    public void agregarMatricula(int idMarca, String placa) {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        Automovil automovil1 = new Automovil();
        String nombreMarca = Automovil.obtenerMarca(idMarca);
        if (idMarca != 0) {
            automovil1.guardarAutomovil(idUsuario, idMarca, placa);
            System.out.println("Matrícula creada exitosamente.");
            enviarNotificacion("Matrícula creada", "Matrícula creada", "Se ha creado una nueva matrícula con la placa " + placa + " y la marca " + nombreMarca + ".");

        } else {
            System.out.println("No se pudo encontrar la marca especificada.");
        }
    }

    public boolean eliminarMatricula(String matricula) {
        Automovil automovil1 = new Automovil();
        enviarNotificacion("Matrícula eliminada", "Matrícula eliminada", "Se ha eliminado la matrícula con la placa " + matricula + ".");
        return automovil1.eliminarMatricula(matricula);
    }

    public boolean modificarMatricula(int nuevoIdMarca, String nuevaPlaca, String matricula) {
        int idAutomovil = Automovil.getIdConMatricula(matricula);
        Log.debug("id:" + idAutomovil);
        automovil = new Automovil(idAutomovil);
        if (automovil != null) {
            automovil.setIdMarca(nuevoIdMarca);
            Log.debug(String.valueOf(automovil.getIdMarca()));
            automovil.setPlaca(nuevaPlaca);
            Log.debug(automovil.getPlaca());
            automovil.modificarPlaca();
            enviarNotificacion("Matrícula modificada", "Matrícula modificada", "Se ha modificado la matrícula con la placa " + matricula + " por la placa " + nuevaPlaca + ".");
        } else {
            return false;
        }
        return false;
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


    /**
     * Metodo para cuando el usuario tiene varios autos asociados a su cuenta
     *
     * @return: Una lista de objetos tipo auto
     */


}
