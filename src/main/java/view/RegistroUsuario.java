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
            if(camposValidos()) {
                String nombre = nombretext.getText();
                String password = new String(passwordText.getPassword());
                String apellidoMaterno = apellidoMaternotext.getText();
                String apellidoPaterno = apellidoPaternotext.getText();
                String telefono = telefonotext.getText();
                String correo = correotext.getText();
                int idGenero = obtenerIdGenero((String) genero.getSelectedItem());
                int idCiudad = obtenerIdCiudad((String) ciudades.getSelectedItem());

                UIManager.put("OptionPane.yesButtonText", "Sí");
                UIManager.put("OptionPane.noButtonText", "No");
                int confirmacion = JOptionPane.showConfirmDialog(registroUsuariopanel, "¿La información que ingresaste es correcta?", "Confirmación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (ctrlUsuario.registrarUsuario(nombre, password, apellidoPaterno, apellidoMaterno, telefono, correo, idGenero, idCiudad)) {
                        UIManager.put("OptionPane.okButtonText", "Aceptar");
                        JOptionPane.showMessageDialog(registroUsuariopanel, "Registro Exitoso", "Registro de usuario", JOptionPane.INFORMATION_MESSAGE);

                        RegistroTDC registroTDC = new RegistroTDC();
                        registroTDC.mostrarInterfaz();
                        dispose();

                        Log.info("Registro de usuario");
                    } else {
                        UIManager.put("OptionPane.okButtonText", "Intentar de nuevo");
                        JOptionPane.showMessageDialog(registroUsuariopanel, "Error en el registro");
                    }
                }
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
            case "Aguascalientes":
                return 1;
            case "Baja California":
                return 2;
            case "Baja California Sur":
                return 3;
            case "Campeche":
                return 4;
            case "Chiapas":
                return 5;
            case "Chihuahua":
                return 6;
            case "Coahuila":
                return 7;
            case "Colima":
                return 8;
            case "Durango":
                return 9;
            case "Guanajuato":
                return 10;
            case "Guerrero":
                return 11;
            case "Hidalgo":
                return 12;
            case "Jalisco":
                return 13;
            case "México":
                return 14;
            case "Michoacán":
                return 15;
            case "Morelos":
                return 16;
            case "Nayarit":
                return 17;
            case "Nuevo León":
                return 18;
            case "Oaxaca":
                return 19;
            case "Puebla":
                return 20;
            case "Querétaro":
                return 21;
            case "Quintana Roo":
                return 22;
            case "San Luis Potosí":
                return 23;
            case "Sinaloa":
                return 24;
            case "Sonora":
                return 25;
            case "Tabasco":
                return 26;
            case "Tamaulipas":
                return 27;
            case "Tlaxcala":
                return 28;
            case "Veracruz":
                return 29;
            case "Yucatán":
                return 30;
            case "Zacatecas":
                return 31;
            default:
                return 0;
        }
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/agregar.gif");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_FAST);
        logo = new JLabel(new ImageIcon(image));
    }

    private boolean camposValidos() {
        if (nombretext.getText().trim().isEmpty() ||
                apellidoPaternotext.getText().trim().isEmpty() ||
                apellidoMaternotext.getText().trim().isEmpty() ||
                telefonotext.getText().trim().isEmpty() ||
                correotext.getText().trim().isEmpty() ||
                passwordText.getPassword().length == 0 ||
                genero.getSelectedIndex() == 0 ||
                ciudades.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(registroUsuariopanel, "Todos los campos son obligatorios");
            return false;
        }
        return true;
    }
}
