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
public class AccountManager {

    private Context ctx;

    private DatabaseManager AMdbManager;

    private static final String DB_NAME = "HelloWorldDB.db";

    // Database Fields
    public static final String TABLE_NAME_ACCOUNT = "Account";
    public static final String TABLE_ACCOUNT_COLUMN_ID = "userID";
    public static final String TABLE_ACCOUNT_COLUMN_USERNAME = "userName";
    public static final String TABLE_ACCOUNT_COLUMN_PASSWORD = "userPassword";
    public static final String TABLE_ACCOUNT_COLUMN_EMAIL = "userEmail";
    public static final String TABLE_ACCOUNT_COLUMN_POINTS= "totalPoints";

    private int DB_VERSION = 1;

    public AccountManager(Context ctx) {
        this.ctx = ctx;
        this.AMdbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION);
    }

    // Open Database connection
    public void open()
    {
        this.AMdbManager.openDB();
    }

    // Close Database connection
    public void close()
    {
        this.AMdbManager.closeDB();
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

        // Add points column
        TableColumn pointsColumn = new TableColumn();
        pointsColumn.setColumnName(this.TABLE_ACCOUNT_COLUMN_POINTS);
        pointsColumn.setColumnValue("0"); //default value
        tableColumnList.add(pointsColumn);

        // Insert added column in to account table.
        this.AMdbManager.insert(this.TABLE_NAME_ACCOUNT, tableColumnList);
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
        this.AMdbManager.update(this.TABLE_NAME_ACCOUNT, updateColumnList, whereClause);
    }

    // Get specific User account with Email and Password
    public User getAccount(String userEmail, String userPassword){
        User currentUser = new User();
        Cursor cursor = this.AMdbManager.queryTwoSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_EMAIL, userEmail, this.TABLE_ACCOUNT_COLUMN_PASSWORD, userPassword);
        if(cursor!=null){
            int id = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
            String userName = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));
            int points = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));
            currentUser.setUserID(id);
            currentUser.setUserName(userName);
            currentUser.setUserPassword(password);
            currentUser.setUserEmail(email);
            currentUser.setPoints(points);
            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return currentUser;
    }

    // Get specific User account with ID
    public User getAccountWithID(String userID){
        User currentUser = new User();
        Cursor cursor = this.AMdbManager.queryOneSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_ID, userID);
        if(cursor!=null){
            int id = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
            String userName = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));
            int points = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_EMAIL));
            currentUser.setUserID(id);
            currentUser.setUserName(userName);
            currentUser.setUserPassword(password);
            currentUser.setUserEmail(email);
            currentUser.setPoints(points);
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
        Cursor cursor = this.AMdbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
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
        Cursor cursor = this.AMdbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        return cursor;
    }

    // Check if email already exist in the database
    public boolean checkExistingEmail(String userEmail){
        Cursor cursor = this.AMdbManager.queryOneSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_EMAIL,userEmail);

        if(cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    // Authenticate User's Log In Detail
    public boolean authenticate(String userEmail, String userPassword){
        Cursor cursor = this.AMdbManager.queryTwoSearchString(this.TABLE_NAME_ACCOUNT,this.TABLE_ACCOUNT_COLUMN_EMAIL, userEmail, this.TABLE_ACCOUNT_COLUMN_PASSWORD, userPassword);

        if(cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}
