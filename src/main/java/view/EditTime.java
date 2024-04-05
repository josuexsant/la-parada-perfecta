package view;

import controller.CtrlTime;
import model.Log;
import model.SimulatedTime;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class EditTime extends JFrame {
    private JPanel panel;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JSpinner spinnerDate;
    private JSpinner spinnerTime;
    private JButton resetearButton;


    public EditTime() {
        guardarButton.addActionListener(e -> guardar());
        resetearButton.addActionListener(e -> reset());
        cancelarButton.addActionListener(e -> cancelar());
    }

    private void reset() {
        CtrlTime ctrlTime = new CtrlTime();
        ctrlTime.reset();
        TimerSimulator view = new TimerSimulator();
        view.mostrarInterfaz();
        dispose();
    }

    private void cancelar() {
        TimerSimulator view = new TimerSimulator();
        view.mostrarInterfaz();
        dispose();
    }

    private void guardar() {
        Date date = (Date) spinnerDate.getValue();
        Date time = (Date) spinnerTime.getValue();

        CtrlTime ctrlTime = new CtrlTime();
        ctrlTime.guardar(date,time);

        TimerSimulator view = new TimerSimulator();
        view.mostrarInterfaz();
        dispose();
    }


    public void mostrarInterfaz() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(800, 100);
        setSize(500, 300);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista EditTime");
    }

    private void createUIComponents() {
        spinnerDate = new JSpinner();
        spinnerTime = new JSpinner();

        Calendar calendar = (Calendar) SimulatedTime.getInstance().getDate().clone();
        Calendar calendarTime = (Calendar) SimulatedTime.getInstance().getDate().clone();

        // Setteamos el modelo de spinnerDate
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -50);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 100);
        Date latestDate = calendar.getTime();

        SpinnerModel modelDate = new SpinnerDateModel(
                initDate,
                earliestDate,
                latestDate,
                Calendar.YEAR);

        // Setteamos el modelo de spinnerTime
        calendarTime.set(Calendar.YEAR, 1970); // Año base para evitar problemas con la fecha
        calendarTime.set(Calendar.MONTH, Calendar.JANUARY);
        calendarTime.set(Calendar.DAY_OF_MONTH, 1);
        calendarTime.set(Calendar.HOUR_OF_DAY, 0);
        calendarTime.set(Calendar.MINUTE, 0);
        calendarTime.set(Calendar.SECOND, 0);
        calendarTime.set(Calendar.MILLISECOND, 0);

        SpinnerModel modelTime = new SpinnerDateModel(
                calendarTime.getTime(),
                calendarTime.getTime(),
                latestDate,
                Calendar.HOUR_OF_DAY
        );

        spinnerDate.setModel(modelDate);
        spinnerTime.setModel(modelTime);
        spinnerDate.setEditor(new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy"));
        spinnerTime.setEditor(new JSpinner.DateEditor(spinnerTime, "HH:mm"));
    }

}
