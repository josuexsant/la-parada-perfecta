package view;

import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmarReserva extends JFrame {

    private JPanel panel1;
    private JPanel CReserva;
    private JLabel confirmarReservaLabel;
    private JLabel labelInfo;
    private JButton MenuButton;
    private ViewMenu menu;

    public ConfirmarReserva(){
        setContentPane(CReserva);
        dispose();
        mostrarMenu();
    }


    public void mostrarReserva(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
        Log.info("Muestra la informaciòn de la reserva");
    }

    public void mostrarMenu(){
        menu = new ViewMenu();
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInterfaz();
                Log.info("Se inicio vista Menù");
                dispose();
            }
        };
        MenuButton.addActionListener(accion);
    }

}
