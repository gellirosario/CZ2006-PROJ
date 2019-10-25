package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.cz2006.helloworld.models.User;
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
    private static final String TABLE_NAME_USAGE = "Usage";
    public static final String TABLE_USAGE_COLUMN_USAGEID = "usageID";
    public static final String TABLE_USAGE_COLUMN_USERID = "userID";
    public static final String TABLE_USAGE_COLUMN_USAGEYEAR = "usageYear";
    public static final String TABLE_USAGE_COLUMN_USAGEMONTH = "usageMonth";
    public static final String TABLE_USAGE_COLUMN_USAGETYPE = "usageType";
    public static final String TABLE_USAGE_COLUMN_USAGEAMOUNT= "usageAmount";
    public static final String TABLE_USAGE_COLUMN_USAGEPRICE= "usagePrice";

    private int DB_VERSION = 1;

    List<String> UMtableNameList = null;

    List<String> UMcreateTableSqlList = null;

    public UsageManager(Context ctx) {
        this.ctx = ctx;
        this.init();
        this.UMdbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION, this.UMtableNameList, this.UMcreateTableSqlList);
    }

    private void init()
    {
        if(this.UMtableNameList==null)
        {
            this.UMtableNameList = new ArrayList<String>();
        }

        if(this.UMcreateTableSqlList==null)
        {
            this.UMcreateTableSqlList = new ArrayList<String>();
        }

        this.UMtableNameList.add(TABLE_NAME_USAGE);

        // Build points table sql
        StringBuffer sqlBuf = new StringBuffer();

        // Create account table sql
        sqlBuf.append("CREATE TABLE ");
        sqlBuf.append(TABLE_NAME_USAGE);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGEID);
        sqlBuf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USERID);
        sqlBuf.append(" INTEGER NOT NULL PRIMARY KEY,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGEYEAR);
        sqlBuf.append(" INTEGER NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGEMONTH);
        sqlBuf.append(" INTEGER NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGETYPE);
        sqlBuf.append(" VARCHAR(1) NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGEAMOUNT);
        sqlBuf.append(" REAL NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USAGEPRICE);
        sqlBuf.append(" REAL NOT NULL)");

        this.UMcreateTableSqlList.add(sqlBuf.toString());

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
        userIDColumn.setColumnName(this.TABLE_USAGE_COLUMN_USERID);
        //userIDColumn.setColumnValue(); //USERID TO BE SOLVED
        tableColumnList.add(userIDColumn);

        // Add usageDate column
        TableColumn usageYearColumn = new TableColumn();
        usageYearColumn.setColumnName(this.TABLE_USAGE_COLUMN_USAGEYEAR);
        //usageYearColumn.setColumnValue(); //USER DATE TO BE SOLVED
        tableColumnList.add(usageYearColumn);

        //Add usageMonth column
        TableColumn usageMonthColumn = new TableColumn();
        usageMonthColumn.setColumnName(this.TABLE_USAGE_COLUMN_USAGEMONTH);
        //usageMonthColumn.setColumnValue(); //USER DATE TO BE SOLVED
        tableColumnList.add(usageMonthColumn);

        // Add usageType column
        TableColumn usageTypeColumn = new TableColumn();
        usageTypeColumn.setColumnName(this.TABLE_USAGE_COLUMN_USAGETYPE);
        //usageTypeColumn.setColumnValue(); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageTypeColumn);

        // Add usageAmount column
        TableColumn usageAmountColumn = new TableColumn();
        usageAmountColumn.setColumnName(this.TABLE_USAGE_COLUMN_USAGEAMOUNT);
        //usageAmountColumn.setColumnValue(); //TYPE TO BE REFERENCED FROM FUNCTION
        tableColumnList.add(usageAmountColumn);

        TableColumn usagePriceColumn = new TableColumn();
        usagePriceColumn.setColumnName(this.TABLE_USAGE_COLUMN_USAGEPRICE);
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

        String whereClause = this.TABLE_USAGE_COLUMN_USERID + " = " + id;

        // Insert added column in to account table.
        this.UMdbManager.update(this.TABLE_NAME_USAGE, updateColumnList, whereClause);
    }


}
