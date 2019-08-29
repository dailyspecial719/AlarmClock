package com.fragment1.generally2.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver", "Yay!");

        //fetch extra strings from intent
        String getYouString = intent.getExtras().getString("Extra");

        //create an intent to the ringtoneservice
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string from Main Activity to Ringtone Playing Service
        serviceIntent.putExtra("Extra", getYouString);


        //start the ringtone
        context.startService(serviceIntent);
    }
}
