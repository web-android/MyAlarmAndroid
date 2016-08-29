package br.edu.ifpr.webandroid.myalarm.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;


import java.util.Calendar;

import br.edu.ifpr.webandroid.myalarm.AlarmBootReceiver;

/**
 * Created by everaldo on 28/02/15.
 * Utility class
 */
public class Util {



    static void enableReceiver(Context context){
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    static void disableReceiver(Context context){
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static int getHours(){
        return getHours(System.currentTimeMillis());
    }

    public static int getHours(long time){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(){
        return getMinutes(System.currentTimeMillis());
    }

    public static int getMinutes(long time){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }


}
