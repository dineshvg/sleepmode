package vg.dinesh.com.sleepmode;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MotionControlService extends Service {

    private static final String TAG = "MotionControlService";
    RemoteViews notificationView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "service stopped");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {

            Intent notificationIntent = new Intent(this, SleepActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Intent playIntent = new Intent(this, FlipService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Log.d(TAG, "service started");
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("SleepMode")
                    .setTicker("Sleep Mode On")
                    .setContentText(getResources().getString(R.string.sleep_on))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_play, "Play", pplayIntent)
                    .build();


            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);

        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Stop Foreground Intent");

            Intent notificationIntent = new Intent(this, SleepActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Intent playIntent = new Intent(this, FlipService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("SleepMode")
                    .setTicker("Sleep Mode Off")
                    .setContentText(getResources().getString(R.string.sleep_off))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.vol_on))
                    .setSmallIcon(R.drawable.vol_on)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_play, "Play", pplayIntent)
                    .build();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);

        }else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)){
            Log.d(TAG,"Its clickable");
        }
        return START_STICKY;
    }
}
