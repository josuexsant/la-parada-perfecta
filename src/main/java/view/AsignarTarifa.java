package view;

import controller.CtrlTarifa;
import controller.CtrlUsuario;
import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AsignarTarifa extends JFrame {
    private JPanel asignarTarifaPanel;
    private JPanel tituloPanel;
    private JLabel tituloLabel;
    private JLabel tServicioLabel;
    private JLabel nuevaTarifaLabel;
    private JTextField tarifaText;
    private JComboBox tServicioBox;
    private JButton volverButton;
    private JButton aplicarButton;
    private JLabel tarifaActual;
    CtrlTarifa ctrlTarifa;

    public AsignarTarifa() {
        setContentPane(asignarTarifaPanel);
        ctrlTarifa = new CtrlTarifa();
        mostrarTarifa();

        tarifaText.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String text = textField.getText();
                return text.matches("\\d{1,5}(\\.\\d{0,2})?"); // Hasta 5 dígitos antes del punto y hasta 2 después
            }
        });
        tServicioBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AsignarTarifa.this.mostrarTarifa();
            }
        });

        aplicarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tarifaText.getText().matches("\\d+\\.\\d{2}")) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un número con dos decimales.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ctrlTarifa.modificarTarifa(Float.parseFloat(tarifaText.getText()), getConcepto());
                JOptionPane.showMessageDialog(null, "Tarifa modificada");
                CtrlUsuario ctrlUsuario = new CtrlUsuario();
                ctrlUsuario.cerrarSesion();
                InicioSesion sesion = new InicioSesion();
                sesion.mostrarInterfaz();
                dispose();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlUsuario ctrlUsuario = new CtrlUsuario();
                ctrlUsuario.cerrarSesion();
                InicioSesion sesion = new InicioSesion();
                sesion.mostrarInterfaz();
                dispose();
            }
        });

    }

    public String getConcepto() {
        switch (tServicioBox.getSelectedItem().toString()) {
            case "Tarifa por reserva":
                return "RESERVADO";
            case "Tarifa por ausencia":
                return "AUSENCIA";
            case "Tarifa tiempo excedido":
                return "EXCEDENTE";
        }
        return "";
    }

    public void mostrarTarifa() {
        Map<String, Double> tarifas = ctrlTarifa.verTarifas();
        tarifaActual.setText("$" + tarifas.get(getConcepto()) + " MXN");
    }

    public void mostrarInterfaz() {
        setContentPane(asignarTarifaPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500,600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Asignar tarifa");
    }
}
