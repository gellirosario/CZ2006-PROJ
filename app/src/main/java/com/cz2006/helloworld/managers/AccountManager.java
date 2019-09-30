package com.cz2006.helloworld.managers;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.cz2006.helloworld.models.User;
import com.cz2006.helloworld.util.DatabaseManager;
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
public class AccountManager {


    private Context ctx;

    private DatabaseManager dbManager;

    private static final String DB_NAME = "HelloWorldDb.db";

    // Database Fields
    private static final String TABLE_NAME_ACCOUNT = "Account";
    public static final String TABLE_ACCOUNT_COLUMN_ID = "userID";
    public static final String TABLE_ACCOUNT_COLUMN_USERNAME = "userName";
    public static final String TABLE_ACCOUNT_COLUMN_PASSWORD = "userPassword";
    public static final String TABLE_ACCOUNT_COLUMN_EMAIL = "userEmail";
    //public static final String TABLE_ACCOUNT_COLUMN_POINTS = "points";

    private int DB_VERSION = 1;

    List<String> tableNameList = null;

    List<String> createTableSqlList = null;

    public AccountManager(Context ctx) {
        this.ctx = ctx;
        this.init();
        this.dbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION, this.tableNameList, this.createTableSqlList);
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

        // Build create account table sql
        StringBuffer sqlBuf = new StringBuffer();

        // Create table sql
        sqlBuf.append("CREATE TABLE ");
        sqlBuf.append(TABLE_NAME_ACCOUNT);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_ID);
        sqlBuf.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_USERNAME);
        sqlBuf.append(" TEXT,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_EMAIL);
        sqlBuf.append(" TEXT ,");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_PASSWORD);
        sqlBuf.append(" TEXT)");



        this.createTableSqlList.add(sqlBuf.toString());
    }

    public void open()
    {
        this.dbManager.openDB();
    }

    public void close()
    {
        this.dbManager.closeDB();;
    }

    // Create User's Account
    public void createAccount(String userName, String userEmail, String userPassword)
    {
        // Create table column list
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();

        // Add user name column
        TableColumn userNameColumn = new TableColumn();
        userNameColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_USERNAME);
        userNameColumn.setColumnValue(userName);
        tableColumnList.add(userNameColumn);

        // Add email column
        TableColumn emailColumn = new TableColumn();
        emailColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_EMAIL);
        emailColumn.setColumnValue(userEmail);
        tableColumnList.add(emailColumn);

        // Add password column
        TableColumn passwordColumn = new TableColumn();
        passwordColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_PASSWORD);
        passwordColumn.setColumnValue(userPassword);
        tableColumnList.add(passwordColumn);

        // Insert added column in to account table.
        this.dbManager.insert(this.TABLE_NAME_ACCOUNT, tableColumnList);
    }

    // Update User's Account
    // TODO: ADD - UPDATE USER'S NAME
    public void updateAccount(int id, String password, String email)
    {
        // Create table column list
        List<TableColumn> updateColumnList = new ArrayList<TableColumn>();

        // Update password can not be empty
        if(!TextUtils.isEmpty(password)) {
            // Add password column
            TableColumn passwordColumn = new TableColumn();
            passwordColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_PASSWORD);
            passwordColumn.setColumnValue(password);
            updateColumnList.add(passwordColumn);
        }

        // Add email column
        TableColumn emailColumn = new TableColumn();
        emailColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_EMAIL);
        emailColumn.setColumnValue(email);
        updateColumnList.add(emailColumn);

        String whereClause = this.TABLE_ACCOUNT_COLUMN_ID + " = " + id;

        // Insert added column in to account table.
        this.dbManager.update(this.TABLE_NAME_ACCOUNT, updateColumnList, whereClause);
    }

    // Get specific User account
    public User getAccount(String userEmail, String userPassword){
        User currentUser = new User();
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        if(cursor!=null){
            int id = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
            String userName = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));
            currentUser.setUserID(id);
            currentUser.setUserName(userName);
            currentUser.setUserPassword(password);
            currentUser.setUserEmail(email);

            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return currentUser;
    }

    // Get all user account list
    public List<User> getAllAccount()
    {
        List<User> ret = new ArrayList<User>();
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        if(cursor!=null)
        {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
                String userName = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_PASSWORD));
                String email = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));

                User user = new User();
                user.setUserID(id);
                user.setUserName(userName);
                user.setUserPassword(password);
                user.setUserEmail(email);
                ret.add(user);

            }while(cursor.moveToNext());

            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return ret;
    }

    // Return SQLite database cursor object
    public Cursor getAllAccountCursor()
    {
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        return cursor;
    }

    // Check if email already exist in the database
    public boolean checkExistingEmail(String userEmail){
        Cursor cursor = this.dbManager.queryOneSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_EMAIL,userEmail);

        if(cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    // Verify Sign Up Form
    public String verifySignUpForm(String name, String email, String password){
        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            return "Please fill in the form.";
        }
        else
        {
            if(checkExistingEmail(email)){
                return "Email already exist.";
            }
        }


        return null;
    }

    public boolean authenticate(String userEmail, String userPassword){
        Cursor cursor = this.dbManager.queryTwoSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_EMAIL, userEmail, this.TABLE_ACCOUNT_COLUMN_PASSWORD, userPassword);

        if(cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}