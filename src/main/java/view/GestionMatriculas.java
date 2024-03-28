package view;

import controller.CtrlAutomovil;
import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class GestionMatriculas extends JFrame {
    private JPanel gestionMatriculasPanel;
    private JPanel tituloPanel;
    private JLabel tituloLabel;
    private JButton agregarMatriculaButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    JComboBox matriculasComboBox;

    private ModificarMatricula modificarM;
    private RegistroMatricula agregarM;
    private CtrlAutomovil ctrlAutomovil;

    public GestionMatriculas() {
        ctrlAutomovil = new CtrlAutomovil();
        setContentPane(gestionMatriculasPanel);
        setLocationRelativeTo(null);
        Modificar();
        AgregarMatricula();
        llenarMatriculas();
        mostrarInterfaz();
        eliminarMatricula();
    }

    public void mostrarInterfaz() {
        setContentPane(gestionMatriculasPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        Log.info("Se inicia la vista gestion de matriculas");
    }

    public void llenarMatriculas() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            matriculasComboBox.addItem(placa);
        }
    }

    public void Modificar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ModificarMatricula ModificarFrame;
                try {
                    ModificarFrame = new ModificarMatricula((String) matriculasComboBox.getSelectedItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ModificarFrame.setLocationRelativeTo(null);
                ModificarFrame.setTitle("Agregar matrícula");
                ModificarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ModificarFrame.setVisible(true);
                ModificarFrame.setSize(900, 300);
                dispose();
            }

        };
        modificarButton.addActionListener(accion);
    }

    public void AgregarMatricula() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RegistroMatricula RegistrarFrame;
                try {
                    RegistrarFrame = new RegistroMatricula();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                dispose();
                RegistrarFrame.setTitle("Agregar matrícula");
                RegistrarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                RegistrarFrame.setVisible(true);
                RegistrarFrame.setSize(900, 300);
                RegistrarFrame.setLocationRelativeTo(null);
                dispose();
            }
        };
        agregarMatriculaButton.addActionListener(accion);
    }

    public void eliminarMatricula() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String matriculaSeleccionada = (String) matriculasComboBox.getSelectedItem();
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar la matrícula seleccionada?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (ctrlAutomovil.eliminarMatricula(matriculaSeleccionada)) {
                        JOptionPane.showMessageDialog(null, "Matrícula eliminada correctamente.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
                        matriculasComboBox.removeItem(matriculaSeleccionada);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hay reservas asociadas a esta matricula");
                    }
                }
            }
        };
        eliminarButton.addActionListener(accion);
    }
}
