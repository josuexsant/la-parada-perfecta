package view;
import controller.CtrlAutomovil;
import model.Log;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class RegistroMatricula extends JFrame{

    private JPanel registroPanel;
    private JPanel tituloPanel;
    private JLabel matriculaLabel;
    private JLabel marcaLabel;
    private JTextField matriculaText;
    private JComboBox<String> marcaComboBox;
    private JButton registrarButton;

    private CtrlAutomovil ctrlAutomovil;
    private GestionMatriculas gestionMatriculas;

    public RegistroMatricula() throws SQLException {
        ctrlAutomovil = new CtrlAutomovil();
        gestionMatriculas = new GestionMatriculas();
        setContentPane(registroPanel);
        obtenerMarcas();
        registrar();
    }

    public void obtenerMarcas() throws SQLException {
        LinkedList<String> marcas = ctrlAutomovil.getMarcas();
        for (String marca : marcas) {
            marcaComboBox.addItem(marca);
        }
    }

    public void registrar(){
        ctrlAutomovil = new CtrlAutomovil();
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String matriculaNueva = (String) matriculaText.getText();
                int marcaSeleccionada = obtenerIdMarca((String) marcaComboBox.getSelectedItem());

                if(marcaSeleccionada == 0){
                    Log.error("Por favor, seleccione una marca");
                }else{
                    ctrlAutomovil.agregarMatricula(marcaSeleccionada, matriculaNueva);
                        JOptionPane.showMessageDialog(null, "Registro exitoso");
                        dispose();
                    }
            }
        });
    }

    private int obtenerIdMarca(String marca){
        switch(marca){
            case "Toyota":
                return 1;
            case "Honda":
                return 2;
            case "Ford":
                return 3;
            case "Chevrolet":
                return 4;
            case "Nissan":
                return 5;
            case "Volkswagen":
                return 6;
            case "BMW":
                return 7;
            case "Mercedez-Benz":
                return 8;
            case "Audi":
                return 9;
            case "Hyundai":
                return 10;
            default:
                return 0;
        }
    }



}