package view;


import controller.CtrlAutomovil;
import controller.CtrlReserva;
import model.Automovil;
import model.Log;
import model.Reserva;
import model.SimulatedTime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.*;

public class ExtenderReserva extends JFrame {
    private JPanel ExtenderP;
    private JLabel img;
    private JLabel LabelnombreUsuario;
    private JLabel nombreUsuario;
    private JLabel fechatextfield;
    private JSpinner llegadaSpinner;
    private JLabel LabelHLlegada;
    private JSpinner salidaSpinner;
    private JLabel LabelHoraSalida;
    private JComboBox MatriculaBox;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JComboBox FechaBox;
    private JList JListReserva;
    private JComboBox MesBox;

    private ViewMenu menu;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;

    private Reserva reserva;

    public ExtenderReserva() {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
        llenarPlacaAutomovil();
        establecerNombre();
        InsertarReservas();
        Confirmar();
        Cancelar();
    }

    private double calcularCosto(int horasSeleccionadas) {
        double tarifaPorHora = 10.0;
        if (horasSeleccionadas < 0) {
            throw new IllegalArgumentException("Las horas seleccionadas deben ser positivas.");
        }
        double costoTotal = horasSeleccionadas * tarifaPorHora;
        return costoTotal;
    }

    public void llenarPlacaAutomovil() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            MatriculaBox.addItem(placa);
        }
    }
    public void InsertarReservas() {
        // Obtener el modelo de lista actual de JListReserva
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JListReserva.setModel(listModel); // Establecer el modelo en el JList

        LinkedList<Reserva> reservasList = ctrlReserva.getList();
        Automovil automovil;
        // Agregar cada reserva al modelo de lista
        for (Reserva reserva : reservasList) {
            automovil = new Automovil(reserva.getIdAutomovil());
            listModel.addElement("ID: " + reserva.getId() + " Fecha: " + reserva.getFecha() + " Matricula: " + automovil.getPlaca());
        }
    }

    public void Cancelar() {
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




    private void Confirmar() {
        ActionListener accion = actionEvent -> {
            int optionSelected = JListReserva.getSelectedIndex();

            Reserva reserva = null;
            try {
                reserva = ctrlReserva.obtenerReservaPorIndice(optionSelected);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            int idReserva = reserva.getId();
            String horaLlegadaSeleccionada = new SimpleDateFormat("HH:mm").format(llegadaSpinner.getValue());
            String horaSalidaSeleccionada = new SimpleDateFormat("HH:mm").format(salidaSpinner.getValue());
            String nombreSeleccionado = nombreUsuario.getText();
            String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();


            if (matriculaSeleccionada == null) {
                Log.error("No hay una matricula registrada");
                JOptionPane.showMessageDialog(ExtenderP, "No hay una matricula seleccionada.");
            } else {
                // Aquí se replica la lógica de la función original
                Loading view = new Loading("Comprobando disponilidad...");
                view.mostrarInterfaz(10000);

                Timer timer = new Timer(10000, e -> {
                    SwingUtilities.invokeLater(() -> {
                        Calendar peticion = Calendar.getInstance();


                        if (verificarDisponibilidad(peticion) > 0) {
                            ctrlReserva.extenderReserva(idReserva,horaLlegadaSeleccionada, horaSalidaSeleccionada, matriculaSeleccionada);
                            JOptionPane.showMessageDialog(ExtenderP, "Modificación exitosa");
                            menu.mostrarInterfaz();
                            dispose();
                            Log.success("Modificación Exitosa");
                        } else {
                            Log.error("No hay una matrícula registrada");
                            JOptionPane.showMessageDialog(ExtenderP, "Modificación exitosa");
                        }
                    });
                });
                timer.setRepeats(false); // Para que solo se ejecute una vez
                timer.start();
            }
        };
        confirmarButton.addActionListener(accion);
    }

    public int verificarDisponibilidad(Calendar peticion) {
        Calendar deadLine = SimulatedTime.getInstance().getDate();
        deadLine.add(Calendar.MINUTE, -15);

        int i = peticion.compareTo(deadLine);
        return i;
    }




    public void establecerNombre() {
        ctrlAutomovil.obtenerNombre();
        nombreUsuario.setText(ctrlAutomovil.obtenerNombre());
    }

    private void createUIComponents() {
        Calendar calendar = (Calendar) SimulatedTime.getInstance().getDate().clone();

        Date initDate = calendar.getTime();
        Date predeterminada = calendar.getTime();
        calendar.add(Calendar.YEAR, 1);
        Date maxDate = calendar.getTime();


        SpinnerModel timeModel1 = new SpinnerDateModel(
                initDate,
                null,
                maxDate,
                Calendar.HOUR_OF_DAY
        );

        SpinnerModel timeModel2 = new SpinnerDateModel(
                initDate,
                null,
                maxDate,
                Calendar.HOUR_OF_DAY
        );


        llegadaSpinner = new JSpinner(timeModel1);
        salidaSpinner = new JSpinner(timeModel2);

        llegadaSpinner.setEditor(new JSpinner.DateEditor(llegadaSpinner, "HH:mm"));
        salidaSpinner.setEditor(new JSpinner.DateEditor(salidaSpinner, "HH:mm"));

        ImageIcon icon = new ImageIcon("src/main/images/editar.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_FAST);
        img = new JLabel(new ImageIcon(image));
    }

    public void mostrarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            setContentPane(ExtenderP);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocation(100, 100);
            setSize(500, 600);
            setResizable(false);
            setVisible(true);
            Log.info("Se inicia la vista Registro reserva");
        });
    }


}