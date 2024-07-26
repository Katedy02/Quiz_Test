package com.example.quiz_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HighScoresFragment extends Fragment {

    private static final String PREFS_NAME = "HighScoresPrefs";
    private static final String ARG_POSITION = "position";

    public static HighScoresFragment newInstance(int position) {
        HighScoresFragment fragment = new HighScoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_high_scores, container, false);
        TextView textViewScores = view.findViewById(R.id.textViewScores);
        Button buttonGoToHomeScreen = view.findViewById(R.id.buttonGoToHomeScreen);

        if (getArguments() != null) {
            int position = getArguments().getInt(ARG_POSITION);
            String difficulty = getDifficultyFromPosition(position);
            loadHighScores(textViewScores, difficulty);
        }

        buttonGoToHomeScreen.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(homeIntent);
            getActivity().finish();
        });

        return view;
    }

    private String getDifficultyFromPosition(int position) {
        switch (position) {
            case 0:
                return "easy";
            case 1:
                return "medium";
            case 2:
                return "hard";
            default:
                return "";
        }
    }

    private void loadHighScores(TextView textView, String difficulty) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String key = "highScores_" + difficulty;
        String highScores = sharedPreferences.getString(key, "No high scores yet");
        textView.setText(highScores);
    }
}
