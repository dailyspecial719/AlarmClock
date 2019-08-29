package com.fragment1.generally2.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView update_text;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        TimeZone.getDefault();

        //Initialize alarmManager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        //Initialize timePicker
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //Initialize update_text
        update_text = (TextView) findViewById(R.id.update_text);

        //Create an instance of Calander
        final Calendar calendar = Calendar.getInstance();

        //create intent to the AlarmReceiver calss
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //Initialize start button
        Button start_button = (Button) findViewById(R.id.start_button);


        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);


                //convert 24 hour time format to 12 hour format
                if (hour > 12){
                    hourString = String.valueOf(hour - 12);
                }

                //converts 00:0 to 00:00
                if(minute < 10){
                    minuteString = "0" + String.valueOf(minute);

                }



                setAlarmText("Alarm Set To " + hourString + ":" + minuteString);

                //put extra string into myIntent
                //tells the clock that you pressed the alarm on button
                myIntent.putExtra("Extra", "Alarm On");


                //creates a pending intent to delay intent until the set time is reached
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);




            }
        });


        //Initialize stop button
        Button stop_button = (Button) findViewById(R.id.stop_button);

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmText("Alarm Off!");

                alarmManager.cancel(pendingIntent);

                //put extra string into myIntent
                //tells the clock that you pressed the alarm off button
                myIntent.putExtra("Extra", "Alarm Off");

                //stop the ringtone
                sendBroadcast(myIntent);
            }
        });




    }

    private void setAlarmText(String output) {
        update_text.setText(output);

    }
}
