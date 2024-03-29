package view;
import controller.CtrlAutomovil;
import model.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class RegistroMatricula extends JFrame{

    private JPanel registroPanel;
    private JLabel matriculaLabel;
    private JLabel marcaLabel;
    private JTextField matriculaText;
    private JComboBox<String> marcaComboBox;
    private JButton registrarButton;
    private JButton volverButton;
    private JLabel img;

    private CtrlAutomovil ctrlAutomovil;
    private GestionMatriculas gestionMatriculas;

    public RegistroMatricula() {
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(registroPanel);
        setLocationRelativeTo(null);
        obtenerMarcas();
        registrar();
        volverButton.addActionListener(e -> volver());
    }

    private void volver() {
        GestionMatriculas view = new GestionMatriculas();
        view.mostrarInterfaz();
        dispose();
    }

    public void obtenerMarcas() {
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
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una marca");
                }else{
                    ctrlAutomovil.agregarMatricula(marcaSeleccionada, matriculaNueva);
                        JOptionPane.showMessageDialog(null, "Registro exitoso");
                         ViewMenu menu = new ViewMenu();
                         menu.mostrarInterfaz();
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

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/matricula.png");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));
    }

    public void mostrarInterfaz() {
        setContentPane(registroPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Registro matricula");
    }
}