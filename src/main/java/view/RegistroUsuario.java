package view;

import controller.CtrlUsuario;
import model.Log;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;

public class RegistroUsuario extends JFrame {
    private JPanel registroUsuariopanel;
    private JTextField nombretext;
    private JTextField apellidoPaternotext;
    private JTextField apellidoMaternotext;
    private JTextField telefonotext;
    private JTextField correotext;
    private JComboBox genero;
    private JPasswordField passwordText;
    private JComboBox ciudades;
    private JButton siguienteButton;
    private JButton cancelarButton;
    private JLabel logo;
    private CtrlUsuario ctrlUsuario;


    public RegistroUsuario() {
        ctrlUsuario = new CtrlUsuario();

        cancelarButton.addActionListener(e -> cancelar());

        siguienteButton.addActionListener(e -> {
            String nombre = nombretext.getText();
            String password = new String(passwordText.getPassword());
            String apellidoMaterno = apellidoMaternotext.getText();
            String apellidoPaterno = apellidoPaternotext.getText();
            String telefono = telefonotext.getText();
            String correo = correotext.getText();
            int idGenero = obtenerIdGenero((String) genero.getSelectedItem());
            int idCiudad = obtenerIdCiudad((String) ciudades.getSelectedItem());

            if (ctrlUsuario.registrarUsuario(nombre, password, apellidoPaterno, apellidoMaterno, telefono, correo, idGenero, idCiudad)) {
                JOptionPane.showMessageDialog(registroUsuariopanel, "Registro Exitoso");

                RegistroTDC registroTDC = new RegistroTDC();
                registroTDC.mostrarInterfaz();
                dispose();

                Log.info("Registro de usuario");
            } else {
                JOptionPane.showMessageDialog(registroUsuariopanel, "Error en el registro");
            }
        });

        nombretext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    Log.warn("Caracter no válido");
                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        apellidoPaternotext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    Log.warn("Caracter no válido");
                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        apellidoMaternotext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    Log.warn("Caracter no válido");
                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        telefonotext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || telefonotext.getText().length() > 10) {
                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        ((AbstractDocument) telefonotext.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException, BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                if (sb.toString().matches("\\d{0,10}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (sb.toString().matches("\\d{0,10}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        correotext.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String correo = correotext.getText();
                if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.com$")) {
                    JOptionPane.showMessageDialog(registroUsuariopanel, "Correo electrónico inválido");
                    correotext.requestFocus();
                }
            }
        });

        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(passwordText.getPassword());
                if (password.length() != 8) {
                    JOptionPane.showMessageDialog(registroUsuariopanel, "La contraseña debe tener 8 caracteres");
                    passwordText.requestFocus();
                }
            }
        });
    }

    private void cancelar(){
        InicioSesion sesion = new InicioSesion();
        sesion.mostrarInterfaz();
        dispose();
    }

    public void mostrarInterfaz() {
        setContentPane(registroUsuariopanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500,600);
        setResizable(false);
        setVisible(true);
        Log.info("Se carga interfaz de registro de usuario");
    }

    private int obtenerIdGenero(String genero) {
        if ("Masculino".equals(genero)) {
            return 1;
        } else if ("Femenino".equals(genero)) {
            return 2;
        } else {
            return 0;
        }
    }

    private int obtenerIdCiudad(String ciudades) {
        switch (ciudades) {
            case "Puebla":
                return 20;
            case "México":
                return 14;
            case "Veracruz":
                return 29;
            case "Nayarit":
                return 17;
            default:
                return 0;
        }
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/agregar.gif");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_FAST);
        logo = new JLabel(new ImageIcon(image));
    }
}
