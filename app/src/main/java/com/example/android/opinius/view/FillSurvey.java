package com.example.android.opinius.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.FillSurveyAdapter;
import com.example.android.opinius.adapter.QuestionListRecyclerAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;
import com.example.android.opinius.view.MainActivity;
import com.example.android.opinius.view.ViewSurveyActivity;
import com.example.android.opinius.view.questionForm.FormMultipleAnswerActivity;
import com.example.android.opinius.view.questionForm.FormShortAnswerActivity;
import com.example.android.opinius.view.questionForm.FormSingleAnswerActivity;

import org.chalup.microorm.MicroOrm;

import java.util.List;

public class FillSurvey extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private RecyclerView mQuestionListView;
    private FillSurveyAdapter mAdapter;
    private TextView mJudulSurvey;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String textJudul;
    private List<Question> questions;
    private TextView noQuestionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_survey);


        mHelper = new SurveyDBHelper(this);
        mQuestionListView = (RecyclerView) findViewById(R.id.question_list);
        noQuestionsView = (TextView) findViewById(R.id.empty_questions_view);
        mJudulSurvey = (TextView) findViewById(R.id.survey_title);

        Intent intent = getIntent();
        textJudul = intent.getStringExtra("surveyTarget");
        Log.d("FILL SURVEY", "onCreate: " + textJudul);
        if (textJudul != null) {
            mJudulSurvey.setText(textJudul);
        }

        updateUI();
        toggleEmptyQuestion();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fill_survey, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_survey:
                submitSurvey();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitSurvey() {

        boolean validation = true;

        for (int i = 0; i < questions.size(); i++) {
            String answer;
            int type = mAdapter.getItemViewType(i);
            RecyclerView.ViewHolder vi = mQuestionListView.findViewHolderForLayoutPosition(i);

            if (validation) {
                switch (type) {
                    case Question.TYPE_SHORT_ANSWER:
                        EditText editText = vi.itemView.findViewById(R.id.answer_input_short);
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Wajib diisi...");
                            validation = false;
                        } else {
                            questions.get(i).setAnswer(editText.getText().toString());
                        }
                        break;
                    case Question.TYPE_SINGLE_ANSWER:
                        radioGroup = vi.itemView.findViewById(R.id.radio_group);

                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        radioButton = (RadioButton) findViewById(selectedId);


//                        RadioGroup radioGroup = vi.itemView.findViewById(R.id.radio_group);
//                        radioGroup.getChildCount();
//                        int id = radioGroup.getCheckedRadioButtonId();
                        Log.d("radio", "submitSurvey: checkedRadio " + Integer.toString(radioGroup.getChildCount()));
//                        RadioButton radioButton = radioGroup.findViewById(id);
                        answer = (String) radioButton.getText();
//                        String[] answerSplit = questions.get(i).getAnswerList().split("#");
//                        questions.get(i).setAnswer(answerSplit[radioGroup.getCheckedRadioButtonId()]);
                        break;
                    case Question.TYPE_MULTIPLE_ANSWER:
                        answer = "";
                        TextView textView = vi.itemView.findViewById(R.id.multi_answer_question);
                        LinearLayout linearLayout = vi.itemView.findViewById(R.id.listview_multiple);
                        String count = Integer.toString(linearLayout.getChildCount());
                        for (int j = 0; j < linearLayout.getChildCount(); j++) {
                            View view = linearLayout.getChildAt(j);
                            CheckBox checkBox = view.findViewById(R.id.checkbox_option);
                            if (checkBox.isChecked()) {
                                answer += checkBox.getText() + "#";
                            }
                        }
                        if (answer.length() > 0) {
                            questions.get(i).setAnswer(answer);
                        } else {
                            textView.setError("Wajib diisi...");
                            validation = false;
                        }
//                        Log.d(" submitSurvey", "submitSurvey: count " + count);
//                        Log.d(" submitSurvey", "submitSurvey: answer " + answer);
                        break;
                }
            }
            updateQuestion(questions.get(i).getAnswer(), i);
        }

        if (!validation) {
            Toast.makeText(this, "Isi Semua Pertanyaan!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hasil Survey telah disimpan..", Toast.LENGTH_SHORT).show();
            Intent replyIntent = new Intent();
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    private void updateQuestion(String answer, int position) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Question q = questions.get(position);
        // updating note text
        q.setAnswer(answer);

        // updating note in db
        mHelper.updateQuestion(q);

        // refreshing the list
        questions.set(position, q);
        mAdapter.notifyItemChanged(position);

        toggleEmptyQuestion();
    }

    private void toggleEmptyQuestion() {

        // you can check notesList.size() > 0
        if (questions.size() > 0) {
            noQuestionsView.setVisibility(View.GONE);
        } else {
            noQuestionsView.setVisibility(View.VISIBLE);
        }

    }

    public void updateUI() {
        SQLiteDatabase mDB = mHelper.getReadableDatabase();
        String whereClause = Question.COLUMN_SURVEY_TITLE + " = ?";
        String[] whereArgs = new String[]{mJudulSurvey.getText().toString()};
        Cursor cursor = mDB.query(Question.TABLE,
                new String[]{Question.COLUMN_ID,
                        Question.COLUMN_SURVEY_TITLE,
                        Question.COLUMN_QUESTION,
                        Question.COLUMN_ANSWER_LIST,
                        Question.COLUMN_QUESTION_TYPE,
                        Question.COLUMN_ANSWER},
                whereClause, whereArgs, null, null, null);
        if (cursor.getCount() != 0) {
            MicroOrm uOrm = new MicroOrm();
            questions = uOrm.listFromCursor(cursor, Question.class);

            mAdapter = new FillSurveyAdapter(this, questions);
            mQuestionListView.setAdapter(mAdapter);
            mQuestionListView.setLayoutManager(new LinearLayoutManager(this));

        }
        cursor.close();
        mDB.close();
    }

}
