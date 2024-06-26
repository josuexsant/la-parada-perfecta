package controller;

import model.Log;
import model.SimulatedTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CtrlTime {
    private final SimulatedTime simulatedTime;

    public CtrlTime(){
        simulatedTime = SimulatedTime.getInstance();
    }

    public void guardar(Date date, Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHours());
        calendar.set(Calendar.MINUTE, time.getMinutes());

        simulatedTime.setDate(calendar);

        Log.debug("Tiempo en la aplicacion: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(SimulatedTime.getInstance().getDate().getTime()));
    }

    public void reset(){
        simulatedTime.setDate(Calendar.getInstance());
    }
}
