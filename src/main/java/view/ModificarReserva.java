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
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.*;


public class ModificarReserva extends JFrame {
    private JPanel ModificarR;
    private JLabel img;
    private JList JListReserva;
    private JComboBox<String> MatriculaBox;
    private JButton CancelarButton;
    private JButton confirmarButton;
    private JLabel LabelHLlegada;
    private JLabel LabelDia;
    private JLabel MesLabel;
    private JLabel LabelHoraSalida;
    private JLabel LabelnombreUsuario;
    private JLabel nombreUsuario;
    private JSpinner DiaSpinner;
    private JSpinner MesSpinner;
    private JSpinner HoraLlSpinner;
    private JSpinner HoraSaSpinner;
    private ViewMenu menu;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;

    public ModificarReserva() {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();

        setContentPane(ModificarR);
        llenarMatriculas();
        setNombreField();
        Confirmar();
        Cancelar();
        InsertarReservas();
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

    public void llenarMatriculas() {
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

    public void setNombreField() {
        ctrlAutomovil.obtenerNombre();
        nombreUsuario.setText(ctrlAutomovil.obtenerNombre());
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
            int diaSeleccionado = Integer.parseInt(new SimpleDateFormat("dd").format(DiaSpinner.getValue()));
            int mesSeleccionado = Integer.parseInt(new SimpleDateFormat("MM").format(MesSpinner.getValue()));
            String horaLlegadaSeleccionada = new SimpleDateFormat("HH:mm").format(HoraLlSpinner.getValue());
            String horaSalidaSeleccionada = new SimpleDateFormat("HH:mm").format(HoraSaSpinner.getValue());
            String nombreSeleccionado = nombreUsuario.getText();
            String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();
            Log.debug("Dia: " +String.valueOf( diaSeleccionado));
            Log.debug("Mes: " + String.valueOf(mesSeleccionado));

            if (matriculaSeleccionada == null) {
                Log.error("No hay una matricula registrada");
                JOptionPane.showMessageDialog(ModificarR, "No hay una matricula seleccionada.");
            } else {
                // Aquí se replica la lógica de la función original
                Loading view = new Loading("Comprobando disponilidad...");
                view.mostrarInterfaz(10000);

                Timer timer = new Timer(10000, e -> {
                    SwingUtilities.invokeLater(() -> {
                        Calendar peticion = Calendar.getInstance();
                        peticion.set(2024, mesSeleccionado - 1, diaSeleccionado, Integer.parseInt(horaLlegadaSeleccionada.split(":")[0]), 0);
                        Log.debug(peticion.toString());

                        if (verificarDisponibilidad(peticion) > 0) {
                            ctrlReserva.modificarReserva(idReserva, diaSeleccionado, mesSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada, matriculaSeleccionada);
                            JOptionPane.showMessageDialog(ModificarR, "Modificación exitosa");
                            menu.mostrarInterfaz();
                            dispose();
                            Log.success("Modificación Exitosa");
                        } else {
                            Log.error("No hay una matrícula registrada");
                            JOptionPane.showMessageDialog(ModificarR, "Modificación exitosa");
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



    public void mostrarInterfaz() {
        setContentPane(ModificarR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Inicio de sesion");
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

        DiaSpinner = new JSpinner(dayModel);
        MesSpinner = new JSpinner(monthModel);
        HoraLlSpinner = new JSpinner(timeModel1);
        HoraSaSpinner = new JSpinner(timeModel2);

        DiaSpinner.setEditor(new JSpinner.DateEditor(DiaSpinner, "dd"));
        MesSpinner.setEditor(new JSpinner.DateEditor(MesSpinner, "MM"));
        HoraLlSpinner.setEditor(new JSpinner.DateEditor(HoraLlSpinner,"HH:mm"));
        HoraSaSpinner.setEditor(new JSpinner.DateEditor(HoraSaSpinner,"HH:mm"));

        ImageIcon icon = new ImageIcon("src/main/images/editar.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_FAST);
        img = new JLabel(new ImageIcon(image)); //Falta agregar imagen
    }
}