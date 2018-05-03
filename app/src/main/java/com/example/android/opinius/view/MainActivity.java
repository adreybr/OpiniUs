package com.example.android.opinius.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.opinius.R;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.model.question.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase mDB;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SurveyDBHelper mHelper;
    private ListView mSurveyList;
    private ArrayAdapter<String> mAdapter;
    private TextView noQuestionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new SurveyDBHelper(this);
        mSurveyList = (ListView) findViewById(R.id.list_survey);
        noQuestionsView = (TextView) findViewById(R.id.empty_questions_view);
//        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambah_survey:
                add_survey();
                return true;
            case R.id.lihat_survey:
                Intent intent1 = new Intent(MainActivity.this, ViewSurveyActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add_survey() {
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_add_survey, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText mJudul = dialogLayout.findViewById(R.id.isiJudul);
                        Intent intent = new Intent(MainActivity.this, QuestionList.class);
                        intent.putExtra("JUDUL", mJudul.getText().toString());
                        intent.putExtras(intent);
                        startActivityForResult(intent, 303);
                    }
                })
                .setNegativeButton("BATAL", null)
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Survey berhasil disimpan", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }


    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        mDB = mHelper.getReadableDatabase();
        Cursor cursor = mDB.query(Question.TABLE,
                new String[]{Question.COLUMN_ID,
                        Question.COLUMN_SURVEY_TITLE,
                        Question.COLUMN_QUESTION,
                        Question.COLUMN_ANSWER_LIST,
                        Question.COLUMN_QUESTION_TYPE,
                        Question.COLUMN_ANSWER},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx =
                    cursor.getColumnIndex(Question.COLUMN_SURVEY_TITLE);
            taskList.add(cursor.getString(idx));

        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.survey_item,
                    R.id.survey_title,
                    taskList);
            mSurveyList.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        mDB.close();
    }

    private void toggleEmptySurvey() {
        // you can check notesList.size() > 0

        if (mHelper.getQuestionCount() > 0) {
            noQuestionsView.setVisibility(View.GONE);
        } else {
            noQuestionsView.setVisibility(View.VISIBLE);
        }
    }

}
