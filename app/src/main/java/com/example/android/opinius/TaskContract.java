package com.example.android.opinius;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.adreybenaya.todolist.db";
    public static int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "Survei";
        public static final String COLUMN_JUDUL_SEMINAR = "Judul";
        public static final String COLUMN_PERTANYAAN = "Pertanyaan";
        public static final String COLUMN_JAWABAN = "Jawaban";
    }
}
