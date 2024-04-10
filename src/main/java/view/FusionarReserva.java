package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class FusionarReserva extends JFrame{
    private JPanel fusionarPanel;
    private JList reservasList;
    private JPanel tituloPanel;
    private JButton cancelarButton;
    private JButton fusionarButton;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;
    private ViewMenu menu;


    public FusionarReserva() throws SQLException {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(fusionarPanel);
        cancelar();
        //fusionar();
    }

    public void cancelar() {
        menu = new ViewMenu();
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInterfaz();
                dispose();
            }
        };
        cancelarButton.addActionListener(accion);
        dispose();
    }

    public void fusionar(){
        
    }

}
