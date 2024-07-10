package com.example.quiz_test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private ListView easyHighScoresListView, mediumHighScoresListView, hardHighScoresListView;
    private ArrayAdapter<String> easyScoresAdapter, mediumScoresAdapter, hardScoresAdapter;
    private List<String> easyHighScores, mediumHighScores, hardHighScores;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Inițializăm listele pentru scoruri
        easyHighScores = new ArrayList<>();
        mediumHighScores = new ArrayList<>();
        hardHighScores = new ArrayList<>();

        // Inițializăm adapterele pentru listele de scoruri
        easyScoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, easyHighScores);
        mediumScoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mediumHighScores);
        hardScoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hardHighScores);

        // Inițializăm ListView-urile
        easyHighScoresListView = findViewById(R.id.easyHighScoresListView);
        mediumHighScoresListView = findViewById(R.id.mediumHighScoresListView);
        hardHighScoresListView = findViewById(R.id.hardHighScoresListView);

        // Setăm adapterele pentru ListView-urile corespunzătoare
        easyHighScoresListView.setAdapter(easyScoresAdapter);
        mediumHighScoresListView.setAdapter(mediumScoresAdapter);
        hardHighScoresListView.setAdapter(hardScoresAdapter);

        // Simulăm adăugarea automată a scorurilor după finalizarea testelor
        // În aplicația reală, această logica va fi înlocuită cu cod care adaugă scorurile
        // utilizatorilor după fiecare test completat.
        addScore("easy", "Player 1", 100);
        addScore("easy", "Player 2", 80);
        addScore("medium", "Player 3", 120);
        addScore("medium", "Player 4", 90);
        addScore("hard", "Player 5", 150);
        addScore("hard", "Player 6", 110);
    }

    // Metodă pentru adăugarea scorului în funcție de nivelul de dificultate
    private void addScore(String difficulty, String playerName, int score) {
        switch (difficulty) {
            case "easy":
                easyHighScores.add(playerName + ": " + score);
                Collections.sort(easyHighScores, new ScoreComparator());
                easyScoresAdapter.notifyDataSetChanged();
                break;
            case "medium":
                mediumHighScores.add(playerName + ": " + score);
                Collections.sort(mediumHighScores, new ScoreComparator());
                mediumScoresAdapter.notifyDataSetChanged();
                break;
            case "hard":
                hardHighScores.add(playerName + ": " + score);
                Collections.sort(hardHighScores, new ScoreComparator());
                hardScoresAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    // Comparator pentru sortarea scorurilor în ordine descrescătoare
    class ScoreComparator implements Comparator<String> {
        @Override
        public int compare(String score1, String score2) {
            int score1Value = Integer.parseInt(score1.split(": ")[1]);
            int score2Value = Integer.parseInt(score2.split(": ")[1]);
            return Integer.compare(score2Value, score1Value); // Sortare descrescătoare după scor
        }
    }
}
