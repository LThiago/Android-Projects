package com.lthiago.timestable;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Integer> generateArray(int number) {
        ArrayList<Integer> timesTable = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            timesTable.add(number * i);
        }
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timesTable);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int min = 1;
        int max = 20;
        int startingPosition = 10;

        SeekBar numberSeekBar = findViewById(R.id.numberSeekBar);
        numberSeekBar.setMin(min);
        numberSeekBar.setMax(max);
        numberSeekBar.setProgress(startingPosition);

        final ListView resultList = findViewById(R.id.resultListView);

        resultList.setAdapter(generateArray(startingPosition));

        numberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultList.setAdapter(generateArray(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Slide to select a number", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
