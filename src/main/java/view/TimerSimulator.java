package view;

import model.Log;
import model.SimulatedTime;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerSimulator extends JFrame {
    private JButton configurarButton;
    private JPanel panel;
    private JLabel dateField;
    private JLabel timeField;

    public TimerSimulator(){
        Calendar calendar = SimulatedTime.getInstance().getDate();
        Date date = calendar.getTime();
        dateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(date));
        timeField.setText(new SimpleDateFormat("HH:mm").format(date));

        configurarButton.addActionListener(e -> configurar());
    }

    public void configurar(){
        EditTime view = new EditTime();
        view.mostrarInterfaz();
        dispose();
    }

    public void mostrarInterfaz() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(800,100);
        setSize(500, 300);
        setResizable(false);
        setVisible(true);
        Log.info("Se inicia la vista Inicio de sesion");
    }
}
