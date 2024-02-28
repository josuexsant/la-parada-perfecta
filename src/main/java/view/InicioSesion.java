package org.example;

import javax.swing.*;

public class InicioSesion {

    private JPanel Inicio;
    private JLabel Correo;
    private JLabel Contraseña;
    private JButton registrarseButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton ingresarButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new InicioSesion().Inicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }


}
