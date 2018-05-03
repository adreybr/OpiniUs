package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.CheckBoxAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.database.model.question.Question;
import com.example.android.opinius.view.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class FormMultipleAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private String mJudulSurvey;
    private EditText mQuestionContent;
    private EditText mAnswerChoiceInput;
    private List<String> listAnswer;
    private ListView mAnswerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_multiple_answer);

        Intent intent = getIntent();
        mJudulSurvey = intent.getStringExtra("JUDUL");
        listAnswer = new ArrayList<>();

        mQuestionContent = findViewById(R.id.question_content_multiple);
        mAnswerChoiceInput = findViewById(R.id.answer_input_multiple);
        mAnswerListView = findViewById(R.id.checkbox_group);


        findViewById(R.id.add_choice_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCheckboxButton();
            }
        });
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {
        mQuestionContent = findViewById(R.id.question_content_multiple);
        boolean validation = true;

        if (mQuestionContent.getText().length() == 0) {
            mQuestionContent.setError("Question is required!");
            validation = false;
        }
        if (listAnswer.size() == 0) {
            mAnswerChoiceInput.setError("Answer Choices is required");
            validation = false;
        }

        if (validation) {
            String answerList = "";
            for (int i = 0; i < listAnswer.size(); i++) {
                answerList += listAnswer.get(i) + "#";
            }
            Log.d("ANSWER_LIST", answerList);
            mHelper = new SurveyDBHelper(this);
            Long id = mHelper.insertQuestion(mJudulSurvey, mQuestionContent.getText().toString(), answerList, Question.TYPE_MULTIPLE_ANSWER);
            Intent replyIntent = new Intent();
            replyIntent.putExtra("questionID", id);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    public void addCheckboxButton() {
        mAnswerChoiceInput = findViewById(R.id.answer_input_multiple);
        String addChoice = mAnswerChoiceInput.getText().toString();
        if (!addChoice.equals("")) {
            Log.d("EDIT_TEXT", addChoice);
            listAnswer.add(addChoice);
            CheckBoxAdapter dataAdapter = new CheckBoxAdapter(this,
                    R.layout.checkbox_item, listAnswer);
            mAnswerListView = findViewById(R.id.checkbox_group);
            mAnswerListView.setAdapter(dataAdapter);
            mAnswerChoiceInput.setText("");
        } else {
//            Toast.makeText(this, "Answer can't null", Toast.LENGTH_SHORT).show();
            mAnswerChoiceInput.setError("Answer can't be null");
        }

    }
}
