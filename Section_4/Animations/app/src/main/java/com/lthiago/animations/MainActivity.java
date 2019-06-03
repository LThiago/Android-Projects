package com.lthiago.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

//    boolean bartIsVisible = true;

    public void fade(View view) {
        Log.i("Info", "ImageView Tapped");

        ImageView bartImageView = findViewById(R.id.bartImageView);
        ImageView homerImageView = findViewById(R.id.homerImageView);

        bartImageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(1000);

//        if (bartIsVisible) {
//            bartImageView.animate().alpha(0).setDuration(2000);
//            homerImageView.animate().alpha(1).setDuration(2000);
//            bartIsVisible = false;
//        } else {
//            bartImageView.animate().alpha(1).setDuration(2000);
//            homerImageView.animate().alpha(0).setDuration(2000);
//            bartIsVisible = true;
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView bartImageView = findViewById(R.id.bartImageView);
        bartImageView.setX(-1250);
        bartImageView.animate().translationXBy(1250).rotation(3600).setDuration(2000);
    }
}
