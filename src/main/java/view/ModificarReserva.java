package view;

import controller.CtrlAutomovil;
import controller.CtrlReserva;
import model.Log;
import model.Reserva;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.*;



public class ModificarReserva extends JFrame{
    private JPanel ModificarR;
    private JPanel JPanelTitulo;
    private JLabel Titulo;
    private JList JListReserva;
    private JComboBox<String>  DiaBox;
    private JComboBox<String>  MesBox;
    private JComboBox<String>  HoraLlegada;
    private JComboBox<String>  HoraSalida;
    private JComboBox<String>  MatriculaBox;
    private JButton CancelarButton;
    private JButton confirmarButton;
    private JLabel LabelHLlegada;
    private JLabel LabelDia;
    private JLabel MesLabel;
    private JLabel LabelHoraSalida;
    private JLabel LabelnombreUsuario;
    private JLabel nombreUsuario;
    private ViewMenu menu;
    private CtrlReserva ctrlReserva;
    private CtrlAutomovil ctrlAutomovil;

    public ModificarReserva() throws SQLException {
        ctrlReserva = new CtrlReserva();
        ctrlAutomovil = new CtrlAutomovil();

        setContentPane(ModificarR);
        getHoraBox();
        getMesBox();
        getFechaBox();
        llenarMatriculas();
        setNombreField();
        Confirmar();
        Cancelar();
        InsertarReservas();
    }
    public void InsertarReservas() throws SQLException {
        // Obtener el modelo de lista actual de JListReserva
        DefaultListModel<String> listModel = (DefaultListModel<String>) JListReserva.getModel();

        // Obtener las reservas del controlador
        LinkedList<String> reservas = ctrlReserva.obtenerReservas();

        // Agregar cada reserva al modelo de lista
        for (String reserva : reservas) {
            listModel.addElement(reserva);
        }
    }
    public void llenarMatriculas() {
        LinkedList<String> placas = ctrlAutomovil.getMatriculas();
        for (String placa : placas) {
            MatriculaBox.addItem(placa);
        }
    }

    public void getMesBox() {
        MesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionadoString = (String) MesBox.getSelectedItem();
                int mesSeleccionado = Integer.parseInt(mesSeleccionadoString);
                RegistroDia(mesSeleccionado);
            }
        });
    }

    public void getHoraBox() {
        for (int f = 0; f <= 23; f++) {
            HoraLlegada.addItem(String.format("%s:00", f));
            HoraSalida.addItem(String.format("%s:00", f));
        }
    }

    public void getFechaBox() {
        MesBox.removeAllItems();
        for (int mes = 1; mes <= 12; mes++) {
            MesBox.addItem(Integer.toString(mes));
        }
    }

    public void RegistroDia(int mes) {
        DiaBox.removeAllItems();
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int f = 1; f <= 31; f++) {
                    DiaBox.addItem(Integer.toString(f));
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
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

    public void Cancelar() {
        menu = new ViewMenu();

        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.mostrarInterfaz();
                dispose();
            }
        };
        CancelarButton.addActionListener(accion);
        dispose();
    }

    private void Confirmar() {
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int optionSelected = JListReserva.getSelectedIndex();


                Reserva reserva = null;
                try {
                    reserva = ctrlReserva.obtenerReservaPorIndice(optionSelected);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                int idReserva = reserva.getId();
                int mesSeleccionado = Integer.parseInt((String) MesBox.getSelectedItem());
                int diaSeleccionado = Integer.parseInt((String) DiaBox.getSelectedItem());
                String horaLlegadaSeleccionada = (String) HoraLlegada.getSelectedItem();
                String horaSalidaSeleccionada = (String) HoraSalida.getSelectedItem();
                String nombreSeleccionado = nombreUsuario.getText();
                String matriculaSeleccionada = (String) MatriculaBox.getSelectedItem();

                if (matriculaSeleccionada == null) {
                    Log.error("No hay una matricula registrada");
                    JOptionPane.showMessageDialog(null, "No hay una matricula seleccionada.");
                } else {
                    ctrlReserva.modificarReserva(idReserva,diaSeleccionado,mesSeleccionado,horaLlegadaSeleccionada,horaSalidaSeleccionada,matriculaSeleccionada);
                    JOptionPane.showMessageDialog(null, "Modificación exitosa");
                    menu.mostrarInterfaz();
                    dispose();
                    Log.success("Modificacion Exitosa");
                }


            }

        };
        confirmarButton.addActionListener(accion);
    }


}
