package view;

import model.Automovil;
import model.Log;
import model.Reserva;

import javax.swing.*;
import java.awt.*;

public class ConfirmarReservaImprevista extends JFrame {

    private JPanel panel;
    private JLabel idTextfield;
    private JLabel horaInicioTextfield;
    private JLabel horaFinTextfield;
    private JLabel matriculaTextfield;
    private JLabel fechaTextfield;
    private JButton salirButton;
    private Reserva reserva;

    public ConfirmarReservaImprevista(Reserva reserva) {
        this.reserva = reserva;
        mostrarReserva();
        salirButton.addActionListener(e->salir());
    }

    public void mostrarReserva() {
        Automovil automovil = new Automovil(reserva.getIdAutomovil());
        idTextfield.setText(String.valueOf(reserva.getId()));
        fechaTextfield.setText(reserva.getFecha());
        horaInicioTextfield.setText(reserva.getHoraInicio());
        horaFinTextfield.setText(reserva.getHoraFin());
        matriculaTextfield.setText(Automovil.getPlaca());
    }

    public void mostrarInterfaz() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Confirmar Reserva Imprevista");
    }

    private void salir(){
        Panel panel = new Panel();
        panel.mostrarInterfaz();
        Log.info("Se inicio vista Panel");
        dispose();
    }

}
