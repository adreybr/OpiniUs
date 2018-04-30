package com.example.android.opinius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TambahSurveyActivity extends AppCompatActivity {
    private EditText mJudulEditText;

    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_survey);

        mJudulEditText = (EditText) findViewById(R.id.isiJudul);
    }

    public void tambahPertanyaan(View view) {
        Intent intent = new Intent(TambahSurveyActivity.this, TambahPertanyaan.class);
        intent.putExtra(JUDUL_SURVEY, mJudulEditText.getText().toString());
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_survey, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(TambahSurveyActivity.this, MainActivity.class);
        startActivity(intent);


        return super.onOptionsItemSelected(item);
    }
}