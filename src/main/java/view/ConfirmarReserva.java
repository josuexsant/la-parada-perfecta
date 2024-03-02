package view;

import controller.CtrlReserva;
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

    public ConfirmarReserva(){
        setContentPane(CReserva);
        dispose();
        BMenu();
    }
    public void mostrarInformacionSeleccionada(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
        Log.info("Muestra la informaciòn de la reserva");
    }

    public void BMenu(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ViewMenu VMenu = new ViewMenu();
                VMenu.setTitle("Regresar al menu");
                VMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                VMenu.setVisible(true);
                VMenu.setSize(300, 300);
                VMenu.setLocationRelativeTo(null);
                Log.info("Se inicio vista Menù");
                dispose();

            }
        };
        MenuButton.addActionListener(accion);
    }

}
