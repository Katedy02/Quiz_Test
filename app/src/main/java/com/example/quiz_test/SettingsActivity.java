package com.example.quiz_test;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private RadioGroup difficultyRadioGroup;
    private SharedPreferences preferences;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        soundSwitch = findViewById(R.id.soundSwitch);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        homeButton = findViewById(R.id.homeButton);
        preferences = getSharedPreferences("QuizSettings", MODE_PRIVATE);

        soundSwitch.setChecked(preferences.getBoolean("sound", true));
        int difficulty = preferences.getInt("difficulty", R.id.easyRadioButton);
        difficultyRadioGroup.check(difficulty);

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("sound", isChecked).apply();
            }
        });

        difficultyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                preferences.edit().putInt("difficulty", checkedId).apply();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
