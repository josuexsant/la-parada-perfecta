package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.CtrlTDC;
import model.Log;

public class RegistroTDC {
    private JPanel RegistroTarjeta;
    private JTextField TxtNombre;
    private JTextField TNumero;
    private JTextField TCvv;
    private JTextField TDir;
    private JButton finalizarButton;
    private JComboBox<String> mesBox;
    private JComboBox<String> Añobox;

    public void mostrarRegistroTDC() {
        JFrame frame = new JFrame("RegistroTDC");
        frame.setContentPane(new RegistroTDC().RegistroTarjeta);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public RegistroTDC() {
        llenarMesBox();
        llenarAñoBox();
        mesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesString = (String) mesBox.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesString);
            }
        });
        Confirmar();
    }

    //metodo para rellenar mesBox
    private void llenarMesBox() {
        for (int mes = 01; mes <= 12; mes++) {
            mesBox.addItem(String.valueOf(mes));
        }
    }

    private void llenarAñoBox() {
        for (int año = 2024; año <= 2040; año++) {
            Añobox.addItem(String.valueOf(año));
        }
    }

    private String construirInformacionSeleccionada(String nombre, String Numero, String año, String mes, String Cvv, String TDir) {
        String fechaExp = año + "-" + mes;
        return String.format("Nombre de usuario: %s <br><br> Numero : %s <br><br> Fecha de Expiracion: %s <br><br> CVV: %s <br><br> Direccion: %s <br><br>", nombre, Numero, fechaExp, Cvv, TDir);
    }

    private void Confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String NombreSeleccionado = TxtNombre.getText();
                String NumeroS = TNumero.getText();
                String CvvS = TCvv.getText();
                String TDrir = TDir.getText();
                String FechExp = (String) Añobox.getSelectedItem() + "-" + mesBox.getSelectedItem() + "-01";
                String mes = (String) mesBox.getSelectedItem();
                String año = (String) Añobox.getSelectedItem();

                CtrlTDC controladorTDC = new CtrlTDC();
                boolean registroExitoso = controladorTDC.registrarTDC(NumeroS, FechExp, CvvS, NombreSeleccionado, TDrir);

                if (registroExitoso) {
                    JOptionPane.showMessageDialog(null, "Registro de Tarjeta exitoso");
                    Log.info("Tarjeta registrada");
                    MostrarTDC mostrarTdc = new MostrarTDC();
                    mostrarTdc.setTitle("Confirmar Reserva");
                    mostrarTdc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mostrarTdc.setVisible(true);
                    mostrarTdc.setSize(900, 300);
                    mostrarTdc.setLocationRelativeTo(null);

                    String informacionSeleccionada = construirInformacionSeleccionada(NombreSeleccionado, NumeroS, año, mes, CvvS, TDrir);
                    mostrarTdc.mostrarInformacionSeleccionada(informacionSeleccionada);
                    ViewMenu inicioMenuFrame = new ViewMenu();
                    inicioMenuFrame.setTitle("Inicio");
                    inicioMenuFrame.setVisible(true);
                    inicioMenuFrame.setSize(300, 300);
                    inicioMenuFrame.setLocationRelativeTo(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la tarjeta");
                }
            }
        };
        finalizarButton.addActionListener(accion);
    }
}