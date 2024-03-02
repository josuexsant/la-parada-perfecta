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

    public void MostrarInicio(){
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new ResgitroReserva().ReservaP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

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
            HoraLlegada.addItem(String.format("%s:00",f));
            HoraSalida.addItem(String.format("%s:00", f));
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

    private double calcularCosto(int horasSeleccionadas) {
        // Puedes ajustar la tarifa por hora según tus necesidades
        double tarifaPorHora = 10.0;
        if (horasSeleccionadas < 0) {
            throw new IllegalArgumentException("Las horas seleccionadas deben ser positivas.");
        }
        double costoTotal = horasSeleccionadas * tarifaPorHora;
        return costoTotal;
    }

    private String construirInformacionSeleccionada(String nombre, int mes, int dia, String horaLlegada, String horaSalida) {
        return String.format("Nombre de usuario: %s <br><br> Mes: %d <br><br> Día: %d <br><br> Hora Llegada: %s <br><br> Hora Salida: %s <br><br>",nombre, mes, dia, horaLlegada, horaSalida);
    }


    private void Confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mesSeleccionado = Integer.parseInt((String) MesBox.getSelectedItem());
                int diaSeleccionado = Integer.parseInt((String) DiaBox.getSelectedItem());
                String horaLlegadaSeleccionada = (String) HoraLlegada.getSelectedItem();
                String horaSalidaSeleccionada = (String) HoraSalida.getSelectedItem();
                String nombreSeleccionado = txtnombreUsuario.getText();


                ConfirmarReserva confirmarReservaFrame = new ConfirmarReserva();
                confirmarReservaFrame.setTitle("Confirmar Reserva");
                confirmarReservaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                confirmarReservaFrame.setVisible(true);
                confirmarReservaFrame.setSize(900, 300);
                confirmarReservaFrame.setLocationRelativeTo(null);
                dispose();

                String informacionSeleccionada = construirInformacionSeleccionada(nombreSeleccionado, mesSeleccionado, diaSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada);

                int horaInicio = Integer.parseInt(horaLlegadaSeleccionada.split(":")[0]);
                int horaFin = Integer.parseInt(horaSalidaSeleccionada.split(":")[0]);
                int horasSeleccionadas = horaFin - horaInicio;
                double costoTotal = calcularCosto(horasSeleccionadas);
                informacionSeleccionada += String.format("\nCosto Total: $%.2f", costoTotal);
                confirmarReservaFrame.mostrarInformacionSeleccionada(informacionSeleccionada);
            }
        };
        confirmarButton.addActionListener(accion);
    }


}
