package com.example.android.opinius.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.android.opinius.adapter.ViewFullSurveyAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;

import org.chalup.microorm.MicroOrm;

import java.util.List;

public class ViewFullSurvey extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private RecyclerView mQuestionListView;
    private ViewFullSurveyAdapter mAdapter;
    private TextView mJudulSurvey;
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

        if (textJudul != null) {
            mJudulSurvey.setText(textJudul);
        }

        updateUI();
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

            mAdapter = new ViewFullSurveyAdapter(this, questions);
            mQuestionListView.setAdapter(mAdapter);
            mQuestionListView.setLayoutManager(new LinearLayoutManager(this));

        }
        cursor.close();
        mDB.close();
    }

}
