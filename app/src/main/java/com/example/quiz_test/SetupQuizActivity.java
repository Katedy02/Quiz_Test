package com.example.quiz_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetupQuizActivity extends AppCompatActivity {

    private EditText editTextPlayerName;
    private RadioGroup radioGroupDifficulty;
    private Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_quiz);

        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        radioGroupDifficulty = findViewById(R.id.radioGroupDifficulty);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = editTextPlayerName.getText().toString();
                int selectedDifficultyId = radioGroupDifficulty.getCheckedRadioButtonId();
                String difficulty = "easy";  // default

                if (selectedDifficultyId == R.id.radioButtonEasy) {
                    difficulty = "easy";
                } else if (selectedDifficultyId == R.id.radioButtonMedium) {
                    difficulty = "medium";
                } else if (selectedDifficultyId == R.id.radioButtonHard) {
                    difficulty = "hard";
                }

                Intent intent = new Intent(SetupQuizActivity.this, QuizActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        });
    }
}



