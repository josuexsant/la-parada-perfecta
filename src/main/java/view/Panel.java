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

public class Panel extends JFrame {
    private JPanel Panel;
    private JTextField textField1;
    private JButton ingresarButton;
    private JTextArea textArea1;
    private JButton reservarButton;
    private CtrlPanel ctrlPanel;
    private CtrlUsuario ctrlUsuario;
    private Usuario usuario;

    public Panel() {
        ctrlPanel = new CtrlPanel();
        setContentPane(Panel);
        ingresar();
        mostrarInterfaz();
    }

    private void ingresar() {
        reservarButton.setEnabled(false);
        ActionListener accionIngresar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String membresia = textField1.getText();
                if (!membresia.isEmpty()) {
                    int membresiaInt = Integer.parseInt(membresia);
                    String texto = null;
                    try {
                        texto = ctrlPanel.verificarMembresiaUsuario(membresiaInt);
                        if (texto.startsWith("Bienvenido")) {
                            reservarButton.setEnabled(true);
                        } else {
                            reservarButton.setEnabled(false);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea1.setText(texto);
                } else {
                    textArea1.setText("Ingrese una membresia valida");
                    reservarButton.setEnabled(false);
                }
            }
        };
        ingresarButton.addActionListener(accionIngresar);

        ActionListener accionReservar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = null;
                String membresia = textField1.getText();
                int membresiaInt = Integer.parseInt(membresia);
                try {
                    texto = ctrlPanel.verificarMembresiaUsuario(membresiaInt);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (texto.startsWith("Bienvenido") && texto != null) {
                    Sesion._instance().setUsuario(new Usuario(membresiaInt));
                    Log.debug("Sesión iniciada: " + Sesion._instance().getUsuario().getCorreoElectronico());
                    ReservaImprevista reservaImprevista = new ReservaImprevista();
                    reservaImprevista.mostrarInterfaz();
                    dispose();
                } else {
                    textArea1.setText("Ingrese una membresia valida.");

                }
            }
        };
        reservarButton.addActionListener(accionReservar);
    }



        public void mostrarInterfaz() {
            setContentPane(Panel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocation(800, 400);
            setSize(500, 300);
            setResizable(false);
            setVisible(true);
            Log.info("Se inicia la vista Panel");
        }
}

