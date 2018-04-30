package com.example.android.opinius.questionForm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.opinius.R;
import com.example.android.opinius.TambahSurveyActivity;

public class FormBanyakJawabanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_banyak_jawaban);
    }

    public void batal(View view) {
        Intent intent = new Intent(FormBanyakJawabanActivity.this, TambahSurveyActivity.class);
        startActivity(intent);
    }

    public void simpan(View view) {
        Intent intent = new Intent(FormBanyakJawabanActivity.this, TambahSurveyActivity.class);
        startActivity(intent);
    }
}