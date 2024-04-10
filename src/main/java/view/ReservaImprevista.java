
package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import controller.CtrlUsuario;
import model.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public ReservaImprevista(){
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();
       // llenarPlacaAutomovil();
        //establecerNombre();
        //Confirmar();
        mostrarInterfaz();
        Cancelar();
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
//        public void llenarPlacaAutomovil() {
//        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
//        for (String placa : placas) {
//            matriculaComboBox.addItem(placa);
//        }
//    }
    public void establecerNombre() {
        ctrlAutomovil.obtenerNombre();
        nombreLabel.setText(ctrlAutomovil.obtenerNombre());
    }
//    public void establecerNombre() {
//        ctrlAutomovil.obtenerNombre();
//        nombreUsuario.setText(ctrlAutomovil.obtenerNombre());
//    }
    public void Cancelar() {
        menu = new ViewMenu();
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInterfaz();
                dispose();
            }
        };
        cancelarButton.addActionListener(accion);
        dispose();
    }
//    private void Confirmar() {
//        ActionListener accion = actionEvent -> {
//            Loading view = new Loading("Comprobando disponilidad...");
//            view.mostrarInterfaz(10000);
//
//            Timer timer = new Timer(10000, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    SwingUtilities.invokeLater(() -> {
//                        int diaSeleccionado = Integer.parseInt(new SimpleDateFormat("dd").format(diaSpinner.getValue()));
//                        int mesSeleccionado = Integer.parseInt(new SimpleDateFormat("MM").format(mesSpinner.getValue()));
//                        String horaLlegadaSeleccionada = new SimpleDateFormat("HH:mm").format(llegadaSpinner.getValue());
//                        String horaSalidaSeleccionada = new SimpleDateFormat("HH:mm").format(salidaSpinner.getValue());
//                        String nombreSeleccionado = nombreUsuario.getText();
//                        String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();
//
//
//                        Calendar peticion = Calendar.getInstance();
//                        peticion.set(2024, mesSeleccionado - 1, diaSeleccionado, Integer.parseInt(horaLlegadaSeleccionada.split(":")[0]), 0);
//                        Log.debug(peticion.toString());
//
//                        if (matriculaSeleccionada != null && verificarDisponibilidad(peticion)>0) {
//                            Reserva reservaNueva = ctrlReserva.crearReserva(diaSeleccionado, mesSeleccionado, horaLlegadaSeleccionada, horaSalidaSeleccionada, matriculaSeleccionada);
//
//                            ConfirmarReserva view = new ConfirmarReserva(reservaNueva);
//                            view.mostrarInterfaz();
//                            dispose();
//                        } else {
//                            Log.error("No hay una matricula registrada");
//                            JOptionPane.showMessageDialog(ReservaP, "No hay una matricula seleccionada.");
//                        }
//                    });
//                }
//            });
//            timer.setRepeats(false); // Para que solo se ejecute una vez
//            timer.start();
//        };
//        confirmarButton.addActionListener(accion);
//    }


}
