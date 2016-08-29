package br.edu.ifpr.webandroid.myalarm.model;


import java.util.Calendar;
import java.util.Formatter;

import br.edu.ifpr.webandroid.myalarm.util.Util;

/**
 * Created by everaldo on 01/03/15.
 */
public class AlarmTime {

    public static final boolean FIRE_IMMEDIATELY = false;
    public static final boolean WAIT_TOMORROW_IF_ALARM_TIME_PAST = true;

    private final int hours;
    private final int minutes;

    public AlarmTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public static AlarmTime createDefault(){
        return new AlarmTime(Util.getHours(), Util.getMinutes());
    }

    public static AlarmTime create(int hours, int minutes) {
        return new AlarmTime(hours, minutes);
    }


    public long getTimeInMillis(){
        return getTimeInMillis(FIRE_IMMEDIATELY);
    }

    public long getTimeInMillis(boolean tomorrow){
        Calendar calendar = Calendar.getInstance();
        long now = System.currentTimeMillis();
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        if (tomorrow && calendar.getTimeInMillis() <= now){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTimeInMillis();
    }



    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String formattedString(){
        return new Formatter().format("%02d:%02d", getHours(), getMinutes()).toString();
    }

    public String toString(){
        return formattedString();
    }

}
