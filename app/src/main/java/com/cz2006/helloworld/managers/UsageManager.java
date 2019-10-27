package com.cz2006.helloworld.managers;

import android.content.Context;

import com.cz2006.helloworld.util.TableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Account Manager
 * which get/create/update User's Accounts
 *
 * @author Rosario Gelli Ann
 *
 */
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
    public void addUsage(int year, int month, Character type, float amount, float price)
    {
        // Create table column list
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();

        // Add userID column
        TableColumn userIDColumn = new TableColumn();
        userIDColumn.setColumnName(this.TABLE_USAGE_COLUMN_USER_ID);
        //userIDColumn.setColumnValue(); //USERID TO BE SOLVED
        tableColumnList.add(userIDColumn);

        // Add usageDate column
        TableColumn usageYearColumn = new TableColumn();
        usageYearColumn.setColumnName(this.TABLE_USAGE_COLUMN_YEAR);
        //usageYearColumn.setColumnValue(); //USER DATE TO BE SOLVED
        tableColumnList.add(usageYearColumn);

        //Add usageMonth column
        TableColumn usageMonthColumn = new TableColumn();
        usageMonthColumn.setColumnName(this.TABLE_USAGE_COLUMN_MONTH);
        //usageMonthColumn.setColumnValue(); //USER DATE TO BE SOLVED
        tableColumnList.add(usageMonthColumn);

        // Add usageType column
        TableColumn usageTypeColumn = new TableColumn();
        usageTypeColumn.setColumnName(this.TABLE_USAGE_COLUMN_TYPE);
        //usageTypeColumn.setColumnValue(); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageTypeColumn);

        // Add usageAmount column
        TableColumn usageAmountColumn = new TableColumn();
        usageAmountColumn.setColumnName(this.TABLE_USAGE_COLUMN_AMOUNT);
        //usageAmountColumn.setColumnValue(); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageAmountColumn);

        TableColumn usagePriceColumn = new TableColumn();
        usagePriceColumn.setColumnName(this.TABLE_USAGE_COLUMN_PRICE);
        //usagePriceColumn.setColumnValue(); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usagePriceColumn);

        // Insert added column in to account table.
        this.UMdbManager.insert(this.TABLE_NAME_USAGE, tableColumnList);
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
