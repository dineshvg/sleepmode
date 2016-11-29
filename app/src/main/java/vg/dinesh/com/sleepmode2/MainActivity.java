package vg.dinesh.com.sleepmode2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RelativeLayout container;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListeners();
        scheduleOpening();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        timer = new Timer();
        init();
        initListeners();
        scheduleOpening();
    }

    private void scheduleOpening() {

        timer.schedule(new TimerTask() {
            public void run() {
                Intent myIntent = new Intent(MainActivity.this, SleepActivity.class);
                startActivity(myIntent);
            }

        }, 1500);
    }

    private void initListeners() {

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                Intent myIntent = new Intent(MainActivity.this, SleepActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void init() {
        container = (RelativeLayout) findViewById(R.id.container);
        showSnackbar();
    }

    private void showSnackbar() {

        Snackbar snackbar = Snackbar
                .make(container,
                        "     Press anywhere to navigate", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }
}
