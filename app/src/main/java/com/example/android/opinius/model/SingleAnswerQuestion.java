package com.example.android.opinius.model;

/**
 * Created by AdreyBenaya on 26/04/2018.
 */

public class SingleAnswerQuestion extends Question {
    private String answer;

    public SingleAnswerQuestion(int id, String surveyTitle, String content, int questionType, String answer) {
        super(id, surveyTitle, content, questionType);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
