package com.example.countdownpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 1500000;
    private TextView myTextViewCountdown;
    private Button myButtonStartPause;
    private Button myButtonReset;
    private CountDownTimer myCountDownTimer;
    private boolean isTimerRunning;
    private long myTimeLeftMillis = START_TIME_IN_MILLIS;


    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextViewCountdown = findViewById(R.id.textViewTimer);
        myButtonStartPause = findViewById(R.id.start_pause_button);
        myButtonReset = findViewById(R.id.reset_button);


        Intent intent = getIntent();
        if (intent.hasExtra("check")) {
            startTimer();
        }


        /////////////////////////////////////
        myButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning){
                    pauseTimer();
                }else {
                    startTimer();
                }



            }
        });
        ////////////////////////////////////
        myButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();

    }
    ///////////////////////////////////////////////
    public void startTimer(){
        myCountDownTimer = new CountDownTimer(myTimeLeftMillis, 1000) {
            @Override
            public void onTick(long l) {
                myTimeLeftMillis = l;
                updateCountDownText();
                myButtonStartPause.setText("PAUSE");
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                myButtonStartPause.setText("START");
                playNotificarionSound();

                Intent intent = new Intent(getApplicationContext(), PauseActivity.class);
                startActivity(intent);



            }
        }.start();
        isTimerRunning = true;
    }
    ///////////////////////////////////////////////







    private void pauseTimer(){
        myCountDownTimer.cancel();
        isTimerRunning = false;
        myButtonStartPause.setText("START");
    }
    private void resetTimer(){
        myTimeLeftMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }
    private void updateCountDownText(){
        int minutes = (int) (myTimeLeftMillis / 1000) / 60;
        int seconds = (int) (myTimeLeftMillis / 1000) % 60;
        String resultTime = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        myTextViewCountdown.setText(resultTime);
    }

    private void playNotificarionSound (){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}