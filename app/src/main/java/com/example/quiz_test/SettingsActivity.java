package com.example.quiz_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Switch soundSwitch;
    private Button homeButton;
    private MediaPlayer correctSoundPlayer;
    private MediaPlayer incorrectSoundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        soundSwitch = findViewById(R.id.soundSwitch);
        homeButton = findViewById(R.id.homeButton);
        correctSoundPlayer = MediaPlayer.create(this, R.raw.correct);
        incorrectSoundPlayer = MediaPlayer.create(this, R.raw.incorrect);

        SharedPreferences preferences = getSharedPreferences("QuizSettings", MODE_PRIVATE);
        boolean sound = preferences.getBoolean("sound", true);


        soundSwitch.setChecked(sound);


        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("sound", isChecked);
            editor.apply();
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
    // Method to play correct sound
    private void playCorrectSound() {
        if (soundSwitch.isChecked() && correctSoundPlayer != null) {
            correctSoundPlayer.start();
        }
    }

    // Method to play incorrect sound
    private void playIncorrectSound() {
        if (soundSwitch.isChecked() && incorrectSoundPlayer != null) {
            incorrectSoundPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        // Release media players
        if (correctSoundPlayer != null) {
            correctSoundPlayer.release();
            correctSoundPlayer = null;
        }
        if (incorrectSoundPlayer != null) {
            incorrectSoundPlayer.release();
            incorrectSoundPlayer = null;
        }
        super.onDestroy();
    }
}


