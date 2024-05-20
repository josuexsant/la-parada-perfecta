package view;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.CtrlTDC;
import controller.CtrlUsuario;
import model.Log;

public class RegistroTDC extends JFrame {
    private JPanel registroTarjeta;
    private JTextField nombreText;
    private JTextField numeroText;
    private JPasswordField cvvText;
    private JTextField direccionText;
    private JButton finalizarButton;
    private JComboBox<String> mesBox;
    private JComboBox<String> yearBox;
    private JButton cancelarButton;
    private JLabel logo;

    public void mostrarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            setContentPane(registroTarjeta);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocation(100, 100);
            setSize(500, 600);
            setResizable(false);
            setVisible(true);
            Log.info("Se muestra RegistroTDC");
        });
    }

    public RegistroTDC() {
        llenarMesBox();
        llenarYearBox();

        cancelarButton.addActionListener(e -> cancelar());
        mesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesString = (String) mesBox.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesString);
            }
        });
        confirmar();
    }

    private void llenarMesBox() {
        for (int mes = 1; mes <= 12; mes++) {
            mesBox.addItem(String.format("%02d", mes));
        }
    }

    private void llenarYearBox() {
        for (int año = 2024; año <= 2040; año++) {
            yearBox.addItem(String.valueOf(año));
        }
    }

    private String construirInformacionSeleccionada(String nombre, String Numero, String año, String mes, String Cvv, String TDir) {
        String fechaExp = año + "-" + mes;
        return String.format("Nombre de usuario: %s <br><br> Numero : %s <br><br> Fecha de Expiracion: %s <br><br> CVV: %s <br><br> Direccion: %s <br><br>", nombre, Numero, fechaExp, Cvv, TDir);
    }

    private void confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String NombreSeleccionado = nombreText.getText();
                String NumeroS = numeroText.getText();
                String CvvS = cvvText.getText();
                String TDrir = direccionText.getText();
                String FechExp = (String) yearBox.getSelectedItem() + "-" + mesBox.getSelectedItem() + "-01";
                String mes = (String) mesBox.getSelectedItem();
                String año = (String) yearBox.getSelectedItem();

                UIManager.put("OptionPane.yesButtonText", "Sí");
                UIManager.put("OptionPane.noButtonText", "No");
                int confirmacion = JOptionPane.showConfirmDialog(registroTarjeta, "¿La información que ingresaste es correcta?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if(confirmacion == JOptionPane.YES_OPTION){
                    if(NombreSeleccionado.isEmpty() || NumeroS.isEmpty() || CvvS.isEmpty() || TDrir.isEmpty()){
                        UIManager.put("OptionPane.okButtonText", "Volver a intentar");
                        JOptionPane.showMessageDialog(registroTarjeta, "Debes ingresar datos validos", "Error", JOptionPane.ERROR_MESSAGE);
                    } else{
                        CtrlTDC controladorTDC = new CtrlTDC();
                        boolean registroExitoso = controladorTDC.registrarTDC(NumeroS, FechExp, CvvS, NombreSeleccionado, TDrir);
                        Loading view = new Loading("Verificando tarjeta");
                        view.mostrarInterfaz(10000);

                        Timer timer = new Timer(10000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (registroExitoso) {
                                    UIManager.put("OptionPane.okButtonText", "Aceptar");
                                    JOptionPane.showMessageDialog(registroTarjeta, "Registro de tarjeta exitoso", "Registro de tarjeta", JOptionPane.INFORMATION_MESSAGE);
                                    Log.info("Tarjeta registrada");
                                    ViewMenu inicioMenuFrame = new ViewMenu();
                                    inicioMenuFrame.mostrarInterfaz();
                                    dispose();
                                } else {
                                    UIManager.put("OptionPane.okButtonText", "Aceptar");
                                    JOptionPane.showMessageDialog(registroTarjeta, "Error al registrar la tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                        timer.setRepeats(false); // Para que solo se ejecute una vez
                        timer.start();
                    }
                }
            }
        };

        nombreText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    e.consume(); // Consumir el evento para evitar que se escriba el carácter
                }
            }
        });

        ((AbstractDocument) numeroText.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);

                if (sb.toString().matches("\\d{0,16}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);

                if (sb.toString().matches("\\d{0,16}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        ((AbstractDocument) cvvText.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException, BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);

                if (sb.toString().matches("\\d{0,13}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);

                if (sb.toString().matches("\\d{0,3}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        finalizarButton.addActionListener(accion);
    }

    private void cancelar() {
        if (JOptionPane.showConfirmDialog(registroTarjeta, "¿Está seguro que desea salir?", "Salir",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            CtrlUsuario ctrlUsuario = new CtrlUsuario();
            ctrlUsuario.eliminar();
            InicioSesion sesion = new InicioSesion();
            sesion.mostrarInterfaz();
            dispose();
        }
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/tdc.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new JLabel(new ImageIcon(image));
    }
}
