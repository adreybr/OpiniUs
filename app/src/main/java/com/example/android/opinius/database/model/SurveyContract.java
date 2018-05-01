package com.example.android.opinius.database.model;

import android.provider.BaseColumns;

public class SurveyContract {
    public static final String DB_NAME = "survey_db";
    public static int DB_VERSION = 1;

    public class SurveyEntry implements BaseColumns {
        public static final String TABLE = "survey";
        public static final String COLUMN_SURVEY_TITLE = "title";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER_LIST = "answer_list";
        public static final String COLUMN_ANSWER = "answer";
    }
}
