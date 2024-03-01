package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroTDC {

    private JPanel RegistroTarjeta;
    private JTextField TxtNombre;
    private JTextField TNumero;
    private JTextField TCvv;
    private JTextField TDir;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton finalizarButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegistroTDC");
        frame.setContentPane(new RegistroTDC().RegistroTarjeta);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public RegistroTDC(){
        Confirmar();
    }


    private String construirInformacionSeleccionada(String nombre, String Numero, String Cvv, String TDir) {
        return String.format("Nombre de usuario: %s <br><br> Numero : %s <br><br> CVV: %s <br><br> Direccion: %s <br><br>",nombre, Numero, Cvv,TDir);
    }
    private void Confirmar(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String NombreSeleccionado =  TxtNombre.getText();
                String NumeroS = TNumero.getText();
                String CvvS = TCvv.getText();
                String TDrir = TDir.getText();

                MostrarTDC mostrarTdc = new MostrarTDC();
                mostrarTdc.setTitle("Confirmar Reserva");
                mostrarTdc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mostrarTdc.setVisible(true);
                mostrarTdc.setSize(900, 300);
                mostrarTdc.setLocationRelativeTo(null);

                String informacionSeleccionada = construirInformacionSeleccionada(NombreSeleccionado,NumeroS,CvvS,TDrir);
                mostrarTdc.mostrarInformacionSeleccionada(informacionSeleccionada);
            }
        };
        finalizarButton.addActionListener(accion);

    }



}
