package com.example.android.opinius.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.SurveyAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;

import org.chalup.microorm.MicroOrm;

import java.util.List;

public class ViewSurveyActivity extends AppCompatActivity {
    SQLiteDatabase mDB;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SurveyDBHelper mHelper;
    private ListView mSurveyList;
    private ArrayAdapter<Question> mAdapter;
    private TextView noQuestionsView;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_survey);

        mHelper = new SurveyDBHelper(this);
        mSurveyList = (ListView) findViewById(R.id.list_survey);
        noQuestionsView = (TextView) findViewById(R.id.empty_questions_view);
        updateUI();

        mSurveyList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(ViewSurveyActivity.this, ViewFullSurvey.class);
                in.putExtra("surveyTarget", questions.get(position).getSurveyTitle());
                startActivityForResult(in, 109);
            }
        });
    }

    private void updateUI() {
        mDB = mHelper.getReadableDatabase();
        String whereClause = Question.COLUMN_ANSWER + " is not null or " + Question.COLUMN_ANSWER + " != ?";
        String[] whereArgs = new String[]{""};
        Cursor cursor = mDB.query(true, Question.TABLE, new String[]{Question.COLUMN_ID,
                        Question.COLUMN_SURVEY_TITLE,
                        Question.COLUMN_QUESTION,
                        Question.COLUMN_ANSWER_LIST,
                        Question.COLUMN_QUESTION_TYPE,
                        Question.COLUMN_ANSWER},
                whereClause, whereArgs, Question.COLUMN_SURVEY_TITLE, null, Question.COLUMN_ID, null);
//        if (cursor.getCount() != 0) {
        MicroOrm uOrm = new MicroOrm();

        questions = uOrm.listFromCursor(cursor, Question.class);
        if (mAdapter == null) {
            mAdapter = new SurveyAdapter(this, R.layout.survey_item, questions);
            mSurveyList.setAdapter(mAdapter);
        } else {
            Log.d("mAdapter", "updateUI: mAdapter = NOT null");
            mAdapter.clear();
            mAdapter.addAll(questions);
            mAdapter.notifyDataSetChanged();
        }
//    }
        toggleEmptySurvey();
        cursor.close();
        mDB.close();
    }

    private void toggleEmptySurvey() {
        // you can check notesList.size() > 0
        if (questions.size() > 0) {
            noQuestionsView.setVisibility(View.GONE);
        } else {
            noQuestionsView.setVisibility(View.VISIBLE);
        }
    }

}
