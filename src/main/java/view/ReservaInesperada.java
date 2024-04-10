package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;

import javax.swing.*;

public class ReservaInesperada {
    private JPanel RPanel;
    private JLabel nombreLabel;
    private JLabel nlabel;
    private JLabel MesLabel;
    private JComboBox comboBox3;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JSpinner DiaSpinner;
    private JSpinner MesSpinner;
    private JSpinner EntradaSpinner;
    private JSpinner SalidaSpinner;
    private ViewMenu menu;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;

    public  ReservaInesperada(){
        ctrlReserva = new CtrlReserva();

    }
}
