package view;

import controller.CtrlUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InicioSesion extends JFrame{

    private JPanel Inicio;
    private JLabel Correo;
    private JLabel Contraseña;
    private JButton registrarseButton;
    private JTextField textField2;
    private JButton ingresarButton;
    private JPasswordField passwordField1;
    private CtrlUsuario ctrlUsuario;

    private JFrame getFrame(){
        return this;
    }

    public InicioSesion() {

        ctrlUsuario = new CtrlUsuario();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correoElectronico = textField2.getText();
                String password = new String(passwordField1.getPassword());

                if (ctrlUsuario.iniciarSesion(correoElectronico, password)) {
                    // Inicio de sesión exitoso, podrías abrir una nueva ventana o realizar otras acciones
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    ViewMenu inicioMenuFrame = new ViewMenu();
                    inicioMenuFrame.setTitle("Inicio");
                    inicioMenuFrame.setVisible(true);
                    inicioMenuFrame.setSize(300, 300);
                    inicioMenuFrame.setLocationRelativeTo(null);
                } else {
                    // Inicio de sesión fallido, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "Inicio de sesión fallido");
                }
            }
        });


        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroUsuario registroUsuario = new RegistroUsuario();
                registroUsuario.mostrarRegistro();
            }
        });

    }


    public void mostrarInicio() {
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new InicioSesion().Inicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }


}
