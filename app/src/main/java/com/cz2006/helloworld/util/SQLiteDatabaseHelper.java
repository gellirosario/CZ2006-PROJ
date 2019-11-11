package com.cz2006.helloworld.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLiteDatabaseHelper is subclass of android.database.sqlite.SQLiteOpenHelper.
 * It overwrite it’s onCreate() and onUpgrade() methods.
 * The main purpose is to create and upgrade SQLite database and tables.
 *
 * @author Rosario Gelli Ann
 *
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    // Contains all table name string list
    private List<String> tableNameList = null;

    // Contains all create table sql command string list
    private List<String> createTableSqlList = null;

    // This is the log tag in android monitor console
    public static final String LOG_TAG_SQLITE_DB = "LOG_TAG_SQLITE_DB";

    // This is the android activity context
    private Context ctx = null;

    /* Constructor with all input parameter*/
    public SQLiteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ctx = context;
    }

    /* Generally run all create table sql in this method. */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if(createTableSqlList!=null)
        {
            int size = createTableSqlList.size();
            for(int i = 0;i<size;i++)
            {
                // Loop all the create table sql command string in the list.
                // each sql will create a table in sqlite database.
                String createTableSql = createTableSqlList.get(i);
                sqLiteDatabase.execSQL(createTableSql);

                //Toast.makeText(ctx, "Run sql successfully, " + createTableSql, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* When the new db version is bigger than current exist db version, this method will be invoked.
     * It always drop all tables and then call onCreate() method to create all table again.*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(tableNameList!=null)
        {
            // Loop and drop all exist SQLite table
            int size = tableNameList.size();
            for(int i = 0;i<size;i++)
            {
                String tableName = tableNameList.get(i);
                if(!TextUtils.isEmpty(tableName)) {
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
                }
            }
        }

        // After drop all exist tables, create all tables again.
        onCreate(sqLiteDatabase);
    }

    public List<String> getTableNameList() {
        if(tableNameList==null)
        {
            tableNameList = new ArrayList<String>();
        }
        return tableNameList;
    }

    public void setTableNameList(List<String> tableNameList) {
        this.tableNameList = tableNameList;
    }

    public List<String> getCreateTableSqlList() {
        if(createTableSqlList==null)
        {
            createTableSqlList = new ArrayList<String>();
        }
        return createTableSqlList;
    }

    public void setCreateTableSqlList(List<String> createTableSqlList) {
        this.createTableSqlList = createTableSqlList;
    }
}

