package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import controller.CtrlReservaGarantizada;
import model.Log;
import model.Reserva;
import model.SimulatedTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class ReservaGarantizada extends JFrame {
    private JPanel ReservaP;
    private JLabel LabelnombreUsuario;
    private JLabel LabelHLlegada;
    private JLabel MesLabel;
    private JLabel LabelDia;
    private JComboBox<String> MatriculaBox;
    private JButton confirmarButton;
    private JLabel nombreUsuario;
    private JButton CancelarButton;
    private JLabel img;
    private JSpinner DaySpinner;
    private JSpinner MesSpineer;
    private JSpinner DaySpinnerFin;
    private JSpinner MesSpinnerFin;
    private ViewMenu menu;
    private CtrlReservaGarantizada ctrlReservaGarantizada;
    private CtrlAutomovil ctrlAutomovil;
    private CtrlReserva ctrlReserva;

    public ReservaGarantizada() {
        ctrlReservaGarantizada = new CtrlReservaGarantizada();
        ctrlAutomovil = new CtrlAutomovil();
        llenarMatriculas();
        setNombreField();
        Confirmar();
        cancelar();
    }

    public void llenarMatriculas() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            MatriculaBox.addItem(placa);
        }
    }
    public int verificarMatricula() {
        if (MatriculaBox.getSelectedItem() == null)
            return 0;
        return 5;
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

//    private void Confirmar() {
//        ActionListener accion = actionEvent -> {
//            Loading view = new Loading("Comprobando disponibilidad...");
//            view.mostrarInterfaz(10000);
//
//            Timer timer = new Timer(10000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    SwingUtilities.invokeLater(() -> {
//                    int diaInicio = Integer.parseInt(new SimpleDateFormat("dd").format(DaySpinner.getValue()));
//                    int mesInicio = Integer.parseInt(new SimpleDateFormat("MM").format(MesSpineer.getValue()));
//                    int diaFin = Integer.parseInt(new SimpleDateFormat("dd").format(DaySpinnerFin.getValue()));
//                    int mesFin = Integer.parseInt(new SimpleDateFormat("MM").format(MesSpinnerFin.getValue()));
//                    String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();
//
//                    Calendar peticion = Calendar.getInstance();
//                    peticion.set(2024,mesInicio -1, diaFin);
//                    Log.debug(peticion.toString());
//
//
//                    });
//                }
//            });
//            timer.setRepeats(false); // Para que solo se ejecute una vez
//            timer.start();
//        };
//        confirmarButton.addActionListener(accion);
//    }
private void Confirmar() {
    ActionListener accion = actionEvent -> {
        Loading view = new Loading("Comprobando disponibilidad...");
        view.mostrarInterfaz(10000);

        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    int diaInicio = Integer.parseInt(new SimpleDateFormat("dd").format(DaySpinner.getValue()));
                    int mesInicio = Integer.parseInt(new SimpleDateFormat("MM").format(MesSpineer.getValue()));
                    int diaFin = Integer.parseInt(new SimpleDateFormat("dd").format(DaySpinnerFin.getValue()));
                    int mesFin = Integer.parseInt(new SimpleDateFormat("MM").format(MesSpinnerFin.getValue()));
                    String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();

                    // Verificar restricciones antes de confirmar la reserva garantizada
                    int status = statusReservaGarantizada(diaInicio, mesInicio, diaFin, mesFin, matriculaSeleccionada);
                    if (status != 0) {
                        // Si hay alguna restricción, mostrar un mensaje de error o manejarlo de acuerdo a tus necesidades
                        switch (status) {
                            case 1:
                                Log.error("Los dias y mesese deben ser validos");
                                break;
                            case 2:
                                Log.error("La matrícula del automóvil es inválida.");
                                break;
                            default:
                                Log.error("Error desconocido al crear la reserva.");
                        }
                        return;
                    }

                    // Si no hay restricciones, confirmar la reserva garantizada
                    ctrlReservaGarantizada.crear(diaInicio, mesInicio, diaFin, mesFin, matriculaSeleccionada);
                });
            }
        });
        timer.setRepeats(false); // Para que solo se ejecute una vez
        timer.start();
    };
    confirmarButton.addActionListener(accion);
}

    private int statusReservaGarantizada(int diaInicio, int mesInicio, int diaFin, int mesFin, String matricula) {
        // Verificar restricciones aquí y devolver un código de estado correspondiente
        if (diaInicio < 1 || diaInicio > 31 || diaFin < 1 || diaFin > 31 || mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12 || diaInicio > diaFin || (mesInicio == mesFin && diaInicio == diaFin)) {
            return 1; // Las horas de inicio y fin deben ser válidas
        }
        if (verificarMatricula() == 0) {
            return 2; // La matrícula del automóvil es inválida
        }
        return 0;
    }


    public void mostrarInterfaz() {
        setContentPane(ReservaP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
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

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/editar.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(image));

        Calendar calendar =  SimulatedTime.getInstance().getDate();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 1); // Suma un año a la fecha actual
        Date maxDate = calendar.getTime();

        // Modelos para los spinners de día
        SpinnerModel dayModelInicio = new SpinnerDateModel(initDate, null, maxDate, Calendar.DAY_OF_MONTH);
        SpinnerModel dayModelFin = new SpinnerDateModel(initDate, null, maxDate, Calendar.DAY_OF_MONTH);

        // Modelo para los spinners de mes
        SpinnerModel monthModelInicio = new SpinnerDateModel(initDate, null, maxDate, Calendar.MONTH);
        SpinnerModel monthModelFin = new SpinnerDateModel(initDate, null, maxDate, Calendar.MONTH);

        // Crear los spinners de día con sus respectivos modelos
        DaySpinner = new JSpinner(dayModelInicio);
        DaySpinnerFin = new JSpinner(dayModelFin);

        // Crear los spinners de mes con sus respectivos modelos
        MesSpineer = new JSpinner(monthModelInicio);
        MesSpinnerFin = new JSpinner(monthModelFin);

        // Configurar el editor de los spinners
        DaySpinner.setEditor(new JSpinner.DateEditor(DaySpinner, "dd"));
        MesSpineer.setEditor(new JSpinner.DateEditor(MesSpineer, "MM"));
        MesSpinnerFin.setEditor(new JSpinner.DateEditor(MesSpinnerFin, "MM"));
        DaySpinnerFin.setEditor(new JSpinner.DateEditor(DaySpinnerFin, "dd"));
    }

}
