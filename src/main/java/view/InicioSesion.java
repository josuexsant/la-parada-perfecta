package view;

import controller.CtrlOperador;
import controller.CtrlUsuario;
import model.Log;
import java.util.Locale;

import javax.swing.*;
import java.awt.*;

public class InicioSesion extends JFrame {
    private JPanel inicio;
    private JLabel email;
    private JLabel password;
    private JButton registrarseButton;
    private JTextField emailText;
    private JButton ingresarButton;
    private JPasswordField passwordText;
    private JLabel inicioDeSesiónLabel;
    private JButton soyOperadorButton;
    private JLabel logo;
    private JLabel background;
    private CtrlUsuario ctrlUsuario;
    private ViewMenu menu;

    public InicioSesion() {
        Locale.setDefault(new Locale("es", "ES"));
        ctrlUsuario = new CtrlUsuario();
        menu = new ViewMenu();

        ingresarButton.addActionListener(e -> iniciarSesion());
        registrarseButton.addActionListener(e -> registrarUsuario());
        soyOperadorButton.addActionListener(e -> iniciarSesionOperador());
    }

    private void iniciarSesion() {
        String correoElectronico = emailText.getText();
        String password = new String(passwordText.getPassword());

        if (correoElectronico.isEmpty() || password.isEmpty()) {
            UIManager.put("OptionPane.okButtonText", "Aceptar");
            JOptionPane.showMessageDialog(inicio, "Ingrese un correo ó contraseña validos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ctrlUsuario.iniciarSesion(correoElectronico, password)) {
            UIManager.put("OptionPane.okButtonText", "Aceptar");
            JOptionPane.showMessageDialog(inicio, "Inicio de sesión exitoso", "Inicio de sesion", JOptionPane.INFORMATION_MESSAGE);
            menu.mostrarInterfaz();
            dispose();
            Log.success("Sesion Exitosa");
        } else {
            UIManager.put("OptionPane.okButtonText", "Intentar de nuevo");
            JOptionPane.showMessageDialog(inicio, "Inicio de sesión fallido", "Error", JOptionPane.ERROR_MESSAGE);
            Log.error("Fallo en el inicio de sesión");
        }
    }

    private void registrarUsuario() {
        RegistroUsuario registroUsuario = new RegistroUsuario();
        registroUsuario.mostrarInterfaz();
        dispose();
    }

    private void iniciarSesionOperador() {
        CtrlOperador ctrlOperador = new CtrlOperador();
        if (emailText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            UIManager.put("OptionPane.okButtonText", "Intentar de nuevo");
            JOptionPane.showMessageDialog(inicio, "Ingrese un correo ó contraseña validos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ctrlOperador.iniciarSesion(emailText.getText(), passwordText.getText())) {
            Thread hiloLoading = new Thread();
            hiloLoading.start();
            Loading loading = new Loading("Cargando...");
            loading.mostrarInterfaz(100);
            AsignarTarifa asignarTarifa = new AsignarTarifa();
            asignarTarifa.mostrarInterfaz();
            dispose();
        } else {
            setLocation(100,100);
            UIManager.put("OptionPane.okButtonText", "Intentar de nuevo");
            JOptionPane.showMessageDialog(inicio, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarInterfaz() {
        setContentPane(inicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Inicio de sesion");
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/usuario.png");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logo = new JLabel(new ImageIcon(image));
    }
}
