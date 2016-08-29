package br.edu.ifpr.webandroid.myalarm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import br.edu.ifpr.webandroid.myalarm.model.AlarmTime;
import br.edu.ifpr.webandroid.myalarm.util.Alarm;
import br.edu.ifpr.webandroid.myalarm.util.AlarmPreferences;


/**
 * Created by everaldo on 01/03/15.
 */
public class AlarmContainer extends LinearLayout {

    private String name;
    private Alarm alarm;
    private boolean enableAlarm;

    private final TextView alarmLabel;
    private final CheckBox enableAlarmCheckBox;
    private final TextView alarmTimeLabel;
    private final LinearLayout alarmDisplayContainer;
    private final Button selectAlarm;



    public AlarmContainer(Context context, AttributeSet attributes){
        super(context, attributes);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.alarm_container, this);

        alarmLabel = (TextView) findViewById(R.id.alarmLabel);
        enableAlarmCheckBox = (CheckBox) findViewById(R.id.enableAlarm);
        alarmDisplayContainer = (LinearLayout) findViewById(R.id.alarmDisplayContainer);
        alarmTimeLabel = (TextView) findViewById(R.id.alarmTime);
        selectAlarm = (Button) findViewById(R.id.selectAlarm);

        init(attributes, 0);
    }

    private void init(AttributeSet attributes, int defStyle) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attributes,
                R.styleable.AlarmContainer, defStyle, 0);

        name = typedArray.getString(R.styleable.AlarmContainer_name);
        String label = typedArray.getString(R.styleable.AlarmContainer_label);
        typedArray.recycle();

        alarmLabel.setText(label);

        enableAlarm = getAlarmPreferences().getAlarmStatus();
        enableAlarmCheckBox.setChecked(enableAlarm);

        initAlarm();
        initCheckboxListener();
        initDisplayContainer();
        initSelectButtonListener();
    }

    private void initAlarm(){
        if(name.equals("morning")){
            alarm = Alarm.createMorningAlarm(getContext());
        }else{
            alarm = Alarm.createEveningAlarm(getContext());
        }
        alarm.setAlarmTime(getAlarmPreferences().getAlarmTime());
    }

    private void initCheckboxListener(){
        enableAlarmCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enableAlarm = ((CheckBox) v).isChecked();
                if(enableAlarm){
                   showDisplayContainer();
                   fireAlarm();
                }
                else{
                    hideDisplayContainer();
                    alarm.cancel();
                    Toast.makeText(getContext().getApplicationContext(),
                            getContext().getString(R.string.canceling_alarm), Toast.LENGTH_SHORT).show();
                }

                AlarmPreferences preferences = getAlarmPreferences();
                preferences.setAlarmStatus(enableAlarm);

            }
        });

    }

    private void initSelectButtonListener(){
        selectAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmPickerFragment alarmPicker = new AlarmPickerFragment();
                alarmPicker.setAlarm(alarm);
                alarmPicker.setLabel(alarmTimeLabel);
                alarmPicker.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), name + "Picker");
            }
        });
    }

    private void initDisplayContainer(){
        if (enableAlarm){
            showDisplayContainer();
        }
        else{
            hideDisplayContainer();
        }
    }

    private void showDisplayContainer(){
        alarmDisplayContainer.setVisibility(View.VISIBLE);
        alarmTimeLabel.setVisibility(View.VISIBLE);
        alarmTimeLabel.setText(alarm.getAlarmTime().toString());
    }

    private void hideDisplayContainer(){
        alarmDisplayContainer.setVisibility(View.GONE);

    }

    private void fireAlarm(){
        AlarmPreferences preferences = getAlarmPreferences();
        AlarmTime alarmTime = preferences.getAlarmTime();
        alarm.setAlarmTime(alarmTime);
        alarm.fire();

    }

    private AlarmPreferences getAlarmPreferences(){
        return new AlarmPreferences(getContext().getApplicationContext(), name);
    }


    public static class AlarmPickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        private Alarm alarm;
        private TextView label;

        public void setAlarm(Alarm alarm){
            this.alarm = alarm;

        }

        public void setLabel(TextView label){
            this.label = label;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstance){
            AlarmTime alarmTime = alarm.getAlarmTime();
            int hours = alarmTime.getHours();
            int minutes = alarmTime.getMinutes();

            return new TimePickerDialog(getActivity(), this, hours, minutes,
                    DateFormat.is24HourFormat(getActivity()));

        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            AlarmTime newAlarm = AlarmTime.create(hourOfDay, minute);
            saveNewAlarm(newAlarm);
            updateLabel(newAlarm);
            updateAlarm(newAlarm);
        }

        private void saveNewAlarm(AlarmTime newAlarm){
            AlarmPreferences preferences = new AlarmPreferences(getActivity(), alarm.getName());
            preferences.setAlarmTime(newAlarm);
        }

        private void updateLabel(AlarmTime newAlarm){
            label.setText(newAlarm.toString());
            label.setVisibility(View.VISIBLE);
        }

        private void updateAlarm(AlarmTime newAlarm){
            alarm.setAlarmTime(newAlarm);
            alarm.cancel();
            alarm.fire();
        }

    }
}
