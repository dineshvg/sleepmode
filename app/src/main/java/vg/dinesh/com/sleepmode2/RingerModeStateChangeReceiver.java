package vg.dinesh.com.sleepmode2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by dinesh on 29.11.16.
 */

public class RingerModeStateChangeReceiver extends BroadcastReceiver {

    private static final String TAG = RingerModeStateChangeReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        int ringer_mode = Manager.checkSilentMode(context);
        if(ringer_mode!=0) {
            //Log.d(TAG,"ringer_mode "+ringer_mode);
            SharedPref.write(ringer_mode);
        }
    }
}
