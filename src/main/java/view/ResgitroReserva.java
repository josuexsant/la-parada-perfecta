package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import controller.CtrlUsuario;
import model.Log;
import model.SimulatedTime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.SimpleFormatter;
import javax.swing.*;

public class ResgitroReserva extends JFrame {

    private JTextField txtnombreUsuario;
    private JLabel LabelnombreUsuario;
    private JLabel LabelHLlegada;
    private JPanel ReservaP;
    private JLabel LabelHoraSalida;
    private JLabel LabelDia;
    private JLabel MesLabel;
    private JComboBox<String> MatriculaBox;
    private JButton confirmarButton;
    private JLabel nombreUsuario;
    private JButton CancelarButton;
    private JLabel img;
    private JSpinner diaSpinner;
    private JSpinner llegadaSpinner;
    private JSpinner salidaSpinner;
    private JSpinner mesSpinner;
    private JLabel LabelHSalida;
    private ViewMenu menu;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;
    private CtrlUsuario ctrlUsuario;


    public ResgitroReserva() {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();

        llenarPlacaAutomovil();
        establecerNombre();
        Confirmar();
        Cancelar();
    }

    public void llenarPlacaAutomovil() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            MatriculaBox.addItem(placa);
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

    public void establecerNombre() {
        ctrlAutomovil.obtenerNombre();
        nombreUsuario.setText(ctrlAutomovil.obtenerNombre());
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
        CancelarButton.addActionListener(accion);
        dispose();
    }

    private void Confirmar() {
        ActionListener accion = actionEvent -> {
            Loading view = new Loading("Comprobando disponilidad...");
            view.mostrarInterfaz(10000);

            Timer timer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        int diaSeleccionado = Integer.parseInt(new SimpleDateFormat("dd").format(diaSpinner.getValue()));
                        int mesSeleccionado = Integer.parseInt(new SimpleDateFormat("MM").format(mesSpinner.getValue()));
                        String horaLlegadaSeleccionada = new SimpleDateFormat("HH:mm").format(llegadaSpinner.getValue());
                        String horaSalidaSeleccionada = new SimpleDateFormat("HH:mm").format(salidaSpinner.getValue());
                        String nombreSeleccionado = nombreUsuario.getText();
                        String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();


                        Calendar peticion = Calendar.getInstance();
                        peticion.set(2024, mesSeleccionado - 1, diaSeleccionado, Integer.parseInt(horaLlegadaSeleccionada.split(":")[0]), 0);
                        Log.debug(peticion.toString());

                        if (matriculaSeleccionada != null && verificarDisponibilidad(peticion)>0) {
                            ConfirmarReserva view = new ConfirmarReserva(ctrlReserva.crearReserva(diaSeleccionado, mesSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada, matriculaSeleccionada));
                            view.mostrarInterfaz();
                            dispose();
                        } else {
                            Log.error("No hay una matricula registrada");
                            JOptionPane.showMessageDialog(ReservaP, "No hay una matricula seleccionada.");
                        }
                    });
                }
            });
            timer.setRepeats(false); // Para que solo se ejecute una vez
            timer.start();
        };
        confirmarButton.addActionListener(accion);
    }

    public void mostrarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            setContentPane(ReservaP);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocation(100, 100);
            setSize(500, 600);
            setResizable(false);
            setVisible(true);
            Log.info("Se inicia la vista Registro reserva");
        });
    }

    public int verificarDisponibilidad(Calendar peticion) {
        Calendar deadLine = SimulatedTime.getInstance().getDate();
        deadLine.add(Calendar.MINUTE, -15);

        int i = peticion.compareTo(deadLine);
        return i;
    }

    private void createUIComponents() {
        Calendar calendar = (Calendar) SimulatedTime.getInstance().getDate().clone();

        Date initDate = calendar.getTime();
        Date predeterminada = calendar.getTime();
        calendar.add(Calendar.YEAR, 1);
        Date maxDate = calendar.getTime();

        SpinnerModel dayModel = new SpinnerDateModel(
                initDate,
                null,
                maxDate,
                Calendar.DAY_OF_MONTH
        );

        SpinnerModel monthModel = new SpinnerDateModel(
                initDate,
                null,
                maxDate,
                Calendar.MONTH
        );
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

        diaSpinner = new JSpinner(dayModel);
        mesSpinner = new JSpinner(monthModel);
        llegadaSpinner = new JSpinner(timeModel1);
        salidaSpinner = new JSpinner(timeModel2);

        diaSpinner.setEditor(new JSpinner.DateEditor(diaSpinner, "dd"));
        mesSpinner.setEditor(new JSpinner.DateEditor(mesSpinner, "MM"));
        llegadaSpinner.setEditor(new JSpinner.DateEditor(llegadaSpinner,"HH:mm"));
        salidaSpinner.setEditor(new JSpinner.DateEditor(salidaSpinner,"HH:mm"));

        ImageIcon icon = new ImageIcon("src/main/images/editar.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_FAST);
        img = new JLabel(new ImageIcon(image));
    }
}