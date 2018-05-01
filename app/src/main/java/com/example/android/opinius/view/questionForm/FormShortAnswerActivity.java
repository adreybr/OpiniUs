package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.android.opinius.R;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.view.QuestionTypeActivity;

public class FormShortAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private SQLiteDatabase mDB;
    private String mJudulSurvey;
    private EditText mQuestionContent;
    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_short_answer);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra(QuestionTypeActivity.JUDUL_SURVEY);

        mQuestionContent = findViewById(R.id.question_content);

    }


    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {

        mHelper = new SurveyDBHelper(this);
        Long id = mHelper.insertQuestion(mJudulSurvey, mQuestionContent.getText().toString(), "", 1);

//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Question.COLUMN_SURVEY_TITLE, mJudulSurvey);
//        values.put(Question.COLUMN_QUESTION, mQuestionContent.getText().toString());
//        db.insertWithOnConflict(SurveyContract.SurveyEntry.TABLE,
//                null,
//                values,
//                SQLiteDatabase.CONFLICT_REPLACE);
//        db.close();
        Intent replyIntent = new Intent();
        replyIntent.putExtra("questionID", id);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
