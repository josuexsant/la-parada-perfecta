package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import controller.CtrlReservaGarantizada;
import model.Log;
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
      /*  getMesBoxInicio();
        getMesBoxFin();
        getFechaBoxInicio();
        getFechaBoxFin();*/
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
/*
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
*/
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

                    Calendar peticion = Calendar.getInstance();
                    peticion.set(2024,mesInicio -1, diaFin);
                    Log.debug(peticion.toString());

                    if(matriculaSeleccionada != null){
                        ctrlReservaGarantizada.crear(diaInicio,mesInicio,diaFin,mesFin,matriculaSeleccionada);
                        menu.mostrarInterfaz();
                    }else{
                        Log.error("No hay matricula registrada");
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
