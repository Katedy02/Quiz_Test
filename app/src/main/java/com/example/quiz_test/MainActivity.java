package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_ENABLED_KEY = "soundEnabled";
    private Switch soundSwitch;
    private boolean isSoundEnabled;
    private Button startQuizButton;
    private Button viewHighScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundSwitch = findViewById(R.id.soundSwitch);
        startQuizButton = findViewById(R.id.startQuizButton);
        viewHighScoresButton = findViewById(R.id.viewHighScoresButton);

        // Load saved sound settings
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isSoundEnabled = prefs.getBoolean(SOUND_ENABLED_KEY, true);
        soundSwitch.setChecked(isSoundEnabled);

        // Listener for Sound Switch
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSoundEnabled = isChecked;
            saveSoundSetting(isSoundEnabled);
        });

        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetupQuizActivity.class);
            intent.putExtra("isSoundEnabled", isSoundEnabled);
            startActivity(intent);
        });

        viewHighScoresButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
            intent.putExtra("isSoundEnabled", isSoundEnabled);
            startActivity(intent);
        });
    }

    private void saveSoundSetting(boolean isEnabled) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SOUND_ENABLED_KEY, isEnabled);
        editor.apply();
    }
}
