package com.cz2006.helloworld.managers;

import android.content.Context;

public class FeedbackManager {
    private Context ctx;

    private DatabaseManager databaseManager;

    private static final String DB_NAME = "HelloWorldDB.db";

    private int DB_VERSION = 1;

    // Database Fields
    public static final String TABLE_NAME_FEEDBACK = "Feedback";
    public static final String TABLE_FEEDBACK_COLUMN_ID = "feedbackID";
    public static final String TABLE_FEEDBACK_COLUMN_TYPE = "feedbackType";
    public static final String TABLE_FEEDBACK_COLUMN_DESC = "feedbackDescription";
    public static final String TABLE_FEEDBACK_COLUMN_DATE = "feedbackDate";
    public static final String TABLE_FEEDBACK_COLUMN_RATING = "feedbackRating";
    public static final String TABLE_FEEDBACK_COLUMN_USER_ID = "userID";

    public FeedbackManager(Context ctx) {
        this.ctx = ctx;
        this.databaseManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION);
    }
}
