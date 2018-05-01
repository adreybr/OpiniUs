package com.example.android.opinius.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.sip.SipRegistrationListener;

import com.example.android.opinius.database.model.SurveyContract;
import com.example.android.opinius.database.model.question.Question;

import java.util.List;

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
//        String createTable = "CREATE TABLE " +
//                SurveyContract.SurveyEntry.TABLE + " (" +
//                SurveyContract.SurveyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                SurveyContract.SurveyEntry.COLUMN_SURVEY_TITLE + " TEXT NOT NULL, " +
//                SurveyContract.SurveyEntry.COLUMN_QUESTION + "TEXT NOT NULL, " +
//                SurveyContract.SurveyEntry.COLUMN_ANSWER_LIST + "TEXT," +
//                SurveyContract.SurveyEntry.COLUMN_ANSWER + "TEXT);";
//        db.execSQL(createTable);
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
