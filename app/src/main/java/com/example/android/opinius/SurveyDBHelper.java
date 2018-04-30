package com.example.android.opinius;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SurveyDBHelper extends SQLiteOpenHelper {
    public SurveyDBHelper(Context context) {
        super(context, SurveyContract.DB_NAME, null, SurveyContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + SurveyContract.SurveyEntry.TABLE + " (" +
                SurveyContract.SurveyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SurveyContract.SurveyEntry.COLUMN_JUDUL_SEMINAR + " TEXT NOT NULL, " +
                SurveyContract.SurveyEntry.COLUMN_PERTANYAAN + "TEXT NOT NULL, " +
                SurveyContract.SurveyEntry.COLUMN_PILIHAN_JAWABAN + "TEXT," +
                SurveyContract.SurveyEntry.COLUMN_JAWABAN + "TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE);
        onCreate(db);
    }
}
