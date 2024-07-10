package com.example.quiz_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button replayButton;
    private Button homeButton;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        replayButton = findViewById(R.id.replayButton);
        homeButton = findViewById(R.id.homeButton);

        // Obține scorul din intent și afișează-l
        score = getIntent().getIntExtra("score", 0);
        scoreTextView.setText("Scorul tău: " + score);

        saveHighScore(score);

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reia quiz-ul
                Intent intent = new Intent(ResultsActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mergi la ecranul principal
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveHighScore(int score) {
        SharedPreferences preferences = getSharedPreferences("QuizHighScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String highScoresString = preferences.getString("highScores", "");
        String[] highScoresArray = highScoresString.isEmpty() ? new String[0] : highScoresString.split(",");

        int[] highScores = new int[highScoresArray.length];
        for (int i = 0; i < highScoresArray.length; i++) {
            highScores[i] = Integer.parseInt(highScoresArray[i]);
        }

        highScores = Arrays.copyOf(highScores, highScores.length + 1);
        highScores[highScores.length - 1] = score;

        Arrays.sort(highScores);
        if (highScores.length > 10) {
            highScores = Arrays.copyOfRange(highScores, highScores.length - 10, highScores.length);
        }

        StringBuilder newHighScoresString = new StringBuilder();
        for (int i = 0; i < highScores.length; i++) {
            if (i > 0) {
                newHighScoresString.append(",");
            }
            newHighScoresString.append(highScores[i]);
        }

        editor.putString("highScores", newHighScoresString.toString());
        editor.apply();
    }
}
