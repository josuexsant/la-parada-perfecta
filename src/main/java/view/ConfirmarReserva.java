package view;

import javax.swing.*;

public class ConfirmarReserva extends JFrame {

    private JPanel panel1;
    private JPanel CReserva;
    private JLabel confirmarReservaLabel;
    private JLabel labelInfo;

    ConfirmarReserva(){
        setContentPane(CReserva);
    }
    public void mostrarInformacionSeleccionada(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
    }
}
