package view;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.CtrlTDC;
import model.Log;

public class RegistroTDC extends  JFrame{
    private JPanel registroTarjeta;
    private JTextField nombreText;
    private JTextField numeroText;
    private JTextField cvvText;
    private JTextField direccionText;
    private JButton finalizarButton;
    private JComboBox<String> mesBox;
    private JComboBox<String> yearBox;

    public void mostrarInterfaz() {
        setContentPane(new RegistroTDC().registroTarjeta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public RegistroTDC() {
        llenarMesBox();
        llenarYearBox();
        mesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesString = (String) mesBox.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesString);
            }
        });
        confirmar();
    }

    //metodo para rellenar mesBox
    private void llenarMesBox() {
        for (int mes = 01; mes <= 12; mes++) {
            mesBox.addItem(String.valueOf(mes));
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

                CtrlTDC controladorTDC = new CtrlTDC();
                boolean registroExitoso = controladorTDC.registrarTDC(NumeroS, FechExp, CvvS, NombreSeleccionado, TDrir);

                if (registroExitoso) {
                    JOptionPane.showMessageDialog(null, "Registro de Tarjeta exitoso");
                    Log.info("Tarjeta registrada");

                    dispose();
                    MostrarTDC mostrarTdc = new MostrarTDC();
                    mostrarTdc.setTitle("Confirmar Reserva");
                    mostrarTdc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mostrarTdc.setVisible(true);
                    mostrarTdc.setSize(300, 250);
                    mostrarTdc.setLocationRelativeTo(null);

                    String informacionSeleccionada = construirInformacionSeleccionada(NombreSeleccionado, NumeroS, año, mes, CvvS, TDrir);
                    mostrarTdc.mostrarInformacionSeleccionada(informacionSeleccionada);


                    ViewMenu inicioMenuFrame = new ViewMenu();
                    inicioMenuFrame.mostrarInterfaz();
                    dispose();


                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la tarjeta");
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

        ((AbstractDocument)numeroText.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException, BadLocationException {
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

        ((AbstractDocument)cvvText.getDocument()).setDocumentFilter(new DocumentFilter() {
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
}