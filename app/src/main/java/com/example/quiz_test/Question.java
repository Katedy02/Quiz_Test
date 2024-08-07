package com.example.quiz_test;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private String[] answers;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

    }

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }


    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer1() {return answer1;}

    public String getAnswer2() {return answer2;}

    public String getAnswer3() {return answer3;}

    public String getAnswer4() {return answer4;}

}

