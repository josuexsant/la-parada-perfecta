package view;

import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarTDC extends JFrame {
    private JPanel panel1;
    private JLabel labelInfo;
    private JButton MenuButton;

    public MostrarTDC(){
        setContentPane(panel1);
    }
    public void mostrarInformacionSeleccionada(String informacion) {
        labelInfo.setText("<html>" + informacion + "</html>");
        Log.info("Registro de TDC con exito");
    }

    public void mostrarMenu(){
        ViewMenu menu = new ViewMenu();
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInicioMenuFrame();
                Log.info("Se inicio vista Menù");
                dispose();
            }
        };
        MenuButton.addActionListener(accion);
    }
}
