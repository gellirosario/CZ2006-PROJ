package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;

import com.cz2006.helloworld.models.User;
import com.cz2006.helloworld.util.TableColumn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * PointManager includes methods to add points into the point table
 *  as well as querying if existing user has a record
 *
 * @author Lexx Audrey
 *
 */

public class PointManager {

    private Context ctx;

    private DatabaseManager databaseManager;

    private static final String DB_NAME = "HelloWorldDB.db";

    private int DB_VERSION = 1;

    // Database Fields
    public static final String TABLE_NAME_POINT = "Point";
    public static final String TABLE_POINT_COLUMN_ID = "pointID";
    public static final String TABLE_POINT_COLUMN_POINTS = "points";
    public static final String TABLE_POINT_COLUMN_TYPE = "pointType";
    public static final String TABLE_POINT_COLUMN_USER_ID = "userID";

    public PointManager(Context ctx) {
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

    // ADD NEW POINTS
    public int checkPointsExists(String type, int userID)
    {
        String user = Integer.toString(userID);
        int prevPoints = 0;
        Cursor cursor = this.databaseManager.queryTwoSearchString(this.TABLE_NAME_POINT ,this.TABLE_POINT_COLUMN_USER_ID, user, this.TABLE_POINT_COLUMN_TYPE, type);
        if(cursor.moveToFirst()){
            prevPoints = cursor.getInt(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_POINTS));

            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return prevPoints;
    }

    public int addPoints(int points, String type, int userID)
    {
        int prevPoints = checkPointsExists(type, userID);
        if(prevPoints > 0 ) {
            points += prevPoints;
        }

        String user = Integer.toString(userID);

        //Create table column list
        List<TableColumn> pointsList = new ArrayList<TableColumn>();

        // Add points column
        TableColumn pointsCol = new TableColumn();
        pointsCol.setColumnName(this.TABLE_POINT_COLUMN_POINTS);
        pointsCol.setColumnValue(Integer.toString(points));
        pointsList.add(pointsCol);

        // Add type column
        TableColumn typeCol = new TableColumn();
        typeCol.setColumnName(this.TABLE_POINT_COLUMN_TYPE);
        typeCol.setColumnValue(type);
        pointsList.add(typeCol);

        // Add userID column
        TableColumn userCol = new TableColumn();
        userCol.setColumnName(this.TABLE_POINT_COLUMN_USER_ID);
        userCol.setColumnValue(user);
        pointsList.add(userCol);

        Log.d("points", Integer.toString(points));
        Log.d("type", type);
        Log.d("user", user);

        if(prevPoints > 0 ) {
            String whereClause = this.TABLE_POINT_COLUMN_ID + " = " + user + " AND " + this.TABLE_POINT_COLUMN_TYPE + " = '" + type + "'";

            // Insert added column in to point table.
            this.databaseManager.update(this.TABLE_NAME_POINT, pointsList, whereClause);
            Log.d("updateDb", "success");
        }else{
            //Insert added column in to Feedback table.
            this.databaseManager.insert(this.TABLE_NAME_POINT, pointsList);
            Log.d("addDb", "success");
        }
        return points;

    }

    /*public int getUserTotalPoints(int userID)
    {
        String user = Integer.toString(userID);

        int points = 0;
        Cursor cursor = this.databaseManager.queryAllReturnCursor(this.TABLE_NAME_POINT);
        if(cursor!=null) {
            Log.d("cursor", "cursor not null");
            do {
                Log.d("status", cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_USER_ID)));
                if (cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_USER_ID)) == user) {
                    Log.d("status", "user match");
                    if(cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_TYPE)) == "QR Code"){
                        Log.d("status", "type match");
                        Log.d("column points", cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_POINTS)));
                        points += cursor.getColumnIndex(this.TABLE_POINT_COLUMN_POINTS);
                        Log.d("loop points", Integer.toString(points));
                    }
                }
            } while (cursor.moveToNext());

            // Close cursor after query.
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }else{
            Log.d("status", "cursor null");
        }
        Log.d("total points", Integer.toString(points));
        return points;
    }*/

}
