package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import model.Automovil;
import model.Log;
import model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class CancelarReserva extends JFrame {
    private JPanel PCancelar;
    private JList JListReserva;
    private JButton CancelarButton;
    private JButton cancelarReservaButton;
    private JLabel img;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;
    private ViewMenu menu;

    public CancelarReserva(){
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(PCancelar);
        InsertarReservas();
        Cancelar();
        Confirmar();
        obtenerIndiceSeleccionado();

    }
    public void InsertarReservas() {
        // Obtener el modelo de lista actual de JListReserva
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JListReserva.setModel(listModel); // Establecer el modelo en el JList

        LinkedList<Reserva> reservasList = ctrlReserva.getList();
        Automovil automovil;
        // Agregar cada reserva al modelo de lista
        for (Reserva reserva : reservasList) {
            automovil = new Automovil(reserva.getIdAutomovil());
            listModel.addElement("ID: " + reserva.getId() + " Fecha: " + reserva.getFecha() + " Matricula: " + automovil.getPlaca());
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
                menu.mostrarInterfaz();
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
                menu.mostrarInterfaz();
                dispose();
                Log.success("Cancelacion Exitosa");
                }

        };
        cancelarReservaButton.addActionListener(accion);
    }

    public void mostrarInterfaz() {
        setContentPane(PCancelar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Cancelar Reserva");
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/cancelar.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));
    }
}