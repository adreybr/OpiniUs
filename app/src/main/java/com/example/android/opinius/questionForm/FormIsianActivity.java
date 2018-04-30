package com.example.android.opinius.questionForm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.opinius.R;
import com.example.android.opinius.TipeJawabActivity;

public class FormIsianActivity extends AppCompatActivity {
    private String mJudulSurvey;
    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_isian);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra(TipeJawabActivity.JUDUL_SURVEY);

    }

    public void batal(View view) {
    }

    public void simpanIsian(View view) {
        Intent replyIntent = new Intent();
//        startActivity(replyIntent);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
