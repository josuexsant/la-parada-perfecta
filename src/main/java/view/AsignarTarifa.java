package view;

import javax.swing.*;

public class AsignarTarifa extends JFrame{
    private JPanel asignarTarifaPanel;
    private JPanel tituloPanel;
    private JLabel tituloLabel;
    private JLabel tServicioLabel;
    private JLabel nuevaTarifaLabel;
    private JTextField tarifaText;
    private JComboBox tServicioBox;
    private JButton volverButton;
    private JButton aplicarButton;


    public AsignarTarifa(){
        setContentPane(asignarTarifaPanel);
    }
}
