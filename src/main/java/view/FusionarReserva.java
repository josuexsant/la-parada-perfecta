package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import model.Log;
import model.Reserva;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class FusionarReserva extends JFrame{
    private JPanel fusionarPanel;
    private JList reservasList;
    private JPanel tituloPanel;
    private JButton volverButton;
    private JButton fusionarButton;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;
    private ViewMenu menu;


    public FusionarReserva() throws SQLException {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();

        setContentPane(fusionarPanel);
        InsertarReservas();
        volver();
        //fusionar();
    }

    public void InsertarReservas() throws SQLException {
        // Obtener el modelo de lista actual de JListReserva
        DefaultListModel<String> listModel = (DefaultListModel<String>) reservasList.getModel();

        // Obtener las reservas del controlador
        LinkedList<String> reservas = ctrlReserva.obtenerReservas();

        // Agregar cada reserva al modelo de lista
        for (String reserva : reservas) {
            listModel.addElement(reserva);
        }
    }

    public void volver() {
        menu = new ViewMenu();
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInterfaz();
                dispose();
            }
        };
        volverButton.addActionListener(accion);
        dispose();
    }

}
