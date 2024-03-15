package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import model.Log;
import model.Reserva;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class CancelarReserva extends JFrame {
    private JPanel PCancelar;
    private JList JListReserva;
    private JButton CancelarButton;
    private JButton cancelarReservaButton;
    private JPanel PTitulo;
    private JLabel Titulo;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;
    private ViewMenu menu;

    public CancelarReserva() throws SQLException{
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(PCancelar);
        InsertarReservas();
        Cancelar();
        Confirmar();
        obtenerIndiceSeleccionado();

    }
    public void InsertarReservas() throws SQLException {
        // Obtener el modelo de lista actual de JListReserva
        DefaultListModel<String> listModel = (DefaultListModel<String>) JListReserva.getModel();

        // Obtener las reservas del controlador
        LinkedList<String> reservas = ctrlReserva.obtenerReservas();

        // Agregar cada reserva al modelo de lista
        for (String reserva : reservas) {
            listModel.addElement(reserva);
        }
    }


    public int obtenerIndiceSeleccionado() {
        return JListReserva.getSelectedIndex();
    }

    private String construirInformacionSeleccionada(String nombre, String fecha, String horaLlegada, String horaSalida) {
        return String.format("Nombre de usuario: %s <br><br> Fecha: %s <br><br> Hora Llegada: %s <br><br> Hora Salida: %s <br><br>", nombre, fecha, horaLlegada, horaSalida);
    }


    public void Cancelar() {
        menu = new ViewMenu();

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInicioMenuFrame();
                dispose();
            }
        };
        CancelarButton.addActionListener(accion);
        dispose();
    }

    private void Confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int optionSelected = JListReserva.getSelectedIndex();


                Reserva reserva = null;
                try {
                    reserva = ctrlReserva.obtenerReservaPorIndice(optionSelected);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                int idReserva = reserva.getId();
                ctrlReserva.eliminarReservaSelccionada(idReserva);
                JOptionPane.showMessageDialog(null, "Cancelacion exitosa");
                menu.mostrarInicioMenuFrame();
                dispose();
                Log.success("Cancelacion Exitosa");
                }

        };
        cancelarReservaButton.addActionListener(accion);
    }

}