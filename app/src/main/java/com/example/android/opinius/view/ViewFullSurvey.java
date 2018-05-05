package com.example.android.opinius.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.opinius.R;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_survey, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                shareSurvey();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleEmptyQuestion() {
        // you can check notesList.size() > 0
        if (questions.size() > 0) {
            noQuestionsView.setVisibility(View.GONE);
        } else {
            noQuestionsView.setVisibility(View.VISIBLE);
        }
    }

    public void shareSurvey() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        String shareBody = "";

        for (int i = 0; i < questions.size(); i++) {
            int number = i + 1;
            if (questions.get(i).getQuestionType() == Question.TYPE_MULTIPLE_ANSWER) {
                String normalizedAnswer = "";
                String[] splitAnswer = questions.get(i).getAnswer().split("#");
                for (int j = 0; j < splitAnswer.length; j++) {
                    if (j == splitAnswer.length - 1) {
                        normalizedAnswer += splitAnswer[j];
                    } else {
                        normalizedAnswer += splitAnswer[j] + ", ";
                    }
                }
                shareBody += "<p>" + number + ". " + questions.get(i).getQuestion() + "<br>"
                        + normalizedAnswer + "</p>";
            } else {
                shareBody += "<p>" + number + ". " + questions.get(i).getQuestion() + "<br>"
                        + questions.get(i).getAnswer() + "</p>";
            }
        }


        String shareString = Html.fromHtml("<p>SURVEY RESULT <br>" + mJudulSurvey.getText().toString() + "</p>" + shareBody).toString();
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mJudulSurvey.getText().toString());
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);
        startActivity(Intent.createChooser(sendIntent, "Share using"));
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
