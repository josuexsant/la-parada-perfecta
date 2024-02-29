package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class ResgitroReserva extends JFrame {

    private JTextField txtnombreUsuario;
    private JLabel LabelnombreUsuario;
    private JLabel LabelHLlegada;
    private JComboBox<String> HoraLlegada;
    private JPanel ReservaP;
    private JPanel LabelTitulo;
    private JLabel LabelHoraSalida;
    private JComboBox<String> HoraSalida;
    private JComboBox<String> DiaBox;
    private JLabel LabelDia;
    private JLabel MesLabel;
    private JComboBox MatriculaBox;
    private JButton confirmarButton;
    private JComboBox<String> MesBox;
    private JLabel LabelHSalida;


    public ResgitroReserva(){
       super ("Crear reserva");

        setContentPane(ReservaP);
        RegistroHora();

        MesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionadoString = (String) MesBox.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesSeleccionadoString);
                RegistroDia(mesSeleccionado);
            }
        });

        RegistroFecha();
        Confirmar();

    }

    public void RegistroHora(){
        for (int f = 0; f <= 23; f++) {
            int hour = (f == 0) ? 12 : (f > 12) ? f - 12 : f;
            String period = (f < 12) ? "AM" : "PM";
            HoraLlegada.addItem(String.format("%d:00 %s", hour, period));
            HoraSalida.addItem(String.format("%d:00 %s", hour, period));
        }
    }

public void RegistroFecha() {
    MesBox.removeAllItems();
    for (int mes = 1; mes <= 12; mes++) {
        MesBox.addItem(Integer.toString(mes));
    }
}

    public void RegistroDia(int mes) {
        DiaBox.removeAllItems();
        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                for (int f = 1; f <= 31; f++) {
                    DiaBox.addItem(Integer.toString(f));
                }
                break;
            case 4: case 6: case 9: case 11:
                for (int f = 1; f <= 30; f++) {
                    DiaBox.addItem(Integer.toString(f));
                }
                break;
            case 2:
                for (int f = 1; f <= 28; f++) {
                    DiaBox.addItem(Integer.toString(f));
                }
                break;
        }
    }
    /*
    private void Confirmar(){
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmarReserva confirmarReservaFrame = new ConfirmarReserva();
                confirmarReservaFrame.setTitle("Confirmar Reserva");
                confirmarReservaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                confirmarReservaFrame.setVisible(true);
                confirmarReservaFrame.setSize(900,300);
                confirmarReservaFrame.setLocationRelativeTo(null);
            }
        };
        confirmarButton.addActionListener(accion);
    }
     */

    private void Confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mesSeleccionado = Integer.parseInt((String) MesBox.getSelectedItem());
                int diaSeleccionado = Integer.parseInt((String) DiaBox.getSelectedItem());
                String horaLlegadaSeleccionada = (String) HoraLlegada.getSelectedItem();
                String horaSalidaSeleccionada = (String) HoraSalida.getSelectedItem();

                // Crear e instanciar la nueva ventana de confirmación
                ConfirmarReserva confirmarReservaFrame = new ConfirmarReserva();
                confirmarReservaFrame.setTitle("Confirmar Reserva");
                confirmarReservaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                confirmarReservaFrame.setVisible(true);
                confirmarReservaFrame.setSize(900, 300);
                confirmarReservaFrame.setLocationRelativeTo(null);

                // Enviar la información seleccionada a la ventana de confirmación
                String informacionSeleccionada = String.format("Mes: %d, Día: %d, Hora Llegada: %s, Hora Salida: %s",
                        mesSeleccionado, diaSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada);
                confirmarReservaFrame.mostrarInformacionSeleccionada(informacionSeleccionada);
            }
        };
        confirmarButton.addActionListener(accion);
    }



}
