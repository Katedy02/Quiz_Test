package com.example.quiz_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button replayButton;
    private Button homeButton;

    private int score;
    private String playerName;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        replayButton = findViewById(R.id.replayButton);
        homeButton = findViewById(R.id.homeButton);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        playerName = intent.getStringExtra("playerName");
        difficulty = intent.getStringExtra("difficulty");

        scoreTextView.setText("Score: " + score);

        updateHighScores();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replayIntent = new Intent(ResultsActivity.this, SetupQuizActivity.class);
                startActivity(replayIntent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    private void updateHighScores() {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String highScoresKey = difficulty + "_high_scores";
        String highScores = sharedPreferences.getString(highScoresKey, "");

        List<HighScore> highScoreList = new ArrayList<>();
        if (!highScores.isEmpty()) {
            String[] scoresArray = highScores.split(",");
            for (String scoreEntry : scoresArray) {
                String[] parts = scoreEntry.split(":");
                highScoreList.add(new HighScore(parts[0], Integer.parseInt(parts[1])));
            }
        }

        highScoreList.add(new HighScore(playerName, score));

        Collections.sort(highScoreList);
        if (highScoreList.size() > 10) {
            highScoreList = highScoreList.subList(0, 10);
        }

        StringBuilder newHighScores = new StringBuilder();
        for (HighScore highScore : highScoreList) {
            newHighScores.append(highScore.getName()).append(":").append(highScore.getScore()).append(",");
        }

        editor.putString(highScoresKey, newHighScores.toString());
        editor.apply();
    }

    private static class HighScore implements Comparable<HighScore> {
        private String name;
        private int score;

        public HighScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(HighScore o) {
            return Integer.compare(o.score, this.score);
        }
    }
}
