package model;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Josue Santamaria
 *
 *
 * Esta clase nos ayuda a simular un tiempo dentro de toda la aplicacion, con este tiempo
 * podemos simular que pasa cuando un cliente llega antes de tiempo a una reserva,
 * cuando se ha pasado del limite de su reserva, cuando no ha llegado a su reserva, etc.
 */
public class SimulatedTime {
    private Calendar date;
    private static SimulatedTime _instace;
    private SimulatedTime(){}

    public static SimulatedTime getInstance(){
        if(_instace == null){
            _instace = new SimulatedTime();
        }
        return _instace;
    }

    public Calendar getDate(){
        return date;
    }

    public void setDate(Calendar date){
        this.date = date;
    }
}

