package org.example;

import javax.swing.*;

public class RegistroTDC {

    private JPanel RegistroTarjeta;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton finalizarButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegistroTDC");
        frame.setContentPane(new RegistroTDC().RegistroTarjeta);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
