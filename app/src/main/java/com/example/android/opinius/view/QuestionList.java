package com.example.android.opinius.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.RVAdapter;
import com.example.android.opinius.database.model.SurveyContract;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.database.model.question.Question;

import org.chalup.microorm.MicroOrm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionList extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private RecyclerView mQuestionListView;
    private RVAdapter mAdapter;
    private TextView mJudulSurvey;
    private ArrayList<Question> questions = new ArrayList<>();

    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Intent intent = getIntent();
        String message =
                intent.getStringExtra(AddSurveyActivity.JUDUL_SURVEY);
        mJudulSurvey = (TextView) findViewById(R.id.survey_title);
        mJudulSurvey.setText(message);

        mHelper = new SurveyDBHelper(this);
        mQuestionListView = (RecyclerView) findViewById(R.id.question_list);

        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambah_pertanyaan:
                Intent intent = new Intent(QuestionList.this, QuestionTypeActivity.class);
                intent.putExtra(JUDUL_SURVEY, mJudulSurvey.getText().toString());
                startActivityForResult(intent, 202);
                return true;
            case R.id.save_survey:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {
//        ArrayList<Question> questionList = new ArrayList<>();
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
//        while (cursor.moveToNext()) {
//            int idx =
//                    cursor.getColumnIndex(Question.COLUMN_QUESTION);
////            questionList.add(cursor.getString(idx));
//        }
        if (cursor.getCount() != 0) {
            MicroOrm uOrm = new MicroOrm();
//            Question o = uOrm.fromCursor(cursor, Question.class);
//            ContentValues values = uOrm.toContentValues(o);

            // in case you'll iterate over the whole cursor
//        Question o = new Question();
//        do {
//            uOrm.fromCursor(cursor, o);
//        } while (cursor.moveToNext());

// if you need to dump the whole cursor to list
            List<Question> questions;
            questions = uOrm.listFromCursor(cursor, Question.class);

            for (int i = 0; i < questions.size(); i++) {
                Log.d("QUESTION", i + " " + questions.get(i).getQuestion());
            }
            if (mAdapter == null) {
                // Create an adapter and supply the data to be displayed.
                mAdapter = new RVAdapter(this, questions);
                // Connect the adapter with the RecyclerView.
                mQuestionListView.setAdapter(mAdapter);
                // Give the RecyclerView a default layout manager.
                mQuestionListView.setLayoutManager(new LinearLayoutManager(this));
//            mAdapter = new ArrayAdapter<>(this,
//                    R.layout.question_item,
//                    R.id.questions,
//                    questionList);
//            mQuestionListView.setAdapter(mAdapter);
            } else {
//            mAdapter.clear();
//            mAdapter.addAll(questionList);
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.notifyDataSetChanged();
        }




        cursor.close();
        mDB.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 202) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Pertanyaan berhasil disimpan", Toast.LENGTH_SHORT).show();
                updateUI();
            }
        }
    }
}
