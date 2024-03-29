package com.lthiago.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;

    TextView resultTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    ConstraintLayout gameLayout;

    ArrayList<Integer> answers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.GONE);

    }

    public void start(View view) {
        goButton.setVisibility(View.GONE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            resultTextView.setText("Right");
            score++;
        } else {
            resultTextView.setText("Wrong");
        }
        numberOfQuestions++;
        scoreTextView.setText(String.format("%d/%d", score, numberOfQuestions));
        newQuestion();
    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(String.format("%d/%d", score, numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.GONE);
        resultTextView.setText("");

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.format("%02ds", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Game Over");
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void newQuestion() {
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(String.format("%s + %s", Integer.toString(a), Integer.toString(b)));

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                int wrongAnswer = rand.nextInt(41);

                while (wrongAnswer == a + b) {
                    wrongAnswer = rand.nextInt(41);
                }

                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
    }
}
