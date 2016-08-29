package br.edu.ifpr.webandroid.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.edu.ifpr.webandroid.myalarm.util.AlarmPreferences;
import br.edu.ifpr.webandroid.myalarm.model.AlarmTime;
import br.edu.ifpr.webandroid.myalarm.util.Alarm;

public class AlarmBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Context appContext = context.getApplicationContext();

            createAlarm(appContext, "morning");
            createAlarm(appContext, "evening");
        }
    }


    private void createAlarm(Context context, String alarmName){
        AlarmPreferences preferences = new AlarmPreferences(context, alarmName);
        boolean enableAlarm = preferences.getAlarmStatus();
        AlarmTime alarmTime = preferences.getAlarmTime();
        if (enableAlarm){
            Alarm alarm = Alarm.createAlarm(context, alarmName);
            alarm.setAlarmTime(alarmTime);
            alarm.fire(AlarmTime.FIRE_IMMEDIATELY);
        }
    }
}
