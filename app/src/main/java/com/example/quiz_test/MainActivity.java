package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_ENABLED_KEY = "soundEnabled";

    private Switch soundSwitch;
    private Button startQuizButton;
    private Button viewHighScoresButton;
    private boolean isSoundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundSwitch = findViewById(R.id.soundSwitch);
        startQuizButton = findViewById(R.id.startQuizButton);
        viewHighScoresButton = findViewById(R.id.viewHighScoresButton);

        loadSoundSetting();
        setupListeners();
    }

    private void loadSoundSetting() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isSoundEnabled = prefs.getBoolean(SOUND_ENABLED_KEY, true);
        soundSwitch.setChecked(isSoundEnabled);
    }

    private void setupListeners() {
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSoundEnabled = isChecked;
            saveSoundSetting(isSoundEnabled);
        });

        startQuizButton.setOnClickListener(v -> startQuizActivity());
        viewHighScoresButton.setOnClickListener(v -> viewHighScoresActivity());
    }

    private void saveSoundSetting(boolean isEnabled) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SOUND_ENABLED_KEY, isEnabled);
        editor.apply();
    }

    private void startQuizActivity() {
        Intent intent = new Intent(MainActivity.this, SetupQuizActivity.class);
        intent.putExtra("isSoundEnabled", isSoundEnabled);
        startActivity(intent);
    }

    private void viewHighScoresActivity() {
        Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
        intent.putExtra("isSoundEnabled", isSoundEnabled);
        startActivity(intent);
    }
}
