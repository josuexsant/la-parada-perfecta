package view;

import model.Automovil;
import model.Log;
import model.Reserva;

import javax.swing.*;
import java.awt.*;

public class ConfirmarReserva extends JFrame {

    private JPanel panel;
    private JButton menuButton;
    private JLabel img;
    private JLabel idTextfield;
    private JLabel horaInicioTextfield;
    private JLabel horaFinTextfield;
    private JLabel matriculaTextfield;
    private JLabel fechaTextfield;
    private Reserva reserva;

    public ConfirmarReserva(Reserva reserva) {
        this.reserva = reserva;

        mostrarReserva();
        menuButton.addActionListener(e -> mostrarMenu());
    }

    public void mostrarReserva() {
        Automovil automovil = new Automovil(reserva.getIdAutomovil());
        idTextfield.setText(String.valueOf(reserva.getId()));
        fechaTextfield.setText(reserva.getFecha());
        horaInicioTextfield.setText(reserva.getHoraInicio());
        horaFinTextfield.setText(reserva.getHoraFin());
        matriculaTextfield.setText(Automovil.getPlaca());
    }

    public void mostrarMenu() {
        ViewMenu menu = new ViewMenu();
        menu.mostrarInterfaz();
        Log.info("Se inicio vista Menù");
        dispose();
    }

    public void mostrarInterfaz() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Confirmar Reserva");
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/confirmacion.png");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));
    }
}
