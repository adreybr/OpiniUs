package com.example.android.opinius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TambahPertanyaan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pertanyaan);

        Intent intent = getIntent();
        String message =
                intent.getStringExtra(TambahSurveyActivity.JUDUL_SURVEY);
        TextView textView = (TextView) findViewById(R.id.judul_survey);
        textView.setText(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pertanyaan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambah_pertanyaan:
                Intent intent = new Intent(TambahPertanyaan.this, TipeJawabActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
