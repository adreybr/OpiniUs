package com.example.android.opinius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.android.opinius.questionForm.FormBanyakJawabanActivity;
import com.example.android.opinius.questionForm.FormIsianActivity;
import com.example.android.opinius.questionForm.FormSatuJawabanActivity;

public class TipeJawabActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private String mJudulSurvey;

    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_jawab);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra(ListPertanyaan.JUDUL_SURVEY);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.tipeJawab);
        btnDisplay = (Button) findViewById(R.id.lanjut);

        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                if (selectedId == R.id.tipeIsian) {
                    Intent intentIsian = new Intent(TipeJawabActivity.this, FormIsianActivity.class);
                    intentIsian.putExtra(JUDUL_SURVEY, mJudulSurvey);
                    startActivityForResult(intentIsian, 202);
                } else if (selectedId == R.id.tipeSatuJawaban) {
                    Intent intentSatu = new Intent(TipeJawabActivity.this, FormSatuJawabanActivity.class);
                    startActivity(intentSatu);
                } else if (selectedId == R.id.tipeBanyakJawaban) {
                    Intent intentBanyak = new Intent(TipeJawabActivity.this, FormBanyakJawabanActivity.class);
                    startActivity(intentBanyak);
                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 202) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }
}
