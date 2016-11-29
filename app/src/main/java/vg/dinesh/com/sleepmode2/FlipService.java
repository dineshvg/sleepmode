package vg.dinesh.com.sleepmode2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FlipService extends Service implements SensorEventListener {

    private final static String TAG = FlipService.class.getSimpleName();
    private SensorManager sensorManager;
    boolean mStarted = false;
    public static Boolean serviceRunning = false;
    private float mGZ = 0;//gravity acceleration along the z axis
    private int mEventCountSinceGZChanged = 0;
    private static final int MAX_COUNT_GZ_CHANGE = 10;

    @Override
    public void onDestroy() {
        serviceRunning = false;
        sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (!mStarted) {
            serviceRunning = true;
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_GAME);
            mStarted = true;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(serviceRunning) {
            AudioManager am;
            am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            int type = event.sensor.getType();
            if (type == Sensor.TYPE_ACCELEROMETER) {
                float gz = event.values[2];
                if (mGZ == 0) {
                    mGZ = gz;
                } else {
                    if ((mGZ * gz) < 0) {
                        mEventCountSinceGZChanged++;
                        if (mEventCountSinceGZChanged == MAX_COUNT_GZ_CHANGE) {
                            mGZ = gz;
                            mEventCountSinceGZChanged = 0;
                            if (gz > 0) {
                                //Log.d(TAG, "now screen is facing up.");
                                int old_mode = SharedPref.read();
                                //Log.d(TAG,"old mode "+old_mode);
                                if(Manager.checkSilentMode(getApplicationContext())== AudioManager.RINGER_MODE_SILENT) {
                                    am.setRingerMode(old_mode);
                                    //Log.d(TAG,"Ringer mode is now "+old_mode);
                                }
                            } else if (gz < 0) {
                                //Log.d(TAG, "now screen is facing down.");
                                if(Manager.checkSilentMode(getApplicationContext())!= AudioManager.RINGER_MODE_SILENT) {
                                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                    //Log.d(TAG, "Ringer mode is now " + Manager.checkSilentMode(getApplicationContext()));
                                }
                            }
                        }
                    } else {
                        if (mEventCountSinceGZChanged > 0) {
                            mGZ = gz;
                            mEventCountSinceGZChanged = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
