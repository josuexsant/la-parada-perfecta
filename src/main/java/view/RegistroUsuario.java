package view;

import javax.swing.*;

public class RegistroUsuario {


    private JPanel RegistroUsuario;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JPasswordField passwordField1;
    private JComboBox comboBox2;
    private JButton siguienteButton;

    public void mostrarRegistro() {
        JFrame frame = new JFrame("RegistroUsuario");
        frame.setContentPane(new RegistroUsuario().RegistroUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

}
