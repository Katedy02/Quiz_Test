package com.example.quiz_test;

import android.content.Context;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button replayButton;
    private Button homeButton;

    private String playerName;
    private String difficulty;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        replayButton = findViewById(R.id.replayButton);
        homeButton = findViewById(R.id.homeButton);

        // Get data from intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        difficulty = intent.getStringExtra("difficulty");
        score = intent.getIntExtra("score", 0);

        // Display score
        scoreTextView.setText("Score: " + score);

        // Save score to SharedPreferences
        saveScore();

        replayButton.setOnClickListener(v -> {
            Intent replayIntent = new Intent(ResultsActivity.this, SetupQuizActivity.class);
            startActivity(replayIntent);
            finish();
        });

        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }

    private void saveScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get existing scores
        Set<String> scoresSet = sharedPreferences.getStringSet(difficulty, new HashSet<>());

        // Convert set to list
        List<String> scoresList = new ArrayList<>(scoresSet);

        // Add new score
        scoresList.add(playerName + ":" + score);

        // Sort list by score in descending order
        Collections.sort(scoresList, (a, b) -> {
            int scoreA = Integer.parseInt(a.split(":")[1]);
            int scoreB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(scoreB, scoreA);
        });

        // Keep only top 10 scores
        if (scoresList.size() > 5) {
            scoresList = scoresList.subList(0, 5);
        }

        // Convert list back to set
        scoresSet = new HashSet<>(scoresList);

        // Save updated scores
        editor.putStringSet(difficulty, scoresSet);
        editor.apply();
    }
}
