package com.example.android.opinius;

import android.provider.BaseColumns;

public class SurveyContract {
    public static final String DB_NAME = "com.example.android.opinius.db";
    public static int DB_VERSION = 1;

    public class SurveyEntry implements BaseColumns {
        public static final String TABLE = "Survey";
        public static final String COLUMN_JUDUL_SEMINAR = "judul";
        public static final String COLUMN_PERTANYAAN = "pertanyaan";
        public static final String COLUMN_PILIHAN_JAWABAN = "pilihan_jawaban";
        public static final String COLUMN_JAWABAN = "jawaban";
    }
}
