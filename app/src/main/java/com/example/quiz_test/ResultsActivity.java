package com.example.quiz_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button replayButton;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        replayButton = findViewById(R.id.replayButton);
        homeButton = findViewById(R.id.homeButton);

        // Obține scorul din intent și afișează-l
        int score = getIntent().getIntExtra("score", 0);
        scoreTextView.setText("Scorul tău: " + score);

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reia quiz-ul
                Intent intent = new Intent(ResultsActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mergi la ecranul principal
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
