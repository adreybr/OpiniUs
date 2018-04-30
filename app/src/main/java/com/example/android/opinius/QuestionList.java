package com.example.android.opinius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionList extends AppCompatActivity {
    private TextView mJudulSurvey;
    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Intent intent = getIntent();
        String message =
                intent.getStringExtra(AddSurveyActivity.JUDUL_SURVEY);
        mJudulSurvey = (TextView) findViewById(R.id.judul_survey);
        mJudulSurvey.setText(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambah_pertanyaan:
                Intent intent = new Intent(QuestionList.this, QuestionTypeActivity.class);
                intent.putExtra(JUDUL_SURVEY, mJudulSurvey.getText().toString());
                startActivityForResult(intent, 202);
                return true;
            case R.id.save_survey:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 202) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Pertanyaan berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
