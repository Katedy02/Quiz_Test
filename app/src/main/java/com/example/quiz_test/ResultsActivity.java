package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "HighScoresPrefs";
    private static final String SETTINGS_PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_ENABLED_KEY = "soundEnabled";

    private TextView scoreTextView;
    private TextView messageTextView;
    private Button replayButton;
    private Button homeButton;
    private MediaPlayer mediaPlayer;
    private String playerName;
    private String difficulty;
    private int score;
    private boolean isSoundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        messageTextView = findViewById(R.id.messageTextView);
        replayButton = findViewById(R.id.replayButton);
        homeButton = findViewById(R.id.homeButton);

        // Get data from intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        difficulty = intent.getStringExtra("difficulty");
        score = intent.getIntExtra("score", 0);

        scoreTextView.setText("Congratulations " + playerName + "! Your score is: " + score);

        // Load sound setting
        SharedPreferences prefs = getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        isSoundEnabled = prefs.getBoolean(SOUND_ENABLED_KEY, true);

        if (score > 80) {
            if (isSoundEnabled) {
                playSuccessSound();
            }
            messageTextView.setText("Congratulations, " + playerName + "!");
        } else {
            messageTextView.setText("Good try, " + playerName + "!");
        }

        saveHighScore(playerName, score, difficulty);

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

    private void playSuccessSound() {
        if (isSoundEnabled) {
            mediaPlayer = MediaPlayer.create(this, R.raw.success_sound);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
            });
        }
    }

    private void saveHighScore(String playerName, int score, String difficulty) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String key = "highScores_" + difficulty;
        String existingScores = sharedPreferences.getString(key, "");
        String newScore = playerName + ":" + score;
        String updatedScores = existingScores.isEmpty() ? newScore : existingScores + "\n" + newScore;

        editor.putString(key, updatedScores);
        editor.apply();
    }
}
