package vg.dinesh.com.sleepmode2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dinesh on 29.11.16.
 */

public class SharedPref {

    private static SharedPreferences mSharedPref;

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void write(int value) {
        if(mSharedPref!=null) {
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            prefsEditor.putInt(Constants.RING_MODE, value);
            prefsEditor.commit();
        }
    }

    public static Integer read() {
        return mSharedPref.getInt(Constants.RING_MODE, 2);
    }
}
