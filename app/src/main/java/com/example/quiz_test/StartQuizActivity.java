package com.example.quiz_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartQuizActivity extends AppCompatActivity {

    private EditText editTextPlayerName;
    private RadioButton radioButtonEasy;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHard;
    private Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_quiz);

        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        radioButtonEasy = findViewById(R.id.radioButtonEasy);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHard = findViewById(R.id.radioButtonHard);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = editTextPlayerName.getText().toString().trim();

                if (playerName.isEmpty()) {
                    Toast.makeText(StartQuizActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(StartQuizActivity.this, SetupQuizActivity.class);
                intent.putExtra("playerName", playerName);
                startActivity(intent);
            }
        });
    }
}



