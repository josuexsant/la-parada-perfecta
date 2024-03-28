package view;

import controller.CtrlAutomovil;
import controller.CtrlReservaGarantizada;
import model.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ReservaGarantizada extends JFrame {
    private JPanel ReservaP;
    private JPanel LabelTitulo;
    private JLabel LabelnombreUsuario;
    private JLabel LabelHLlegada;
    private JLabel MesLabel;
    private JLabel LabelDia;
    private JComboBox DiaBoxFin;
    private JComboBox MatriculaBox;
    private JButton confirmarButton;
    private JComboBox MesBoxInicio;
    private JComboBox DiaBoxInicio;
    private JLabel nombreUsuario;
    private JButton CancelarButton;
    private JComboBox MesBoxFin;
    private ViewMenu menu;
    private CtrlReservaGarantizada ctrlReservaGarantizada;
    private CtrlAutomovil ctrlAutomovil;

    public ReservaGarantizada() {
        ctrlReservaGarantizada = new CtrlReservaGarantizada();
        ctrlAutomovil = new CtrlAutomovil();
        getMesBoxInicio();
        getMesBoxFin();
        getFechaBoxInicio();
        getFechaBoxFin();
        llenarMatriculas();
        setNombreField();
        confirmar();
        cancelar();
    }

    public void llenarMatriculas() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            MatriculaBox.addItem(placa);
        }
    }

    public void getMesBoxInicio() {
        MesBoxInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionadoString = (String) MesBoxInicio.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesSeleccionadoString);
                RegistroDiaInicio(mesSeleccionado);
            }
        });
    }
    public void getMesBoxFin() {
        MesBoxFin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionadoString = (String) MesBoxFin.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesSeleccionadoString);
                RegistroDiaFin(mesSeleccionado);
            }
        });
    }

    public void getFechaBoxInicio() {
        MesBoxInicio.removeAllItems();
        for (int mes = 1; mes <= 12; mes++) {
            MesBoxInicio.addItem(Integer.toString(mes));
        }
    }
    public void getFechaBoxFin() {
        MesBoxFin.removeAllItems();
        for (int mes = 1; mes <= 12; mes++) {
            MesBoxFin.addItem(Integer.toString(mes));
        }
    }

    public void RegistroDiaInicio(int mes) {
        DiaBoxInicio.removeAllItems();
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int f = 1; f <= 31; f++) {
                    DiaBoxInicio.addItem(Integer.toString(f));
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for (int f = 1; f <= 30; f++) {
                    DiaBoxInicio.addItem(Integer.toString(f));
                }
                break;
            case 2:
                for (int f = 1; f <= 28; f++) {
                    DiaBoxInicio.addItem(Integer.toString(f));
                }
                break;
        }
    }
    public void RegistroDiaFin(int mes) {
        DiaBoxFin.removeAllItems();
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int f = 1; f <= 31; f++) {
                    DiaBoxFin.addItem(Integer.toString(f));
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for (int f = 1; f <= 30; f++) {
                    DiaBoxFin.addItem(Integer.toString(f));
                }
                break;
            case 2:
                for (int f = 1; f <= 28; f++) {
                    DiaBoxFin.addItem(Integer.toString(f));
                }
                break;
        }
    }

    private double calcularCosto(int horasSeleccionadas) {
        double tarifaPorHora = 10.0;
        if (horasSeleccionadas < 0) {
            throw new IllegalArgumentException("Las horas seleccionadas deben ser positivas.");
        }
        double costoTotal = horasSeleccionadas * tarifaPorHora;
        return costoTotal;
    }

    private String construirInformacionSeleccionada(String nombre, int mes, int dia, String horaLlegada, String horaSalida, String matriculaSeleccionada) {
        return String.format("Nombre de usuario: %s <br><br> Mes: %d <br><br> Día: %d <br><br> Hora Llegada: %s <br><br> Hora Salida: %s <br><br> Matricula: %s <br><br>", nombre, mes, dia, horaLlegada, horaSalida, matriculaSeleccionada);
    }

    public void setNombreField() {
        ctrlAutomovil.obtenerNombre();
        nombreUsuario.setText(ctrlAutomovil.obtenerNombre());
    }

    private void confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mesInicio = Integer.parseInt((String) MesBoxInicio.getSelectedItem());
                int mesFin = Integer.parseInt((String) MesBoxFin.getSelectedItem());
                int diaInicio = Integer.parseInt((String) DiaBoxInicio.getSelectedItem());
                int diaFin = Integer.parseInt((String) DiaBoxFin.getSelectedItem());
                String nombre = nombreUsuario.getText();
                String matricula = (String) MatriculaBox.getSelectedItem();

                if (matricula == null) {
                    Log.error("No hay una matricula registrada");
                    JOptionPane.showMessageDialog(null, "No hay una matricula seleccionada.");
                } else {
                    ctrlReservaGarantizada.crear(diaInicio,mesInicio,diaFin,mesFin,matricula);
                    JOptionPane.showMessageDialog(null,"Reserva garantizada creada");
                    menu.mostrarInterfaz();
                    dispose();
                }
            }
        };
        confirmarButton.addActionListener(accion);
    }

    public void mostrarInterfaz() {
        setContentPane(ReservaP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Inicio de sesion");
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
        CancelarButton.addActionListener(accion);

    }
}
