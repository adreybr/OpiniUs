package com.example.android.opinius.model;

import java.util.List;

/**
 * Created by AdreyBenaya on 26/04/2018.
 */

public class MultipleAnswerQuestion extends Question {
    private List answer;

    public MultipleAnswerQuestion(int id, String surveyTitle, String content, int questionType, List answer) {
        super(id, surveyTitle, content, questionType);
        this.answer = answer;
    }

    public List getAnswer() {
        return answer;
    }

    public void setAnswer(List answer) {
        this.answer = answer;
    }
}
