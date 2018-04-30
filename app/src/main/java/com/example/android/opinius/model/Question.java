package com.example.android.opinius.model;

/**
 * Created by AdreyBenaya on 26/04/2018.
 */

public abstract class Question {
    private int id;
    private String surveyTitle;
    private String content;
    private int questionType;


    public Question(int id, String surveyTitle, String content, int questionType) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.content = content;
        this.questionType = questionType;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }


}
