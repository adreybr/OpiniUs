package com.example.android.opinius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;

import com.example.android.opinius.questionForm.FormBanyakJawabanActivity;
import com.example.android.opinius.questionForm.FormIsianActivity;
import com.example.android.opinius.questionForm.FormSatuJawabanActivity;

public class TipeJawabActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_jawab);

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


                if(selectedId == R.id.tipeIsiian){
                    Intent intent = new Intent(TipeJawabActivity.this, FormIsianActivity.class);
                    startActivity(intent);

                } else if(selectedId == R.id.tipeSatuJawaban){
                    Intent intent = new Intent(TipeJawabActivity.this, FormSatuJawabanActivity.class);
                    startActivity(intent);
                } else if(selectedId == R.id.tipeBanyakJawaban){
                    Intent intent = new Intent(TipeJawabActivity.this, FormBanyakJawabanActivity.class);
                    startActivity(intent);
                }
            }

        });

    }


}
