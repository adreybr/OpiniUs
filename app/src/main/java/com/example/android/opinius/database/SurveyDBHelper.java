package com.example.android.opinius.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.opinius.database.model.question.Question;

public class SurveyDBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "survey_db";

    public SurveyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        context.deleteDatabase(DATABASE_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Tables
        db.execSQL(Question.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Question.TABLE);
        // Create tables again
        onCreate(db);
    }

    public Long insertQuestion(String surveyTitle, String question, String answerList, int questionType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Question.COLUMN_SURVEY_TITLE, surveyTitle);
        values.put(Question.COLUMN_QUESTION, question);
        values.put(Question.COLUMN_ANSWER_LIST, answerList);
        values.put(Question.COLUMN_QUESTION_TYPE, questionType);

        long id = db.insert(Question.TABLE, null, values);
        db.close();
        return id;
    }

}
