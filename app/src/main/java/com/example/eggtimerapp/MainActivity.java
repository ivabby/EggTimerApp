package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    SeekBar seekBar;
    TextView time;
    Button controlButton;
    boolean activeTimer;

    public void update(int progress){
        seekBar.setProgress(progress);
        int minutes = progress/60;
        int seconds = progress - minutes * 60;

        String second = String.valueOf(seconds);

        if(seconds <= 9)
        {
            second = "0" + second;
        }

        time.setText(String.valueOf(minutes) + ":" + second);
    }

    public void reset(){
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        time.setText("0:30");
        seekBar.setProgress(30);
        controlButton.setText("Go!");
    }

    public void onClick(View view){
        if(!activeTimer){
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    controlButton.setText("Stop!");
                    seekBar.setEnabled(false);
                    update((int)millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.airhorn);
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        } else{
            reset();
        }

        activeTimer = !activeTimer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar =  (SeekBar) findViewById(R.id.progressBar);
        time =(TextView) findViewById(R.id.time);
        controlButton = (Button) findViewById(R.id.controlButton);

        activeTimer = false;

        seekBar.setMax(600);
        seekBar.setProgress(30);

        //  SeekBar change
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SeekBar" , "Progress: "+progress);

                update(progress);
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
