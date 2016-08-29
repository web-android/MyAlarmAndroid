package br.edu.ifpr.webandroid.myalarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.Random;


public class AlarmDisplayActivity extends ActionBarActivity {


    private ImageView imageView;
    private int selected_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);

        imageView = (ImageView) findViewById(R.id.memeImage);

        RoundedBitmapDrawable roundedCornerImage = getImage();
        imageView.setImageDrawable(roundedCornerImage);

        if (savedInstanceState == null) {
            createNotification(roundedCornerImage.getBitmap());
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putBoolean("notification", true);
    }

    private void createNotification(Bitmap icon) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                setSmallIcon(selected_image).
                setLargeIcon(icon).
                setSound(alarmSound).
                setAutoCancel(true).
                setContentTitle(getResources().getString(R.string.app_name)).
                setContentText(getResources().getString(R.string.medicine_time));

        Intent resultIntent = new Intent(this, AlarmDisplayActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0 , resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    private RoundedBitmapDrawable getImage() {
        int imageOfDay = getImageOfDayResourceId();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                imageOfDay);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
        dr.setAntiAlias(true);
        return dr;
    }

    private int getImageOfDayResourceId() {
        Random random = new Random(System.currentTimeMillis());
        int n = random.nextInt(5) + 1;
        int imageOfDay = 0;
        switch (n) {
            case 1:
                imageOfDay = R.drawable.meme01;
                break;
            case 2:
                imageOfDay =  R.drawable.meme02;
                break;
            case 3:
                imageOfDay =  R.drawable.meme03;
                break;
        }
        selected_image = imageOfDay;
        return imageOfDay;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_receiver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
