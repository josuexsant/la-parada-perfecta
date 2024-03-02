package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private void CrearReserva(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                ResgitroReserva RegistroFrame = new ResgitroReserva();
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
