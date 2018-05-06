package com.example.android.opinius.view.questionForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.opinius.R;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;
import com.example.android.opinius.view.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class FormMultipleAnswerActivity extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private String mJudulSurvey;
    private EditText mQuestionContent;
    private EditText mAnswerChoiceInput;
    private List<String> listAnswer;
    private LinearLayout mAnswerListView;

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
        mQuestionContent = findViewById(R.id.question_content_multiple);
        boolean validation = true;

        if (mQuestionContent.getText().length() == 0) {
            mQuestionContent.setError("Pertanyaan wajib diisi");
            validation = false;
        }

        if (listAnswer.size() < 2) {
            if (listAnswer.size() == 0) {
                mAnswerChoiceInput.setError("Masukkan pilihan jawaban");
                validation = false;
            } else {
                mAnswerChoiceInput.setError("Pilihan jawaban minimal 2");
                validation = false;
            }
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
        boolean validation = true;

        if (!addChoice.equals("")) {
            if (listAnswer.size() > 0) {
                for (int i = 0; i < listAnswer.size(); i++) {
                    if (addChoice.equals(listAnswer.get(i))) {
                        mAnswerChoiceInput.setError("Pilihan jawaban sudah ada");
                        validation = false;
                        break;
                    } else {
                        validation = true;
                    }
                }
            }
            if (validation) {
                mAnswerChoiceInput.setError(null);
                listAnswer.add(addChoice);
                mAnswerListView = findViewById(R.id.checkbox_group);

                LayoutInflater mInflater = LayoutInflater.from(this);

                View view = mInflater.inflate(R.layout.checkbox_item, mAnswerListView, false);
                CheckBox option = view.findViewById(R.id.checkbox_option);
                option.setText(addChoice);
                mAnswerListView.addView(view);
                mAnswerChoiceInput.setText("");
            }
        } else {
            mAnswerChoiceInput.setError("Pilihan jawaban tidak boleh kosong");
        }

    }
}
