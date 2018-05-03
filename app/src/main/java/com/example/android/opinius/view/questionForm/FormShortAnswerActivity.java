package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.opinius.R;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;

public class FormShortAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private String mJudulSurvey;
    private EditText mQuestionContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_short_answer);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra("JUDUL");
        mQuestionContent = findViewById(R.id.question_content_short);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {
        mHelper = new SurveyDBHelper(this);

        mQuestionContent = findViewById(R.id.question_content_short);
        boolean validation = true;

        if (mQuestionContent.getText().length() == 0) {
            mQuestionContent.setError("Question is required!");
            validation = false;
        }

        if (validation) {
            String answerList = "";
            Log.d("ANSWER_LIST", answerList);
            mHelper = new SurveyDBHelper(this);
            Long id = mHelper.insertQuestion(mJudulSurvey, mQuestionContent.getText().toString(), answerList, Question.TYPE_SHORT_ANSWER);
            Intent replyIntent = new Intent();
            replyIntent.putExtra("questionID", id);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
