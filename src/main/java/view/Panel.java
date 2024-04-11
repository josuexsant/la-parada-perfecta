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
    private JButton aceptarButton;
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
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String membresia = textField1.getText();
                if (!membresia.isEmpty()) {
                    int membresiaInt = Integer.parseInt(membresia);
                    String texto = null;
                    try {
                        texto = ctrlPanel.verificarMembresiaUsuario(membresiaInt);
                        // Si la membresía es verificada con éxito, abrir la ventana de reserva imprevista
                        if (texto != null && !texto.isEmpty()) {
                            Sesion._instance().setUsuario(new Usuario(Integer.parseInt(membresia)));
                            Log.debug("Sesion iniciada: " + Sesion._instance().getUsuario().getCorreoElectronico());
                            // Crear instancia de la ventana de reserva imprevista
                            ReservaImprevista reservaImprevista = new ReservaImprevista();
                            // Mostrar la interfaz de la ventana de reserva imprevista
                            reservaImprevista.mostrarInterfaz();
                            // Cerrar la ventana actual (de inicio de sesión)
                            dispose();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea1.setText(texto); // Mostrar en el JTextArea
                } else {
                    textArea1.setText("Por favor, ingrese su membresía."); // Mostrar mensaje en el JTextArea
                }
            }
        };
        ingresarButton.addActionListener(accion);

        // ActionListener para el botón aceptarButton
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el JTextField y el JTextArea
                textField1.setText("");
                textArea1.setText("");
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
        Log.info("Se inicia la vista Inicio de sesion");
    }
}
