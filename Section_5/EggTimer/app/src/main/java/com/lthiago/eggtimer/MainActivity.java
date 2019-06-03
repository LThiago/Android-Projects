package com.lthiago.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int MAX_TIMER = 600;
    int STARTING_POSITION = 30;

    TextView timerTextView;
    SeekBar timerSeekBar;
    Button interactionButton;
    CountDownTimer countDownTimer;

    boolean counterIsActive = false;


    private void resetTimer() {
        timerTextView.setText(formatTime(STARTING_POSITION));
        timerSeekBar = findViewById(R.id.timerSeekBar);
        interactionButton.setText("GO");
        counterIsActive = false;
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
    }

    private String formatTime(int progress) {

        String formatted;
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);

        if (seconds <= 9) {
            formatted = Integer.toString(minutes) + ":0" + Integer.toString(seconds);
        } else {
            formatted = Integer.toString(minutes) + ":" + Integer.toString(seconds);
        }
        return formatted;
    }

    public void buttonClicked(View view) {

        if (counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            interactionButton.setText("STOP");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(formatTime((int) (millisUntilFinished / 1000)));
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();

                    resetTimer();
                }
            }.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        interactionButton = findViewById(R.id.interactionButton);

        timerSeekBar.setMax(MAX_TIMER);
        timerSeekBar.setProgress(STARTING_POSITION);
        timerTextView.setText(formatTime(STARTING_POSITION));

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timerTextView.setText(formatTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
