package com.example.quiz_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Alte coduri pentru inițializare, dacă există

        // Inițializare buton (opțional, doar dacă vrei să faci ceva suplimentar cu butonul)
        Button homeButton = findViewById(R.id.homeButton);
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
}
