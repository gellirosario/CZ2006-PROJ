package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.se.omapi.Session;
import android.widget.Toast;

import com.cz2006.helloworld.activities.AddUsageActivity;
import com.cz2006.helloworld.models.User;
import com.cz2006.helloworld.util.TableColumn;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class UsageManager {

    private Context ctx;

    private DatabaseManager UMdbManager;

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
        this.UMdbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION);
    }

    // Open Database connection
    public void open()
    {
        this.UMdbManager.openDB();
    }

    // Close Database connection
    public void close()
    {
        this.UMdbManager.closeDB();
    }

    // ADD NEW USAGE
    public void addUsage(int year, int month, char type, float amount, float price)
    {
        //Create table column list
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();
        SessionManager UMsessionManager = new SessionManager(ctx.getApplicationContext());

        int userid = UMsessionManager.getUserDetails().get("userID");


        // Add userID column
        TableColumn userIDColumn = new TableColumn();
        userIDColumn.setColumnName(this.TABLE_USAGE_COLUMN_USER_ID);
        userIDColumn.setColumnValue(String.valueOf(userid)); //USERID TO BE SOLVED
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

        Toast.makeText(ctx.getApplicationContext(), "CREATED ALL COLUMNS!", Toast.LENGTH_SHORT).show();
        //Insert added column in to account table.
        this.UMdbManager.insert(this.TABLE_NAME_USAGE, tableColumnList);
    }

    public float calyearsum(int id, int year, char type)
    {
        float sum1 = 0;
        Cursor cursor = this.UMdbManager.queryTwoSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year));
        /*while (cursor!=null) {
            float amount = cursor.getFloat(cursor.getColumnIndex(TABLE_USAGE_COLUMN_AMOUNT));
            sum1 = sum1 + amount;
            cursor.moveToNext();
        }*/
        int count = cursor.getCount();
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
        this.UMdbManager.update(this.TABLE_NAME_USAGE, updateColumnList, whereClause);
    }


}
