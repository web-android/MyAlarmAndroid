package br.edu.ifpr.webandroid.myalarm.util;

import android.content.Context;
import android.content.SharedPreferences;

import br.edu.ifpr.webandroid.myalarm.model.AlarmTime;


/**
 * Created by everaldo on 01/03/15.
 */
public class AlarmPreferences {

    public static final String PREFS_NAME = "MYALARMPrefs";
    public static final boolean DEFAULT_ENABLE_ALARM = false;
    private final String ALARM_HOURS;
    private final String ALARM_MINUTES;
    private final String ENABLE_ALARM;
    private Context context;
    private final String prefix;


    public AlarmPreferences(Context context, String prefix) {
        this.context = context.getApplicationContext();
        this.prefix = prefix;
        this.ENABLE_ALARM = prefix + "AlarmStatus";
        this.ALARM_HOURS  = prefix + "AlarmHours";
        this.ALARM_MINUTES = prefix + "AlarmMinutes";
    }

    public static AlarmPreferences createMorningAlarmPreferences(Context context){
        return new AlarmPreferences(context, "morning");
    }

    public static AlarmPreferences createEveningAlarmPreferences(Context context){
        return new AlarmPreferences(context, "evening");
    }

    public boolean getAlarmStatus(){
        SharedPreferences settings = getPreferences();
        return settings.getBoolean(ENABLE_ALARM, DEFAULT_ENABLE_ALARM);
    }

    public void setAlarmStatus(boolean alarmStatus){
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putBoolean(ENABLE_ALARM, alarmStatus);
        editor.commit();
    }

    public AlarmTime getAlarmTime(){
        SharedPreferences settings = getPreferences();
        int hours = settings.getInt(ALARM_HOURS, Util.getHours());
        int minutes = settings.getInt(ALARM_MINUTES, Util.getMinutes());
        return AlarmTime.create(hours, minutes);
    }

    public void setAlarmTime(AlarmTime alarmTime){
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putInt(ALARM_HOURS, alarmTime.getHours());
        editor.putInt(ALARM_MINUTES, alarmTime.getMinutes());
        editor.commit();
    }



    private SharedPreferences getPreferences(){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPreferencesEditor(){
        return getPreferences().edit();
    }

}
