package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.cz2006.helloworld.util.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class UsageManager {

    private Context ctx;

    private DatabaseManager databaseManager;

    private static final String DB_NAME = "HelloWorldDB.db";

    // Database Fields
    public static final String TABLE_NAME_USAGE = "Usage";
    public static final String TABLE_USAGE_COLUMN_ID = "usageID";
    public static final String TABLE_USAGE_COLUMN_USER_ID = "userID";
    public static final String TABLE_USAGE_COLUMN_YEAR = "usageYear";
    public static final String TABLE_USAGE_COLUMN_MONTH = "usageMonth";
    public static final String TABLE_USAGE_COLUMN_TYPE = "usageType";
    public static final String TABLE_USAGE_COLUMN_AMOUNT = "usageAmount";
    public static final String TABLE_USAGE_COLUMN_PRICE = "usagePrice";

    private int DB_VERSION = 1;

    public UsageManager(Context ctx) {
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
    public void addUsage(int year, int month, char type, float amount, float price)
    {
        //Create table column list
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();

        SessionManager sessionManager = new SessionManager(ctx.getApplicationContext());

        int userID = sessionManager.getUserDetails().get("userID");

        // Add userID column
        TableColumn userIDColumn = new TableColumn();
        userIDColumn.setColumnName(this.TABLE_USAGE_COLUMN_USER_ID);
        userIDColumn.setColumnValue(String.valueOf(userID)); //USERID TO BE SOLVED
        tableColumnList.add(userIDColumn);

        // Add Year column
        TableColumn usageYearColumn = new TableColumn();
        usageYearColumn.setColumnName(this.TABLE_USAGE_COLUMN_YEAR);
        usageYearColumn.setColumnValue(String.valueOf(year)); //USER DATE TO BE SOLVED
        tableColumnList.add(usageYearColumn);

        //Add Month column
        TableColumn usageMonthColumn = new TableColumn();
        usageMonthColumn.setColumnName(this.TABLE_USAGE_COLUMN_MONTH);
        usageMonthColumn.setColumnValue(String.valueOf(month)); //USER DATE TO BE SOLVED
        tableColumnList.add(usageMonthColumn);

        // Add usageType column
        TableColumn usageTypeColumn = new TableColumn();
        usageTypeColumn.setColumnName(this.TABLE_USAGE_COLUMN_TYPE);
        usageTypeColumn.setColumnValue(String.valueOf(type)); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageTypeColumn);

        // Add usageAmount column
        TableColumn usageAmountColumn = new TableColumn();
        usageAmountColumn.setColumnName(this.TABLE_USAGE_COLUMN_AMOUNT);
        usageAmountColumn.setColumnValue(String.valueOf(amount)); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageAmountColumn);

        TableColumn usagePriceColumn = new TableColumn();
        usagePriceColumn.setColumnName(this.TABLE_USAGE_COLUMN_PRICE);
        usagePriceColumn.setColumnValue(String.valueOf(price)); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usagePriceColumn);

        //Insert added column in to account table.
        this.databaseManager.insert(this.TABLE_NAME_USAGE, tableColumnList);
    }

    public float calYearSum(int id, int year, char type)
    {
        float sum1 = 0;
        Cursor cursor = this.databaseManager.queryThreeSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year), this.TABLE_USAGE_COLUMN_TYPE, String.valueOf(type));
        //cursor = this.databaseManager.queryTwoSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year));

        int count = cursor.getCount();
        if (cursor.getCount() != 0) {
            do {
                float amount = cursor.getFloat(cursor.getColumnIndex(TABLE_USAGE_COLUMN_AMOUNT));
                sum1 = sum1 + amount;

            } while (cursor.moveToNext());
        }
        Toast.makeText(ctx.getApplicationContext(), "NOTHING IN CURSOR! " + String.valueOf(count), Toast.LENGTH_SHORT).show();
        return sum1;
    }

    // Update Usage Data OF THE CURRENT USER
    // TODO: UPDATE USAGE DATA
    public void updateUsage(int id, int year, int month, char usageType)
    {
        // Create table column list
        List<TableColumn> updateColumnList = new ArrayList<TableColumn>();

        String whereClause = this.TABLE_USAGE_COLUMN_USER_ID + " = " + id;

        // Insert added column in to account table.
        this.databaseManager.update(this.TABLE_NAME_USAGE, updateColumnList, whereClause);
    }


}
