package com.example.quiz_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {

    private TextView highScoresTextView;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        highScoresTextView = findViewById(R.id.highScoresTextView);
        homeButton = findViewById(R.id.homeButton);

        displayHighScores();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen(v);
            }
        });
    }

    // Metoda onClick pentru butonul "Go to Home Screen"
    public void goToHomeScreen(View view) {
        Intent intent = new Intent(HighScoresActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayHighScores() {
        SharedPreferences preferences = getSharedPreferences("QuizHighScores", MODE_PRIVATE);
        String highScoresString = preferences.getString("highScores", "");

        if (!highScoresString.isEmpty()) {
            String[] highScoresArray = highScoresString.split(",");
            StringBuilder highScoresDisplay = new StringBuilder("High Scores:\n\n");
            for (int i = highScoresArray.length - 1; i >= 0; i--) {
                highScoresDisplay.append(highScoresArray[i]).append("\n");
            }
            highScoresTextView.setText(highScoresDisplay.toString());
        } else {
            highScoresTextView.setText("No high scores yet.");
        }
    }
}
