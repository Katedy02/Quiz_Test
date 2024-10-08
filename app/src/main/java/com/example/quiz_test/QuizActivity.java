package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button submitButton;
    private TextView progressTextView;

    private List<Question> questionsList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean isSoundEnabled;

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

        // Load sound setting
        SharedPreferences prefs = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
        isSoundEnabled = prefs.getBoolean("soundEnabled", true);

        if (playerName == null || difficulty == null) {
            Toast.makeText(this, "Player name or difficulty level not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        questionsList = getQuestionsByDifficulty(difficulty);
        if (questionsList.isEmpty()) {
            Toast.makeText(this, "No questions available for the selected difficulty", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Collections.shuffle(questionsList); // Shuffle questions

        loadNextQuestion();

        submitButton.setOnClickListener(v -> {
            if (answersRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
            } else {
                checkAnswer();
                loadNextQuestion();
            }
        });
    }

    private List<Question> getQuestionsByDifficulty(String difficulty) {
        List<Question> selectedQuestions = new ArrayList<>();
        String fileName = "";

        switch (difficulty.toLowerCase()) {
            case "easy":
                fileName = "easy_questions.json";
                break;
            case "medium":
                fileName = "medium_questions.json";
                break;
            case "hard":
                fileName = "hard_questions.json";
                break;
        }

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder jsonString = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonString.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String questionText = jsonObject.getString("question");
                JSONArray answersArray = jsonObject.getJSONArray("answers");
                String[] answers = new String[answersArray.length()];

                for (int j = 0; j < answersArray.length(); j++) {
                    answers[j] = answersArray.getString(j);
                }

                int correctAnswer = jsonObject.getInt("correctAnswer");
                selectedQuestions.add(new Question(questionText, answers, correctAnswer));
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error reading or parsing JSON file", e);
            e.printStackTrace();
        }

        Log.d(TAG, "Loaded " + selectedQuestions.size() + " questions from " + fileName);
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
                if (isSoundEnabled) {
                    MediaPlayer.create(this, R.raw.correct).start();
                }
            } else {
                if (isSoundEnabled) {
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
