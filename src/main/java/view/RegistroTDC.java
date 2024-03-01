package view;

import javax.swing.*;

public class RegistroTDC {

    private JPanel RegistroTdcpn;
    private JTextField nombreTit;
    private JTextField num_tarjeta;
    private JTextField cvvtext;
    private JTextField dire_fact;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton finalizarButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegistroTDC");
        frame.setContentPane(new RegistroTDC().RegistroTdcpn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
