package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cz2006.helloworld.models.Usage;
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

    //Calculate Year Usage Sum
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
        //Toast.makeText(ctx.getApplicationContext(), "NOTHING IN CURSOR! " + String.valueOf(count), Toast.LENGTH_SHORT).show();
        return sum1;
    }

    //Calculate Year Price Sum
    public float calYearPSum(int id, int year, char type)
    {
        float sum1 = 0;
        Cursor cursor = this.databaseManager.queryThreeSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year), this.TABLE_USAGE_COLUMN_TYPE, String.valueOf(type));
        //cursor = this.databaseManager.queryTwoSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year));

        int count = cursor.getCount();
        if (cursor.getCount() != 0) {
            do {
                float amount = cursor.getFloat(cursor.getColumnIndex(TABLE_USAGE_COLUMN_PRICE));
                sum1 = sum1 + amount;

            } while (cursor.moveToNext());
        }
        //Toast.makeText(ctx.getApplicationContext(), "NOTHING IN CURSOR! " + String.valueOf(count), Toast.LENGTH_SHORT).show();
        return sum1;
    }


    public Cursor findUsageRecord(int id, int year, int month, char type)
    {
        Cursor cursor = this.databaseManager.queryFourSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, String.valueOf(id), this.TABLE_USAGE_COLUMN_YEAR, String.valueOf(year), this.TABLE_USAGE_COLUMN_MONTH, String.valueOf(month), this.TABLE_USAGE_COLUMN_TYPE, String.valueOf(type));
        return cursor;
    }

    // Update Usage Data OF THE CURRENT USER
    // TODO: UPDATE USAGE DATA
    public void updateUsage(int id, int year, int month, float amount, float price, char type)
    {
        // Create table column list
        List<TableColumn> updateColumnList = new ArrayList<TableColumn>();

            // Add amount column
            TableColumn amountColumn = new TableColumn();
            amountColumn.setColumnName(this.TABLE_USAGE_COLUMN_AMOUNT);
            amountColumn.setColumnValue(String.valueOf(amount));
            updateColumnList.add(amountColumn);

            // Add price column
            TableColumn priceColumn = new TableColumn();
            priceColumn.setColumnName(this.TABLE_USAGE_COLUMN_PRICE);
            priceColumn.setColumnValue(String.valueOf(price));
            updateColumnList.add(priceColumn);

        String whereClause = this.TABLE_USAGE_COLUMN_USER_ID + " = " + id + " AND " + this.TABLE_USAGE_COLUMN_YEAR + " = " + year + " AND " + this.TABLE_USAGE_COLUMN_MONTH + " = " + month + " AND " + this.TABLE_USAGE_COLUMN_TYPE + " = '" + type +"'";

        //Toast.makeText(this.ctx, whereClause + String.valueOf(amount) + String.valueOf(price), Toast.LENGTH_LONG).show();
        // Insert added column in to account table.
        this.databaseManager.update(this.TABLE_NAME_USAGE, updateColumnList, whereClause);
    }

    //get all usage of a selected type for a specific user for a given year
    public List<Usage> getUserUsage(String userId, String year, String usageType){

        List<Usage> ret = new ArrayList<Usage>();

        Cursor cursor = this.databaseManager.queryThreeSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, userId, this.TABLE_USAGE_COLUMN_YEAR, year, this.TABLE_USAGE_COLUMN_TYPE, usageType);
        //Cursor cursor = this.databaseManager.queryOneSearchString(this.TABLE_NAME_USAGE, this.TABLE_USAGE_COLUMN_USER_ID, userId);

        if (cursor != null && cursor.getCount() > 0){
            do{
                Log.d("DEBUG: ", "Found entry..");

                int uid = cursor.getInt(cursor.getColumnIndex(this.TABLE_USAGE_COLUMN_USER_ID));
                int yr = cursor.getInt(cursor.getColumnIndex(this.TABLE_USAGE_COLUMN_YEAR));
                int mth = cursor.getInt(cursor.getColumnIndex(this.TABLE_USAGE_COLUMN_MONTH));
                int amount = cursor.getInt(cursor.getColumnIndex(this.TABLE_USAGE_COLUMN_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndex(this.TABLE_USAGE_COLUMN_TYPE));

                Log.d("UID", String.valueOf(uid));
                Log.d("YEAR", String.valueOf(yr));
                Log.d("MONTH", String.valueOf(mth));
                Log.d("AMOUNT", String.valueOf(amount));
                Log.d("TYPE", String.valueOf(type));

                Usage usage = new Usage();

                usage.setUserId(uid);
                usage.setUsageYear(yr);
                usage.setUsageMonth(mth);
                usage.setUsageAmount(amount);
                usage.setUsageType(type);

                ret.add(usage);
            }
            while (cursor.moveToNext());

            if (!cursor.isClosed()){
                cursor.close();
            }
        }
        return ret;
    }


}
