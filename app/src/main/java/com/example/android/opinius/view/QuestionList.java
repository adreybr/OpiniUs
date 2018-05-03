package com.example.android.opinius.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.opinius.R;
import com.example.android.opinius.adapter.QuestionListRecyclerAdapter;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.database.model.question.Question;
import com.example.android.opinius.view.questionForm.FormMultipleAnswerActivity;
import com.example.android.opinius.view.questionForm.FormShortAnswerActivity;
import com.example.android.opinius.view.questionForm.FormSingleAnswerActivity;

import org.chalup.microorm.MicroOrm;

import java.util.List;

public class QuestionList extends AppCompatActivity {
    private SurveyDBHelper mHelper;
    private RecyclerView mQuestionListView;
    private QuestionListRecyclerAdapter mAdapter;
    private TextView mJudulSurvey;
    private RadioGroup radioGroup;
    private String message;
    private RadioButton radioButton;

    public static final String JUDUL_SURVEY = "com.example.android.opinius.extra.JUDUL_SURVEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Intent intent = getIntent();
        message =
                intent.getStringExtra("JUDUL");
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
                add_question();
                return true;
            case R.id.save_survey:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add_question() {
        LayoutInflater inflater = QuestionList.this.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.activity_question_type, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        radioGroup = dialogLayout.findViewById(R.id.tipeJawab);

                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        radioButton = (RadioButton) findViewById(selectedId);

                        if (selectedId == R.id.tipeIsian) {
                            Intent intentIsian = new Intent(QuestionList.this, FormShortAnswerActivity.class);
                            intentIsian.putExtra("JUDUL", message);
                            startActivityForResult(intentIsian, 202);
                        } else if (selectedId == R.id.tipeSatuJawaban) {
                            Intent intentSatu = new Intent(QuestionList.this, FormSingleAnswerActivity.class);
                            intentSatu.putExtra("JUDUL", message);
                            startActivityForResult(intentSatu, 202);
                        } else if (selectedId == R.id.tipeBanyakJawaban) {
                            Intent intentBanyak = new Intent(QuestionList.this, FormMultipleAnswerActivity.class);
                            intentBanyak.putExtra("JUDUL", message);
                            startActivityForResult(intentBanyak, 202);
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
        dialog.show();
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
//        while (cursor.moveToNext()) {
//            int idx =
//                    cursor.getColumnIndex(Question.COLUMN_QUESTION);
////            questionList.add(cursor.getString(idx));
//        }
        if (cursor.getCount() != 0) {
            MicroOrm uOrm = new MicroOrm();

            List<Question> questions;
            questions = uOrm.listFromCursor(cursor, Question.class);

            if (mAdapter == null) {
                Log.d("mAdapter", "updateUI: mAdapter = null");
                mAdapter = new QuestionListRecyclerAdapter(this, questions);
                mQuestionListView.setAdapter(mAdapter);
                mQuestionListView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.d("mAdapter", "updateUI: mAdapter = NOT null");
                mAdapter.swap(questions);
                mAdapter.notifyDataSetChanged();
            }

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
