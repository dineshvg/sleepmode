package vg.dinesh.com.sleepmode;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SleepActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    LinearLayout buttonLayout;
    RelativeLayout container;
    private final String TAG = SleepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        init();
        verifyTextAndIcon();
        initListeners();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        modifySnackBar();
    }

    private void modifySnackBar() {

        if(FlipService.serviceRunning==true) {
            Snackbar snack = Snackbar.make(container, R.string.modified_details, Snackbar.LENGTH_INDEFINITE);
            View view = snack.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            snack.show();
    } else {
            showSnackbar();
        }

    }

    private void verifyTextAndIcon() {

        if(FlipService.serviceRunning) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
    }

    private void initListeners() {

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int modeVal = Manager.checkSilentMode(getApplicationContext());
                if (modeVal != AudioManager.RINGER_MODE_SILENT) {
                    if (FlipService.serviceRunning==false) {
                        Log.d(TAG, "Service started");
                        toggleButton.setChecked(true);
                        Intent startIntent = new Intent(getBaseContext(), MotionControlService.class);
                        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                        startService(startIntent);
                        startService(new Intent(getBaseContext(), FlipService.class));
                    } else {
                        Log.d(TAG, "Service stopped");
                        toggleButton.setChecked(false);
                        Intent startIntent = new Intent(getBaseContext(), MotionControlService.class);
                        startIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                        startService(startIntent);
                        stopService(new Intent(getBaseContext(), FlipService.class));
                    }
                }
                modifySnackBar();
            }
        });

        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dineshvg1023"+"@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QUERY/ISSUE : SleepMode app");
                startActivity(emailIntent);
            }
        });
    }

    private void init() {
        container = (RelativeLayout)findViewById(R.id.sleepContainer);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        buttonLayout = (LinearLayout)findViewById(R.id.buttonLayout2);
        showSnackbar();
    }

    private void showSnackbar() {

        Snackbar snack = Snackbar.make(container, R.string.init_details, Snackbar.LENGTH_INDEFINITE);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        snack.show();
    }

}
