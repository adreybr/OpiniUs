package com.example.android.opinius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent = new Intent(MainActivity.this, TambahSurveyActivity.class);
                startActivityForResult(intent, 101);
                return true;
            case R.id.lihat_survey:
                Intent intent1 = new Intent(MainActivity.this, SurveyTerjawabActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Survey berhasil disimpan",Toast.LENGTH_LONG).show();
            }
        }
    }
}
