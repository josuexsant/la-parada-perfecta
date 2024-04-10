import model.SimulatedTime;
import view.InicioSesion;
import view.Panel;
import view.ReservaImprevista;
import view.TimerSimulator;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        InicioSesion sesion = new InicioSesion();
        sesion.mostrarInterfaz();

        SimulatedTime.getInstance().setDate(Calendar.getInstance());

        TimerSimulator timer = new TimerSimulator();
        timer.mostrarInterfaz();

        Panel panel = new Panel();
        panel.mostrarInterfaz();
    }
}
