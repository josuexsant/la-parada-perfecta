import view.InicioSesion;
import view.TimerSimulator;

public class Main {
    public static void main(String[] args) {
        InicioSesion sesion = new InicioSesion();
        sesion.mostrarInterfaz();

        TimerSimulator timer = new TimerSimulator();
        timer.mostrarInterfaz();
    }
}
