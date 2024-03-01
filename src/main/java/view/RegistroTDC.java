package view;

import controller.CtrlTDC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class RegistroTDC {

    private JPanel RegistroTdcpn;
    private JTextField nombreTit;
    private JTextField num_tarjeta;
    private JTextField cvvtext;
    private JTextField dire_fact;
    private JButton finalizarButton;
    private JTextField Fecha_exptext;
    private CtrlTDC ctrlTDC;


    public static void main(String[] args) {
        JFrame frame = new JFrame("RegistroTDC");
        frame.setContentPane(new RegistroTDC().RegistroTdcpn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}



