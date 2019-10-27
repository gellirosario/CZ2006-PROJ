package com.cz2006.helloworld.managers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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

}
