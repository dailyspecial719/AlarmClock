package com.fragment1.generally2.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class RingtonePlayingService extends Service {

    MediaPlayer mediaSong;
    private boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Local Service", "reveived start id" + startId + ": " + intent);

        //fetch the extra string values
        String state = intent.getExtras().getString("Extra");

        Log.e("Ringtone extra is", state);

        /*notification
        //set up the notification service
        NotificationManager notifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //set up intent that goes to mani activity
        Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

        //set up a pending intent
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0,
                intentMainActivity, 0);


        //make the notification parameters
        Notification notificationPopup = new Notification.Builder(this).setContentTitle("Alarm Is Going Off!")
                .setContentText("Click Me!").setContentIntent(pendingIntentMainActivity).build();

        //set up the notification call command
        assert notifyManager != null;
        notifyManager.notify(0, notificationPopup);*/




        assert state != null;
        switch (state) {
            case "Alarm On":
                startId = 1;
                break;
            case "Alarm Off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        //if else statements

        //if there IS NOT music playing and the user presses "alarm on
        //music should start playing
        if (!this.isRunning && startId == 1){
            //create an instance of the media player
            mediaSong = MediaPlayer.create(this, R.raw.got_theme);
            mediaSong.start();

            this.isRunning = true;
            startId = 0;
        }
        //if ther IS music playing and the user presses "alarm off"
        //music should stop playing
        else if (this.isRunning && startId == 0){

            //stop the ringtone
            mediaSong.stop();
            mediaSong.reset();
            this.isRunning = false;
            startId = 0;

        }
        //if there IS NOT music playing and the user presses "alarm off"
        //do nothing
        else if (!this.isRunning && startId == 0){
            this.isRunning =false;
            startId = 0;

        }
        //if there IS music playing and the user presses "alarm on"
        //do nothing
        else if (this.isRunning && startId == 1){
            this.isRunning = true;
            startId = 1;

        }
        else {
            this.isRunning = false;
            startId = 1;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}
