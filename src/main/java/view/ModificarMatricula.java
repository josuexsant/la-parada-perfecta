package view;

import controller.CtrlAutomovil;
import model.Automovil;
import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class ModificarMatricula extends JFrame{
    private JPanel tituloPanel;
    private JLabel matricuiaLabel;
    private JLabel marcaLabel;
    private JTextField matriculaText;
    private JComboBox marcaComboBox;
    private JButton modificarButton;
    private JLabel tituloLabel;
    private JLabel matriculaField;
    private JPanel modificarPanel;
    private GestionMatriculas gestionMatriculas;

    private CtrlAutomovil ctrlAutomovil;
    private Automovil automovil;

    private String matriculaSeleccionada;

    public ModificarMatricula(String matriculaSeleccionada) throws SQLException {
        this.matriculaSeleccionada = matriculaSeleccionada;
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(modificarPanel);
        obtenerMarcas();
        setMatriculaField();
        modificar();
    }
    public void setMatriculaField() {
        matriculaField.setText(matriculaSeleccionada);
    }

    public void obtenerMarcas() throws SQLException {
        LinkedList<String> marcas = ctrlAutomovil.getMarcas();
        for (String marca : marcas) {
            marcaComboBox.addItem(marca);
        }
    }
    public void modificar(){
        ctrlAutomovil = new CtrlAutomovil();
        automovil = new Automovil();
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String matriculaNueva = (String) matriculaText.getText();
                int marcaSeleccionada = obtenerIdMarca((String) marcaComboBox.getSelectedItem());
                if(marcaSeleccionada == 0){
                    Log.error("Por favor, seleccione una marca");
                }else{
                    ctrlAutomovil.modificarMatricula(marcaSeleccionada, matriculaNueva, matriculaSeleccionada);
                    JOptionPane.showMessageDialog(null, "Modificacion exitosa");
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
