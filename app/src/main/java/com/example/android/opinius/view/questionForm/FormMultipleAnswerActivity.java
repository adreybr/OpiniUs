package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.CustomListAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.view.QuestionTypeActivity;

import java.util.List;

public class FormMultipleAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private String mJudulSurvey;
    private EditText mQuestionContent;
    private EditText mAnswerChoiceInput;
    private ListView mAnswerList;
    private List<String> listAnswer;
    private int choiceCount = 0;
    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_multiple_answer);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra(QuestionTypeActivity.JUDUL_SURVEY);

        mQuestionContent = findViewById(R.id.question_content);
        mAnswerChoiceInput = findViewById(R.id.answer_input);
        mAnswerList = findViewById(R.id.checkbox_group);


        findViewById(R.id.add_choice).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAnswerChoiceInput = findViewById(R.id.answer_input);
                addCheckboxButton();
            }
        });
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {
        mHelper = new SurveyDBHelper(this);
        String answerList = "";
        for (int i = 0; i < listAnswer.size(); i++) {
            answerList += listAnswer.get(i) + "#";
        }
        Long id = mHelper.insertQuestion(mJudulSurvey,
                mQuestionContent.getText().toString(), answerList, 3);

        Intent replyIntent = new Intent();
        replyIntent.putExtra("questionID", id);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void addCheckboxButton() {
        listAnswer.add(mAnswerChoiceInput.getText().toString());
        CheckBox checkBoxOption = new CheckBox(this);
        checkBoxOption.setText(mAnswerChoiceInput.getText().toString());
        checkBoxOption.setId(choiceCount + 1);
        choiceCount++;
        mAnswerList.addView(checkBoxOption);

//        LayoutInflater inflater = FormMultipleAnswerActivity.this.getLayoutInflater();
//        final View checkboxLayout = inflater.inflate(R.layout.checkbox_item, mAnswerList);


    }
}
