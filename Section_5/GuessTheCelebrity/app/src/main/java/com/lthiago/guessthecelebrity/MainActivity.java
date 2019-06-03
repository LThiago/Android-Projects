package com.lthiago.guessthecelebrity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebrityImages = new ArrayList<>();
    ArrayList<String> celebrityNames = new ArrayList<>();
    ArrayList<Integer> chosenItems = new ArrayList<>();

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    ImageView celebrityImageView;

    int locationOfCorrectAnswer;
    int sortedCelebrity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillLists();
        setRound();
    }

    public void chooseAnswer(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, String.format("Wrong! It was %s", celebrityNames.get(sortedCelebrity)), Toast.LENGTH_SHORT).show();
        }
        setRound();
    }


    private void fillLists() {
        DownloadTask downloadTask = new DownloadTask();
        String pageHTML;
        try {
            pageHTML = downloadTask.execute("http://www.posh24.se/kandisar").get();
            String[] splitPageHTML = pageHTML.split("<div class=\"col-xs-12 col-sm-6 col-md-4\">");

            Pattern pattern = Pattern.compile("<img src=\"(.*?)\" alt="); //    Image Pattern
            Matcher matcher = pattern.matcher(splitPageHTML[0]);

            while (matcher.find()) {
                celebrityImages.add(matcher.group(1));
            }

            pattern = Pattern.compile("\" alt=\"(.*?)\"/>"); //    Image Pattern
            matcher = pattern.matcher(splitPageHTML[0]);

            while (matcher.find()) {
                celebrityNames.add(matcher.group(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int manageItems() {
        Random random = new Random();
        int sortedIndex = random.nextInt(celebrityImages.size());
        while (true) {
            if (chosenItems.contains(sortedIndex)) {
                sortedIndex = random.nextInt(celebrityImages.size());
            } else {
                chosenItems.add(sortedIndex);
                break;
            }
        }
        return sortedIndex;
    }

    private void setRound() {

        celebrityImageView = findViewById(R.id.celebrityImageView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        ImageDownloader imageDownloader = new ImageDownloader();

        ArrayList<String> answers = new ArrayList<>();

        Random random = new Random();

        locationOfCorrectAnswer = random.nextInt(4);

        Bitmap celebrityBitmap;

        try {
            sortedCelebrity = manageItems();
            celebrityBitmap = imageDownloader.execute(celebrityImages.get(sortedCelebrity)).get();
            celebrityImageView.setImageBitmap(celebrityBitmap);

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers.add(celebrityNames.get(sortedCelebrity));
                } else {
                    int wrongAnswer = random.nextInt(58);

                    while ((wrongAnswer == sortedCelebrity) && (!answers.contains(wrongAnswer))) {
                        wrongAnswer = random.nextInt(58);
                    }
                    answers.add(celebrityNames.get(wrongAnswer));
                }
            }

            button1.setText(answers.get(0));
            button2.setText(answers.get(1));
            button3.setText(answers.get(2));
            button4.setText(answers.get(3));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
