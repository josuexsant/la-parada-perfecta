package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ViewMenu extends JFrame{
    private JPanel  PMenu;
    private JPanel  PnMenu;
    private JButton crearReservaButton;
    private JButton modificarReservaButton;
    private JButton eliminarReservaButton;
    private JButton agregaMatriculaButton;
    private JButton modificarMatriculaButton;
    private JButton verMatriculaButton;

    public ViewMenu(){
        setContentPane(PnMenu);
        CrearReserva();
    }
    public void mostrarInicioMenuFrame() {
        setTitle("Inicio");
        setVisible(true);
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    private void CrearReserva(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                ResgitroReserva RegistroFrame = null;
                try {
                    RegistroFrame = new ResgitroReserva();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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





}
