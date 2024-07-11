package com.example.quiz_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HighScoresActivity extends AppCompatActivity {

    private TextView easyHighScoresTextView;
    private TextView mediumHighScoresTextView;
    private TextView hardHighScoresTextView;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        easyHighScoresTextView = findViewById(R.id.easyHighScoresTextView);
        mediumHighScoresTextView = findViewById(R.id.mediumHighScoresTextView);
        hardHighScoresTextView = findViewById(R.id.hardHighScoresTextView);
        homeButton = findViewById(R.id.homeButton);

        displayHighScores("easy", easyHighScoresTextView);
        displayHighScores("medium", mediumHighScoresTextView);
        displayHighScores("hard", hardHighScoresTextView);

        homeButton.setOnClickListener(v -> finish());
    }

    private void displayHighScores(String difficulty, TextView textView) {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        Set<String> scoresSet = sharedPreferences.getStringSet(difficulty, new HashSet<>());

        // Convert set to list and sort it
        List<String> scoresList = new ArrayList<>(scoresSet);
        scoresList.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(":")[1]);
            int scoreB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(scoreB, scoreA);
        });

        // Limit the list to the top 5 scores
        if (scoresList.size() > 5) {
            scoresList = scoresList.subList(0, 5);
        }

        // Build the display text
        StringBuilder displayText = new StringBuilder();
        for (String score : scoresList) {
            displayText.append(score).append("\n");
        }

        textView.setText(displayText.toString());
    }
}
