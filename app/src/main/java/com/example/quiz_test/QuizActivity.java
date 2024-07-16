package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button submitButton;
    private TextView progressTextView;

    private List<Question> questionsList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean isSoundOn;

    private String playerName;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        progressTextView = findViewById(R.id.progressTextView);

        // Get data from the intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        difficulty = intent.getStringExtra("difficulty");
        isSoundOn = intent.getBooleanExtra("isSoundOn", true); // Default sound is on

        if (playerName == null || difficulty == null) {
            Toast.makeText(this, "Player name or difficulty level not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        questionsList = getQuestionsByDifficulty(difficulty);
        Collections.shuffle(questionsList); // Shuffle questions

        loadNextQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                loadNextQuestion();
            }
        });
    }
    private void saveHighScore(String playerName, int score) {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String difficultyKey = difficulty.toLowerCase();

        Set<String> scoresSet = sharedPreferences.getStringSet(difficultyKey, new HashSet<>());
        List<String> scoresList = new ArrayList<>(scoresSet);
        scoresList.add(playerName + ":" + score);

        // Sort the list in descending order
        scoresList.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(":")[1]);
            int scoreB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(scoreB, scoreA);
        });

        // Limit the list to the top 5 scores
        if (scoresList.size() > 5) {
            scoresList = scoresList.subList(0, 5);
        }

        // Convert back to set
        Set<String> newScoresSet = new HashSet<>(scoresList);

        editor.putStringSet(difficultyKey, newScoresSet);
        editor.apply();
    }

    private List<Question> getQuestionsByDifficulty(String difficulty) {
        List<Question> selectedQuestions = new ArrayList<>();
        switch (difficulty) {
            case "easy":
                selectedQuestions = Question.getEasyQuestions();
                break;
            case "medium":
                selectedQuestions = Question.getMediumQuestions();
                break;
            case "hard":
                selectedQuestions = Question.getHardQuestions();
                break;
        }
        return selectedQuestions;
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(currentQuestionIndex);
            questionTextView.setText(currentQuestion.getQuestion());
            answersRadioGroup.removeAllViews();

            for (int i = 0; i < currentQuestion.getAnswers().length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(currentQuestion.getAnswers()[i]);
                radioButton.setId(i);
                answersRadioGroup.addView(radioButton);
            }
            currentQuestionIndex++;
            progressTextView.setText("Question: " + currentQuestionIndex + "/" + questionsList.size());
        } else {
            endQuiz();
        }
    }

    private void checkAnswer() {
        int selectedAnswerId = answersRadioGroup.getCheckedRadioButtonId();
        if (selectedAnswerId != -1) {
            Question currentQuestion = questionsList.get(currentQuestionIndex - 1);
            if (selectedAnswerId == currentQuestion.getCorrectAnswer()) {
                score += 10; // Score incremented by 10 for correct answer
                if (isSoundOn) {
                    MediaPlayer.create(this, R.raw.correct).start();
                }
            } else {
                if (isSoundOn) {
                    MediaPlayer.create(this, R.raw.incorrect).start();
                }
            }
        }
    }


    private void endQuiz() {
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("playerName", playerName);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();
    }
}

