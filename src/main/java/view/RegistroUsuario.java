package view;

import controller.CtrlUsuario;
import model.Log;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.*;
import java.sql.SQLException;

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
    private CtrlUsuario ctrlUsuario;


    public RegistroUsuario() {
        ctrlUsuario = new CtrlUsuario();

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombretext.getText();
                String password = new String(passwordText.getPassword());
                String apellidoMaterno = apellidoMaternotext.getText();
                String apellidoPaterno = apellidoPaternotext.getText();
                String telefono = telefonotext.getText();
                String correo = correotext.getText();
                int idGenero = obtenerIdGenero((String) genero.getSelectedItem());
                int idCiudad = obtenerIdCiudad((String) ciudades.getSelectedItem());

                try {
                    if (ctrlUsuario.registrarUsuario(nombre, password, apellidoPaterno, apellidoMaterno, telefono, correo, idGenero, idCiudad)) {
                        JOptionPane.showMessageDialog(null, "Registro Exitoso");

                        dispose();
                        RegistroTDC registroTDC = new RegistroTDC();
                        registroTDC.mostrarInterfaz();
                        dispose();

                        Log.info("Registro de usuario");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el registro");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        nombretext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    JOptionPane.showMessageDialog(null, "Solo poner letras");

                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        apellidoPaternotext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    JOptionPane.showMessageDialog(null, "Solo poner letras");

                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });
        apellidoMaternotext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    JOptionPane.showMessageDialog(null, "Solo poner letras");

                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });
        ((AbstractDocument)telefonotext.getDocument()).setDocumentFilter(new DocumentFilter() {
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
                    JOptionPane.showMessageDialog(null, "Correo electrónico inválido");
                    correotext.requestFocus();
                }
            }
        });

        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(passwordText.getPassword());
                if (password.length() != 8) {
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener 8 caracteres");
                    passwordText.requestFocus();
                }
            }
        });
    }

    public void mostrarInterfaz() {
        JFrame frame = new JFrame("RegistroUsuario");
        frame.setContentPane(new RegistroUsuario().registroUsuariopanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        dispose();
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
        if ("Puebla".equals(ciudades)) {
            return 20;
        } else if ("México".equals(ciudades)) {
            return 14;
        } else if ("Veracruz".equals(ciudades)) {
            return 29;
        } else if ("Nayarit".equals(ciudades)) {
            return 17;
        } else {
            return 0;
        }
    }
}
