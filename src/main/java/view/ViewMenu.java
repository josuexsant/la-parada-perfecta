package view;

import controller.CtrlUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ViewMenu extends JFrame {
    private JPanel PMenu;
    private JPanel pnMenu;
    private JButton crearReservaButton;
    private JButton modificarReservaButton;
    private JButton eliminarReservaButton;
    private JButton gestionMatriculasButton;
    private JButton crearReservaGarantizadaButton;
    private JButton reservasList;
    private JButton cerrarSesíonButton;

    private JButton verMatriculaButton;

    public ViewMenu() {
        setContentPane(pnMenu);
        CrearReserva();
        CancelarReserva();
        ModificarReserva();
        ReservaGarantizada();
        GestionMatriculas();
        setCerrarSesíonButton();
    }

    public void mostrarInterfaz() {
        setTitle("Inicio");
        setVisible(true);
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    private void CrearReserva() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RegistroReserva RegistroFrame;

                try {
                    RegistroFrame = new RegistroReserva();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                dispose();
                RegistroFrame.setTitle("Confirmar Reserva");
                RegistroFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                RegistroFrame.setVisible(true);
                RegistroFrame.setSize(900, 300);
                RegistroFrame.setLocationRelativeTo(null);
                dispose();
            }
        };
        crearReservaButton.addActionListener(accion);
    }

    private void CancelarReserva() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CancelarReserva CancelarFrame;

                try {
                    CancelarFrame = new CancelarReserva();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                dispose();
                CancelarFrame.setTitle("Confirmar Reserva");
                CancelarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                CancelarFrame.setVisible(true);
                CancelarFrame.setSize(900, 300);
                CancelarFrame.setLocationRelativeTo(null);
                dispose();
            }
        };
        eliminarReservaButton.addActionListener(accion);
    }

    private void ModificarReserva() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ModificarReserva ModificarFrame;

                try {
                    ModificarFrame = new ModificarReserva();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                dispose();
                ModificarFrame.setTitle("Confirmar Reserva");
                ModificarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ModificarFrame.setVisible(true);
                ModificarFrame.setSize(900, 300);
                ModificarFrame.setLocationRelativeTo(null);
                dispose();
            }
        };
        modificarReservaButton.addActionListener(accion);
    }

    private void ReservaGarantizada() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReservaGarantizada GarantizadaFrame;
                GarantizadaFrame = new ReservaGarantizada();
                dispose();
                GarantizadaFrame.setTitle("Confirmar Reserva");
                GarantizadaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GarantizadaFrame.setVisible(true);
                GarantizadaFrame.setSize(900, 300);
                GarantizadaFrame.setLocationRelativeTo(null);
                dispose();
            }
        };
        crearReservaGarantizadaButton.addActionListener(accion);
    }

    public void setCerrarSesíonButton() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlUsuario ctrlUsuario = new CtrlUsuario();
                InicioSesion sesion = new InicioSesion();
                if (ctrlUsuario.cerrarSesion()) {
                    JOptionPane.showMessageDialog(null, "Sesión cerrada");
                    dispose();
                    sesion.mostrarInterfaz();
                }
            }
        };
        cerrarSesíonButton.addActionListener(actionListener);
    }

    private void GestionMatriculas(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GestionMatriculas GestionFrame;
                try {
                    GestionFrame = new GestionMatriculas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                dispose();
                GestionFrame.setTitle("Gestion de Matriculas");
                GestionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GestionFrame.setVisible(true);
                GestionFrame.setSize(900, 300);
                GestionFrame.setLocationRelativeTo(null);
                dispose();

            }
        };
        gestionMatriculasButton.addActionListener(accion);
    }
}
