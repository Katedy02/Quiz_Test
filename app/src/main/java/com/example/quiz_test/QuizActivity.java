package com.example.quiz_test;

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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QuizActivity extends AppCompatActivity {

    private String[] questions;
    private String[][] answers;
    private int[] correctAnswers;
    private MediaPlayer correctSound;
    private MediaPlayer incorrectSound;
    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton;
    private Button submitButton;
    private TextView progressTextView;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean soundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Inițializăm efectele sonore
        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        submitButton = findViewById(R.id.submitButton);
        progressTextView = findViewById(R.id.progressTextView);

        // Citim setările pentru sunet
        SharedPreferences preferences = getSharedPreferences("QuizSettings", MODE_PRIVATE);
        soundEnabled = preferences.getBoolean("sound", true);

        loadQuestions();
        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion();
                } else {
                    showResults();
                }
            }
        });
    }

    private void loadQuestions() {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.questions);
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = jsonBuilder.toString();

        try {
            JSONArray questionsArray = new JSONArray(json);
            questions = new String[questionsArray.length()];
            answers = new String[questionsArray.length()][4];
            correctAnswers = new int[questionsArray.length()];

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);
                questions[i] = questionObject.getString("question");
                JSONArray answersArray = questionObject.getJSONArray("answers");

                for (int j = 0; j < answersArray.length(); j++) {
                    answers[i][j] = answersArray.getString(j);
                }

                correctAnswers[i] = questionObject.getInt("correctAnswer");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        answer1RadioButton.setText(answers[currentQuestionIndex][0]);
        answer2RadioButton.setText(answers[currentQuestionIndex][1]);
        answer3RadioButton.setText(answers[currentQuestionIndex][2]);
        answer4RadioButton.setText(answers[currentQuestionIndex][3]);
        answersRadioGroup.clearCheck();
        progressTextView.setText("Întrebarea " + (currentQuestionIndex + 1) + " din " + questions.length);
    }

    private void showResults() {
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    private void checkAnswer() {
        int selectedAnswerIndex = answersRadioGroup.indexOfChild(findViewById(answersRadioGroup.getCheckedRadioButtonId()));

        if (selectedAnswerIndex == correctAnswers[currentQuestionIndex]) {
            score += 10;
            if (soundEnabled) {
                correctSound.start();
            }
        } else {
            if (soundEnabled) {
                incorrectSound.start();
            }
        }
    }
}
