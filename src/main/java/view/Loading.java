package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading extends JFrame {
    private JPanel panel1;
    private JLabel iconCar;
    private JLabel message;

    public Loading(String text) {
        this.message.setText(text);
        createUIComponents();
    }

    public void mostrarInterfaz(int milliSeconds) {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(320, 320);
        setResizable(false);
        setVisible(true);
        showForMlls(milliSeconds);
    }
    private void showForMlls(int milliSeconds) {
        Timer timer = new Timer(milliSeconds, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        timer.setRepeats(false); // Solo queremos que se ejecute una vez
        timer.start();
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("src/main/images/car-running.gif");
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_REPLICATE);
        iconCar = new JLabel(new ImageIcon(image));
    }
}
