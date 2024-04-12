package view;

import controller.CtrlPanel;
import controller.CtrlUsuario;
import model.Log;
import model.Sesion;
import model.SimulatedTime;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Panel extends JFrame{
    private JPanel Panel;
    private JTextField textField1;
    private JButton ingresarButton;
    private JTextArea textArea1;
    private JButton reservarButton;
    private CtrlPanel ctrlPanel;
    private CtrlUsuario ctrlUsuario;
    private Usuario usuario;
    public Panel(){
        ctrlPanel = new CtrlPanel();
        setContentPane(Panel);
        ingresar();
        mostrarInterfaz();
    }


    private void ingresar() {
        // ActionListener para el botón ingresarButton
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String membresia = textField1.getText();
                if (!membresia.isEmpty()) {
                    int membresiaInt = Integer.parseInt(membresia);
                    String texto = null;
                    try {
                        texto = ctrlPanel.verificarMembresiaUsuario(membresiaInt);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea1.setText(texto);
                } else {
                    textArea1.setText("Por favor, ingrese su membresía.");
                }
            }
        };
        ingresarButton.addActionListener(accion);

        // ActionListener para el botón aceptarButton
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String membresia = textField1.getText();
                if (!membresia.isEmpty()) {
                    int membresiaInt = Integer.parseInt(membresia);
                    String texto = null;
                    try {
                        texto = ctrlPanel.verificarMembresiaUsuario(membresiaInt);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Si la membresía es verificada con éxito se abre la ventana de reserva imprevista
                    if (texto != null && !texto.isEmpty()) {
                        Sesion._instance().setUsuario(new Usuario(membresiaInt));
                        Log.debug("Sesión iniciada: " + Sesion._instance().getUsuario().getCorreoElectronico());

                        ReservaImprevista reservaImprevista = new ReservaImprevista();
                        reservaImprevista.mostrarInterfaz();
                        dispose();
                    } else {
                        // Si no hay membresía verificada se muestra mensaje de error
                        textArea1.setText("Membresía no verificada. Por favor, inténtelo de nuevo.");
                    }
                } else {
                    textArea1.setText("Por favor, ingrese su membresía.");
                }
            }
        });
    }

    public void mostrarInterfaz() {
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(800,400);
        setSize(500, 300);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Panel");
    }
}
