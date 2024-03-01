package view;

import javax.swing.*;

public class MostrarTDC extends JFrame {
    private JPanel panel1;
    private JLabel labelInfo;

    public MostrarTDC(){
        setContentPane(panel1);
    }
    public void mostrarInformacionSeleccionada(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
    }



}
