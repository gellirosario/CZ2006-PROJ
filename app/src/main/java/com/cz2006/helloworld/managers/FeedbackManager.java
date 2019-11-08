package com.cz2006.helloworld.managers;

import android.content.Context;
import android.util.Log;

import com.cz2006.helloworld.util.TableColumn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *  FeedbackManager includes add feedback of user to
 *  the database feedback table
 *
 * @author Lexx Audrey
 *
 */

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

    // Open Database connection
    public void open()
    {
        this.databaseManager.openDB();
    }

    // Close Database connection
    public void close()
    {
        this.databaseManager.closeDB();
    }

    // ADD NEW USAGE
    public void sendFeedback(String type, String desc, String rating)
    {
        //Create table column list
        List<TableColumn> feedbackList = new ArrayList<TableColumn>();

        SessionManager sessionManager = new SessionManager(ctx.getApplicationContext());

        int userID = sessionManager.getUserDetails().get("userID");
        String user = Integer.toString(userID);

        // Add type column
        TableColumn typeCol = new TableColumn();
        typeCol.setColumnName(this.TABLE_FEEDBACK_COLUMN_TYPE);
        typeCol.setColumnValue(type);
        feedbackList.add(typeCol);

        // Add desc column
        TableColumn descCol = new TableColumn();
        descCol.setColumnName(this.TABLE_FEEDBACK_COLUMN_DESC);
        descCol.setColumnValue(desc);
        feedbackList.add(descCol);

        // Add date column
        TableColumn dateCol = new TableColumn();
        dateCol.setColumnName(this.TABLE_FEEDBACK_COLUMN_DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        dateCol.setColumnValue(currentDate);
        feedbackList.add(dateCol);

        // Add rating column
        TableColumn ratingCol = new TableColumn();
        ratingCol.setColumnName(this.TABLE_FEEDBACK_COLUMN_RATING);
        ratingCol.setColumnValue(rating);
        feedbackList.add(ratingCol);

        // Add userID column
        TableColumn userCol = new TableColumn();
        userCol.setColumnName(this.TABLE_FEEDBACK_COLUMN_USER_ID);
        userCol.setColumnValue(user);
        feedbackList.add(userCol);

        Log.d("type", type);
        Log.d("desc", desc);
        Log.d("date", currentDate);
        Log.d("rating", rating);
        Log.d("user", user);


        //Insert added column in to Feedback table.
        this.databaseManager.insert(this.TABLE_NAME_FEEDBACK, feedbackList);
    }
}
