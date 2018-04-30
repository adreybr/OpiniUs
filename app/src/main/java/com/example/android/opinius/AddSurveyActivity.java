package com.example.android.opinius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddSurveyActivity extends AppCompatActivity {
    private EditText mJudulEditText;

    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);

        mJudulEditText = (EditText) findViewById(R.id.isiJudul);
    }

    public void tambahPertanyaan(View view) {
        Intent intent = new Intent(AddSurveyActivity.this, QuestionList.class);
        intent.putExtra(JUDUL_SURVEY, mJudulEditText.getText().toString());
        startActivityForResult(intent, 303);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(AddSurveyActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 303){
            if(resultCode == RESULT_OK){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}