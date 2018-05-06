package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.opinius.R;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;
import com.example.android.opinius.view.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class FormSingleAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private String mJudulSurvey;
    private EditText mQuestionContent;
    private EditText mAnswerChoiceInput;
    private RadioGroup mRadioGroup;
    private List<String> listAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_single_answer);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra("JUDUL");
        listAnswer = new ArrayList<>();

        mQuestionContent = findViewById(R.id.question_content_single);
        mAnswerChoiceInput = findViewById(R.id.answer_input_single);
        mRadioGroup = findViewById(R.id.radio_group);

        findViewById(R.id.add_choice_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRadioButtons();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, QuestionList.class);
                parentActivityIntent.putExtra("JUDUL", mJudulSurvey);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {
        mQuestionContent = findViewById(R.id.question_content_single);
        boolean validation = true;

        if (mQuestionContent.getText().length() == 0) {
            mQuestionContent.setError("Question is required");
            validation = false;
        }
        if (listAnswer.size() < 2) {
            if (listAnswer.size() == 0) {
                mAnswerChoiceInput.setError("Answer option(s) required");
                validation = false;
            } else {
                mAnswerChoiceInput.setError("At least 2 answer options needed");
                validation = false;
            }
        }


        if (validation) {
            String answerList = "";
            for (int i = 0; i < listAnswer.size(); i++) {
                answerList += listAnswer.get(i) + "#";
            }
//            Log.d("ANSWER_LIST", answerList);
            mHelper = new SurveyDBHelper(this);
            Long id = mHelper.insertQuestion(mJudulSurvey, mQuestionContent.getText().toString(), answerList, Question.TYPE_SINGLE_ANSWER);
            Intent replyIntent = new Intent();
            replyIntent.putExtra("questionID", id);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    public void addRadioButtons() {
        mAnswerChoiceInput = findViewById(R.id.answer_input_single);
        String addChoice = mAnswerChoiceInput.getText().toString();
        boolean validation = true;

        if (!addChoice.equals("")) {

            if (listAnswer.size() > 0) {
                for (int i = 0; i < listAnswer.size(); i++) {
                    if (addChoice.equals(listAnswer.get(i))) {
                        mAnswerChoiceInput.setError("This option already exists");
                        validation = false;
                        break;
                    } else {
                        validation = true;
                    }
                }
            }

            if (validation) {
                listAnswer.add(addChoice);
                mRadioGroup = findViewById(R.id.radio_group);

                RadioButton option = new RadioButton(this);
                option.setText(addChoice);
                mRadioGroup.addView(option);
                mAnswerChoiceInput.setText("");
            }
        } else {
            mAnswerChoiceInput.setError("Answer choices can't be null");
        }

    }
}
