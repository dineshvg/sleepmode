package vg.dinesh.com.sleepmode;


import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

public class Manager {

    private static final String TAG = Manager.class.getSimpleName();

    public static int checkSilentMode(Context context) {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                Log.i(TAG, "Silent mode");
                return AudioManager.RINGER_MODE_SILENT;
            case AudioManager.RINGER_MODE_VIBRATE:
                Log.i(TAG,"Vibrate mode");
                return AudioManager.RINGER_MODE_VIBRATE;
            case AudioManager.RINGER_MODE_NORMAL:
                Log.i(TAG, "Normal mode");
                return AudioManager.RINGER_MODE_NORMAL;
        }
        return -1;
    }
}
