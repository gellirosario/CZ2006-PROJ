package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;

import com.cz2006.helloworld.models.Points;
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
    public static final String TABLE_POINT_COLUMN_DATE = "pointDate";
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
        if(cursor.moveToFirst()) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                prevPoints += cursor.getInt(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_POINTS));
            }
            // Close cursor after query.
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }


        return prevPoints;
    }

    public int addPoints(int points, String type, int userID) {
        int prevPoints = checkPointsExists(type, userID);

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

        // Add date column
        TableColumn dateCol = new TableColumn();
        dateCol.setColumnName(this.TABLE_POINT_COLUMN_DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        dateCol.setColumnValue(currentDate);
        pointsList.add(dateCol);

        // Add userID column
        TableColumn userCol = new TableColumn();
        userCol.setColumnName(this.TABLE_POINT_COLUMN_USER_ID);
        userCol.setColumnValue(user);
        pointsList.add(userCol);

        Log.d("points", Integer.toString(points));
        Log.d("type", type);
        Log.d("user", user);

        this.databaseManager.insert(this.TABLE_NAME_POINT, pointsList);
        Log.d("addDb", "success");

        return prevPoints;

    }

    // Get all points list according to User ID
    public ArrayList<Points> getAllPointsWithID(String id)
    {
        ArrayList<Points> ret = new ArrayList<Points>();

        Cursor cursor = this.databaseManager.queryOneSearchString(this.TABLE_NAME_POINT, this.TABLE_POINT_COLUMN_USER_ID, id);
        if(cursor!=null && cursor.moveToFirst())
        {
            do {
                int pointID = cursor.getInt(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_ID));
                int points = cursor.getInt(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_POINTS));
                String pointType = cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_TYPE));
                String pointDate = cursor.getString(cursor.getColumnIndex(this.TABLE_POINT_COLUMN_DATE));

                Points userPoints = new Points(pointID, points, pointType, pointDate);
                ret.add(userPoints);

            }while(cursor.moveToNext());

            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return ret;
    }
}
