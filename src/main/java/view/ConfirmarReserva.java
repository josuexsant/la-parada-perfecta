package view;

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
    }

    public void MostrarInicio(){
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new ConfirmarReserva().CReserva);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
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
            }
        };
        MenuButton.addActionListener(accion);
    }

}
