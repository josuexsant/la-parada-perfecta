
package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import controller.CtrlUsuario;
import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.*;

public class ReservaImprevista extends JFrame{
    private JPanel ReservaP;
    private JLabel nombreLabel;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JLabel nombreUsuario;
    private JLabel fechaLabel;
    private JLabel horaLlegadaLabel;
    private JSpinner horaSalidaSpinner;
    private JComboBox matriculaComboBox;
    private JPanel reservaImprevistaPanel;
    private ViewMenu menu;
    CtrlReserva ctrlReserva;
    CtrlAutomovil ctrlAutomovil;
    private CtrlUsuario ctrlUsuario;
    private Panel panel;
    public ReservaImprevista(){
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
        llenarPlacaAutomovil();
        establecerNombre();
        configurarHoraSalidaSpinner();
        mostrarInterfaz();
        Cancelar();
        confirmar();
    }

    private void confirmar() {
        ActionListener accion = actionEvent -> {
            Loading view = new Loading("Comprobando disponilidad...");
            view.mostrarInterfaz(10000);

            Timer timer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        String fechaTexto = fechaLabel.getText();
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                        Date fecha;
                        try {
                            fecha = formatoFecha.parse(fechaTexto);
                        } catch (ParseException ex) {
                            Log.error("Error al parsear la fecha: " + ex.getMessage());
                            return;
                        }
                        // Obtener el día y el mes de la fecha
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fecha);
                        int diaSeleccionado = calendar.get(Calendar.DAY_OF_MONTH);
                        int mesSeleccionado = calendar.get(Calendar.MONTH) + 1; // Los meses en Calendar van de 0 a 11
                        String horaLlegadaSeleccionada = horaLlegadaLabel.getText();
                        String horaSalidaSeleccionada = new SimpleDateFormat("HH:mm").format(horaSalidaSpinner.getValue());
                        String nombreSeleccionado;
                        try {
                            nombreSeleccionado = Sesion._instance().getUsuario().nombreCompleto(Sesion._instance().getUsuario().getId());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        String matriculaSeleccionada = (String) matriculaComboBox.getSelectedItem();


                        Calendar peticion = Calendar.getInstance();
                        peticion.set(2024, mesSeleccionado - 1, diaSeleccionado, Integer.parseInt(horaLlegadaSeleccionada.split(":")[0]), 0);
                        Log.debug(peticion.toString());

                        if (matriculaSeleccionada != null) {
                            Reserva reservaNueva = ctrlReserva.crearReserva(diaSeleccionado, mesSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada, matriculaSeleccionada);
                            ConfirmarReservaImprevista view = new ConfirmarReservaImprevista(reservaNueva);
                            view.mostrarInterfaz();
                            dispose();
                        } else {
                            Log.error("No hay una matricula registrada");
                            UIManager.put("OptionPane.okButtonText", "Volver a intentar");
                            JOptionPane.showMessageDialog(ReservaP, "No hay una matricula seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
            });
            timer.setRepeats(false); // Para que solo se ejecute una vez
            timer.start();
        };
        confirmarButton.addActionListener(accion);
    }

    public void llenarPlacaAutomovil() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            matriculaComboBox.addItem(placa);
        }
    }
    public void mostrarInterfaz() {
        setContentPane(ReservaP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(100,100);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista reserva imprevista");
    }
    public void establecerNombre() {
        nombreLabel.setText(Sesion._instance().getUsuario().getNombre());
        Calendar calendar = SimulatedTime.getInstance().getDate();
        Date date = calendar.getTime();
        horaLlegadaLabel.setText(new SimpleDateFormat("HH:mm").format(date));
        fechaLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(date));

    }
    public void Cancelar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel = new Panel();
                panel.mostrarInterfaz();
                dispose();
            }
        };
        cancelarButton.addActionListener(accion);
        dispose();
    }

    private void configurarHoraSalidaSpinner() {
        Calendar calendar = SimulatedTime.getInstance().getDate();
        Date predeterminada = calendar.getTime();

        // Redondea las siguientes horas a 60 minutos
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        Date initDate = calendar.getTime();
        Date maxDate = calendar.getTime();

        SpinnerDateModel timeModel = new SpinnerDateModel(
                initDate,  // Hora inicial redondeada a la próxima hora completa
                null,
                maxDate,
                Calendar.HOUR_OF_DAY
        );

        horaSalidaSpinner.setModel(timeModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(horaSalidaSpinner, "HH:mm");
        horaSalidaSpinner.setEditor(editor);
    }

}
