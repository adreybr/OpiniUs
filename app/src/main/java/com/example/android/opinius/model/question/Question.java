package com.example.android.opinius.model.question;

import org.chalup.microorm.annotations.Column;

/**
 * Created by AdreyBenaya on 26/04/2018.
 */

public class Question {

    public static final String TABLE = "survey";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SURVEY_TITLE = "title";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER_LIST = "answer_list";
    public static final String COLUMN_QUESTION_TYPE = "question_type";
    public static final String COLUMN_ANSWER = "answer";

    public static final int TYPE_SHORT_ANSWER = 1;
    public static final int TYPE_SINGLE_ANSWER = 2;
    public static final int TYPE_MULTIPLE_ANSWER = 3;


    @Column(COLUMN_ID)
    private int id;
    @Column(COLUMN_SURVEY_TITLE)
    private String surveyTitle;
    @Column(COLUMN_QUESTION)
    private String question;
    @Column(COLUMN_ANSWER_LIST)
    private String answerList;
    @Column(COLUMN_QUESTION_TYPE)
    private int questionType;
    @Column(COLUMN_ANSWER)
    private String answer;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SURVEY_TITLE + " TEXT NOT NULL,"
                    + COLUMN_QUESTION + " TEXT NOT NULL,"
                    + COLUMN_ANSWER_LIST + " TEXT,"
                    + COLUMN_QUESTION_TYPE + " INTEGER NOT NULL,"
                    + COLUMN_ANSWER + " TEXT"
                    + ")";

    public Question() {
    }

    public Question(int id, String surveyTitle, String question, String answerList, int questionType) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.question = question;
        this.answerList = answerList;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
