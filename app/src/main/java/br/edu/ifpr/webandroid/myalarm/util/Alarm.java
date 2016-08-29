package br.edu.ifpr.webandroid.myalarm.util;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.widget.Toast;

import br.edu.ifpr.webandroid.myalarm.AlarmDisplayActivity;
import br.edu.ifpr.webandroid.myalarm.model.AlarmTime;


/**
 * Created by everaldo on 01/03/15.
 */
public class Alarm {

    public static final int REQUEST_CODE_MORNING_ALARM = 0;
    public static final int REQUEST_CODE_EVENING_ALARM = 1;
    private final Context context;
    private final AlarmManager alarmManager;
    private final int requestCode;
    private PendingIntent pendingIntent;
    private AlarmTime alarmTime;
    private String name;

    private static final int DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24;
    //private static final int DAY_IN_MILLISECONDS = 1000 * 30;


    public Alarm(Context context, int requestCode, String name) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.requestCode = requestCode;
        this.pendingIntent = getAlarmPendingIntent(context, requestCode);
        this.alarmTime = AlarmTime.createDefault();
        this.name = name;

    }

    public static Alarm createMorningAlarm(Context context){
        return new Alarm(context, REQUEST_CODE_MORNING_ALARM, "morning");
    }

    public static Alarm createEveningAlarm(Context context){
        return new Alarm(context, REQUEST_CODE_EVENING_ALARM, "evening");
    }

    public static Alarm createAlarm(Context context, String alarmName){
        if (alarmName.equals("morning")){
            return Alarm.createMorningAlarm(context);
        }
        else{
            return Alarm.createEveningAlarm(context);
        }
    }

    private PendingIntent getAlarmPendingIntent(Context context, int requestCode){
        Intent intent = new Intent(context, AlarmDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, requestCode, intent, 0);
    }

    public void fire(boolean tomorrow){
        long time = alarmTime.getTimeInMillis(tomorrow);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,
                DAY_IN_MILLISECONDS, pendingIntent);
        Toast.makeText(context, DateUtils.
                getRelativeTimeSpanString(time, System.currentTimeMillis(), 0), Toast.LENGTH_SHORT).show();
        Util.enableReceiver(context);



    }

    public void fire(){
        fire(true);
    }

    public void cancel(){
        alarmManager.cancel(pendingIntent);
        Util.disableReceiver(context);
    }


    public void setAlarmTime(AlarmTime alarmTime){
        this.alarmTime = alarmTime;
    }

    public AlarmTime getAlarmTime(){
        return this.alarmTime;
    }

    public String getName(){
        return this.name;
    }


}
