package com.cz2006.helloworld.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.cz2006.helloworld.util.SQLiteDatabaseHelper;
import com.cz2006.helloworld.util.TableColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cz2006.helloworld.managers.AccountManager.TABLE_ACCOUNT_COLUMN_EMAIL;
import static com.cz2006.helloworld.managers.AccountManager.TABLE_ACCOUNT_COLUMN_ID;
import static com.cz2006.helloworld.managers.AccountManager.TABLE_ACCOUNT_COLUMN_PASSWORD;
import static com.cz2006.helloworld.managers.AccountManager.TABLE_ACCOUNT_COLUMN_POINTS;
import static com.cz2006.helloworld.managers.AccountManager.TABLE_ACCOUNT_COLUMN_USERNAME;
import static com.cz2006.helloworld.managers.AccountManager.TABLE_NAME_ACCOUNT;
import static com.cz2006.helloworld.managers.PointManager.TABLE_NAME_POINT;
import static com.cz2006.helloworld.managers.PointManager.TABLE_POINT_COLUMN_ID;
import static com.cz2006.helloworld.managers.PointManager.TABLE_POINT_COLUMN_POINTS;
import static com.cz2006.helloworld.managers.PointManager.TABLE_POINT_COLUMN_TYPE;
import static com.cz2006.helloworld.managers.PointManager.TABLE_POINT_COLUMN_USER_ID;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_NAME_USAGE;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_AMOUNT;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_ID;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_MONTH;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_PRICE;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_TYPE;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_YEAR;
import static com.cz2006.helloworld.managers.UsageManager.TABLE_USAGE_COLUMN_USER_ID;

/**
 *  DatabaseManager includes methods to implement basic SQLite database and table operations,
 *  such as open database, close database, insert data, update data, delete data and query data.
 *
 * @author Rosario Gelli Ann
 *
 */
public class DatabaseManager {

    // This is the activity context.
    private Context ctx = null;

    // Database helper class to manipulate sqlite database.
    private SQLiteDatabaseHelper dbHelper = null;

    // Represent connection with sqlite database.
    private SQLiteDatabase database = null;

    // Database name.
    private String dbName = "";

    // Database version.
    private int dbVersion = 0;

    // All tables that this database contains.
    private List<String> tableNameList = null;

    // All table create sql that this db need.
    private List<String> createTableSqlList = null;

    // Create database manager instance.
    public DatabaseManager(Context ctx, String dbName, int dbVersion) {
        this.ctx = ctx;
        this.dbName = dbName;
        this.dbVersion = dbVersion;
        this.init();
    }

    private void init()
    {
        if(this.tableNameList==null)
        {
            this.tableNameList = new ArrayList<String>();
        }

        if(this.createTableSqlList==null)
        {
            this.createTableSqlList = new ArrayList<String>();
        }

        this.tableNameList.add(TABLE_NAME_ACCOUNT);

        // Build points table sql
        StringBuffer sqlBuf = new StringBuffer();

        // Create account table sql
        sqlBuf.append("CREATE TABLE ");
        sqlBuf.append(TABLE_NAME_ACCOUNT);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_ID);
        sqlBuf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_USERNAME);
        sqlBuf.append(" TEXT NOT NULL,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_EMAIL);
        sqlBuf.append(" TEXT NOT NULL,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_PASSWORD);
        sqlBuf.append(" TEXT NOT NULL,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_POINTS);
        sqlBuf.append(" INTEGER NOT NULL)");

        this.createTableSqlList.add(sqlBuf.toString());
        sqlBuf = new StringBuffer();

        // Create point table sql
        sqlBuf.append("CREATE TABLE ");
        sqlBuf.append(TABLE_NAME_POINT);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_POINT_COLUMN_ID);
        sqlBuf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuf.append(TABLE_POINT_COLUMN_POINTS);
        sqlBuf.append(" INTEGER NOT NULL,");
        sqlBuf.append(TABLE_POINT_COLUMN_TYPE);
        sqlBuf.append(" TEXT NOT NULL,"); // EITHER GOTTEN FROM SCAN OR TRACK
        sqlBuf.append(TABLE_POINT_COLUMN_USER_ID);
        sqlBuf.append(" INTEGER NOT NULL, FOREIGN KEY (");
        sqlBuf.append(TABLE_POINT_COLUMN_USER_ID);
        sqlBuf.append(") REFERENCES ");
        sqlBuf.append(TABLE_NAME_ACCOUNT);
        sqlBuf.append("(");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_ID);
        sqlBuf.append("))");

        this.createTableSqlList.add(sqlBuf.toString());
        sqlBuf = new StringBuffer();

        // Create usage table sql
        sqlBuf.append("CREATE TABLE ");
        sqlBuf.append(TABLE_NAME_USAGE);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_USAGE_COLUMN_ID);
        sqlBuf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuf.append(TABLE_USAGE_COLUMN_YEAR);
        sqlBuf.append(" INTEGER NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_MONTH);
        sqlBuf.append(" INTEGER NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_TYPE);
        sqlBuf.append(" VARCHAR(1) NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_AMOUNT);
        sqlBuf.append(" REAL NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_PRICE);
        sqlBuf.append(" REAL NOT NULL,");
        sqlBuf.append(TABLE_USAGE_COLUMN_USER_ID);
        sqlBuf.append(" INTEGER NOT NULL, FOREIGN KEY (");
        sqlBuf.append(TABLE_USAGE_COLUMN_USER_ID);
        sqlBuf.append(") REFERENCES ");
        sqlBuf.append(TABLE_NAME_ACCOUNT);
        sqlBuf.append("(");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_ID);
        sqlBuf.append("))");

        this.createTableSqlList.add(sqlBuf.toString());

    }

    // Open database connection.
    public DatabaseManager openDB()
    {
        // Create database helper instance.
        dbHelper = new SQLiteDatabaseHelper(ctx, this.dbName, null, this.dbVersion);
        dbHelper.setTableNameList(this.tableNameList);
        dbHelper.setCreateTableSqlList(this.createTableSqlList);

        // Get sqlite database connection. If not exist then create one else return a database object.
        this.database = dbHelper.getWritableDatabase();
        return this;
    }


    // close database connection.
    public void closeDB()
    {
        this.database.close();
        this.dbHelper.close();
        this.database = null;
        this.dbHelper = null;
    }

    /* Insert data into table.
     *  tableName : The table name.
     *  columnList : A list of table column name value pair.
     * */
    public void insert(String InsertTableName, List<TableColumn> insertcolumnList)
    {
        if(!TextUtils.isEmpty(InsertTableName) && insertcolumnList!=null)
        {
            int size = insertcolumnList.size();

            if(size > 0)
            {
                // Create a content values object.
                ContentValues InsertContentValues = new ContentValues();

                // Loop in the table column list
                for(int i=0;i<size;i++)
                {
                    //SEPERATE THE LIST INTO EACH COLUMN
                    TableColumn InsertTableColumn = insertcolumnList.get(i);

                    // Put column name and value in content values.
                    if(!TextUtils.isEmpty(InsertTableColumn.getColumnName())) {
                        InsertContentValues.put(InsertTableColumn.getColumnName(), InsertTableColumn.getColumnValue());
                    }
                }

                // Insert data.
                this.database.insert(InsertTableName, null, InsertContentValues);
            }
        }
    }

    /*
     *  Update data in table.
     *  tableName : The table name.
     *  columnList : Need update column name and value.
     *  whereClause : The update rows meet condition.
     * */
    public int update(String tableName,  List<TableColumn> columnList, String whereClause)
    {
        int ret = 0;
        if(!TextUtils.isEmpty(tableName) && columnList!=null)
        {
            int size = columnList.size();

            if(size > 0)
            {
                // Create a content values object.
                ContentValues UpdateContentValues = new ContentValues();

                // Loop in the table column list
                for(int i=0;i<size;i++)
                {
                    TableColumn tableColumn = columnList.get(i);

                    // Put column name and value in content values.
                    if(!TextUtils.isEmpty(tableColumn.getColumnName())) {
                        UpdateContentValues.put(tableColumn.getColumnName(), tableColumn.getColumnValue());
                    }
                }

                // Update data.Return update row count.
                ret = this.database.update(tableName, UpdateContentValues, whereClause, null);
            }
        }
        return ret;
    }

    /* Delete rows in table.
     *  tableName : Delete data table name.
     *  whereClause : Deleted rows meet condition. */
    public void delete(String tableName, String whereClause)
    {
        if(!TextUtils.isEmpty(tableName)) {
            this.database.delete(tableName, whereClause, null);
        }
    }

    /*
     *  Query all rows in SQLITE database table.
     *  tableName : The table name.
     *  Return a list a Map object. Each map object represent a row of data in table.
     * */
    public List<Map<String, String>> queryAllReturnListMap(String tableName)
    {
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();

        // Query all rows in table.
        Cursor cursor = this.database.query(tableName, null, null, null, null, null, null);
        if(cursor!=null)
        {
            // Get all column name in an array.
            String columnNamesArr[] = cursor.getColumnNames();

            // Move to first cursor.
            cursor.moveToFirst();
            do {
                // Create a map object represent a table row data.
                Map<String, String> rowMap = new HashMap<String, String>();

                // Get column count.
                int columnCount = columnNamesArr.length;
                for(int i=0;i<columnCount;i++)
                {
                    // Get each column name.
                    String columnName = columnNamesArr[i];

                    // This is the column value.
                    String columnValue = "";

                    // Get column index value.
                    int columnIndex = cursor.getColumnIndex(columnName);

                    // Get current column data type.
                    int columnType = cursor.getType(columnIndex);

                    if(Cursor.FIELD_TYPE_STRING == columnType)
                    {
                        columnValue = cursor.getString(columnIndex);
                    }else if(Cursor.FIELD_TYPE_INTEGER == columnType)
                    {
                        columnValue = String.valueOf(cursor.getInt(columnIndex));
                    }else if(Cursor.FIELD_TYPE_FLOAT == columnType)
                    {
                        columnValue = String.valueOf(cursor.getFloat(columnIndex));
                    }else if(Cursor.FIELD_TYPE_BLOB == columnType)
                    {
                        columnValue = String.valueOf(cursor.getBlob(columnIndex));
                    }else if(Cursor.FIELD_TYPE_NULL == columnType)
                    {
                        columnValue = "null";
                    }

                    rowMap.put(columnName, columnValue);

                    ret.add(rowMap);
                }
            }while(cursor.moveToNext());

            cursor.close();
        }
        return ret;
    }

    /*
     *  Query all rows in SQLITE database table.
     *  tableName : The table name.
     *  Return a Cursor object.
     * */
    public Cursor queryAllReturnCursor(String tableName)
    {
        // Query all rows in table.
        Cursor cursor = this.database.query(tableName, null, null, null, null, null, null);
        if(cursor!=null)
        {
            // Move to first cursor.
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
     *  Query all rows in SQLITE database table.
     *  tableName : The table name.
     *  columnName : The column name
     *  inputData : The input data
     *  Return a Cursor object.
     * */
    public Cursor queryOneSearchString(String tableName, String columnName, String inputData)
    {
        // Query all rows in table.
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName + "=?",new String[] {String.valueOf(inputData)});
        if(cursor!=null)
        {
            // Move to first cursor.
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
     *  Query all rows in SQLITE database table.
     *  tableName : The table name.
     *  columnName : The column name
     *  inputData : The input data
     *  Return a Cursor object.
     * */
    public Cursor queryTwoSearchString(String tableName, String columnName1, String inputData1, String columnName2, String inputData2)
    {
        // Query all rows in table.
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName1 + "=? AND " + columnName2 + " =? ",new String[] {String.valueOf(inputData1),String.valueOf(inputData2)});
        if(cursor!=null)
        {
            // Move to first cursor.
            cursor.moveToFirst();
        }
        return cursor;
    }
}
