package com.example.quiz_test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch soundSwitch;
    private RadioGroup difficultyRadioGroup;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        soundSwitch = findViewById(R.id.soundSwitch);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
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
    }
}
