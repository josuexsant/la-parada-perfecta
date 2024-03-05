package view;

import controller.CtrlUsuario;
import model.Log;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesion extends JFrame{

    private JPanel Inicio;
    private JLabel Correo;
    private JLabel Contraseña;
    private JButton registrarseButton;
    private JTextField textField2;
    private JButton ingresarButton;
    private JPasswordField passwordField1;
    private CtrlUsuario ctrlUsuario;
    private ViewMenu menu;

    public InicioSesion() {

        ctrlUsuario = new CtrlUsuario();
        menu = new ViewMenu();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correoElectronico = textField2.getText();
                String password = new String(passwordField1.getPassword());


                if (ctrlUsuario.iniciarSesion(correoElectronico, password)) {
                    // Inicio de sesión exitoso, podrías abrir una nueva ventana o realizar otras acciones
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    menu.mostrarInicioMenuFrame();
                    dispose();
                    Log.success("Sesion Exitosa");
                } else {
                    if (ctrlUsuario.iniciarSesion(correoElectronico, password)) {
                        // Inicio de sesión exitoso, podrías abrir una nueva ventana o realizar otras acciones
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                        menu.mostrarInicioMenuFrame();
                        dispose();
                        Log.success("Sesión Exitosa");
                    } else {
                        // Inicio de sesión fallido, mostrar un mensaje de error
                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido");
                        Log.error("Fallo en el inicio de sesión");
                    }
                }
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroUsuario registroUsuario = new RegistroUsuario();
                registroUsuario.mostrarRegistro();
                dispose();
            }
        });
    }

    /**
     * Vista para inicio de sesión
     */
    public void mostrarInicio() {
        setContentPane(Inicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        Log.info("Se inicia la vista Inicio de sesion");
    }
}
