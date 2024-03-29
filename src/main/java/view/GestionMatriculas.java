package view;

import controller.CtrlAutomovil;
import model.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class GestionMatriculas extends JFrame {
    private JPanel gestionMatriculasPanel;
    private JButton agregarMatriculaButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    JComboBox matriculasComboBox;
    private JButton volverButton;
    private JLabel img;

    private ModificarMatricula modificarM;
    private RegistroMatricula agregarM;
    private CtrlAutomovil ctrlAutomovil;

    public GestionMatriculas() {
        ctrlAutomovil = new CtrlAutomovil();
        modificarButton.addActionListener(e -> modificar());
        agregarMatriculaButton.addActionListener(e -> agregarMatricula());
        llenarMatriculas();
        mostrarInterfaz();
        eliminarMatricula();
        volverButton.addActionListener(e -> volver());
    }

    public void mostrarInterfaz() {
        setContentPane(gestionMatriculasPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista gestion matriculas");
    }

    public void volver() {
        ViewMenu view = new ViewMenu();
        view.mostrarInterfaz();
        dispose();
    }

    public void llenarMatriculas() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            matriculasComboBox.addItem(placa);
        }
    }

    public void modificar() {
        ModificarMatricula view = null;
        try {
            view = new ModificarMatricula((String) matriculasComboBox.getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        view.mostrarInterfaz();
        dispose();
    }

    public void agregarMatricula() {
      RegistroMatricula view = new RegistroMatricula();
      view.mostrarInterfaz();
      dispose();
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

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/matricula.png");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));
    }
}
