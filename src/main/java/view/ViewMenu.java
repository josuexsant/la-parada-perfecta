package view;

import controller.CtrlUsuario;

import javax.swing.*;
import java.awt.*;

public class ViewMenu extends JFrame {
    private JPanel PMenu;
    private JPanel pnMenu;
    private JButton crearReservaButton;
    private JButton modificarReservaButton;
    private JButton eliminarReservaButton;
    private JButton gestionMatriculasButton;
    private JButton crearReservaGarantizadaButton;
    private JButton cerrarSesionButton;
    private JLabel img;

    public ViewMenu() {
        crearReservaButton.addActionListener(e -> crearReserva());
        eliminarReservaButton.addActionListener(e -> cancelarReserva());
        modificarReservaButton.addActionListener(e -> modificarReserva());
        crearReservaGarantizadaButton.addActionListener(e -> reservaGarantizada());
        gestionMatriculasButton.addActionListener(e -> gestionMatriculas());
        cerrarSesionButton.addActionListener(e -> cerrarSesion());
    }

    public void mostrarInterfaz() {
        setContentPane(pnMenu);
        setTitle("Inicio");
        setVisible(true);
        pack();
        setResizable(false);
        setSize(500, 600);
        setLocation(100, 100);
    }

    private void crearReserva() {
        ResgitroReserva view = new ResgitroReserva();
        view.mostrarInterfaz();
        dispose();
    }

    private void cancelarReserva() {
        CancelarReserva view = new CancelarReserva();
        view.mostrarInterfaz();
        dispose();
    }

    private void modificarReserva() {
        ModificarReserva view = new ModificarReserva();
        view.mostrarInterfaz();
        dispose();
    }

    private void reservaGarantizada() {
        ReservaGarantizada view = new ReservaGarantizada();
        view.mostrarInterfaz();
        dispose();
    }

    public void cerrarSesion() {
        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        InicioSesion sesion = new InicioSesion();
        if (ctrlUsuario.cerrarSesion()) {
            JOptionPane.showMessageDialog(pnMenu, "Sesión cerrada");
            dispose();
            sesion.mostrarInterfaz();
        }
    }

    private void gestionMatriculas() {
        GestionMatriculas view = new GestionMatriculas();
        view.mostrarInterfaz();
        dispose();
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/menu.png");
        Image image = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));
    }
}
