package com.tevin.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    //Reset the stopwatch when te Reset Button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //Stop the stopwatch running when the Stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    //Start the stopwatch running when the Start button is clicked.
    public void OnclickStart(View view) {
        running = true;
    }

    @Override
    protected void onStop ()
    {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    protected void onStart()
    {
        super.onStart();
        if(wasRunning)
        {
            running = true;
        }
    }

    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
    }

    protected void onResume ()
    {
        super.onResume();
        if (wasRunning)
        {
            running = true;
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById((R.id.time_view));
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                //Convert seconds into hours, minutes and seconds.
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time); //Set the textview text
                if (running)
                {
                    seconds++; //if running is true, increment the seconds variable
                }
                handler.postDelayed(this, 1000); //Post code in the Runnable to be run again after a delay of 1000 milliseconds
            }
        });
    }
}