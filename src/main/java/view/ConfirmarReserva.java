package view;

import javax.swing.*;

public class ConfirmarReserva extends JFrame {

    private JPanel panel1;
    private JPanel CReserva;
    private JLabel confirmarReservaLabel;
    private JLabel labelInfo;

    public ConfirmarReserva(){
        setContentPane(CReserva);
    }
    public void mostrarInformacionSeleccionada(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
    }

    public void MostrarInicio(){
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new ConfirmarReserva().CReserva);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
