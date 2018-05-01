package com.example.android.opinius.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.opinius.R;
import com.example.android.opinius.database.model.SurveyContract;
import com.example.android.opinius.database.SurveyDBHelper;
import com.example.android.opinius.database.model.question.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase mDB;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SurveyDBHelper mHelper;
    private ListView mSurveyList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new SurveyDBHelper(this);
//        mDB = mHelper.getWritableDatabase();
//        mDB.execSQL("DROP TABLE IF EXISTS " + Question.TABLE);
        mSurveyList = (ListView) findViewById(R.id.list_survey);
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
                Intent intent = new Intent(MainActivity.this, AddSurveyActivity.class);
                startActivityForResult(intent, 101);
                return true;
            case R.id.lihat_survey:
                Intent intent1 = new Intent(MainActivity.this, ViewSurveyActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
