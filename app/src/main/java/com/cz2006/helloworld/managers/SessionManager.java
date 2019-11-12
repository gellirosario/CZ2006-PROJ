package com.cz2006.helloworld.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cz2006.helloworld.activities.LogInActivity;

import java.util.HashMap;

/**
 * Represents Session Manager
 * which set user's current session
 *
 * @author Rosario Gelli Ann
 *
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared Pref file name
    private static final String PREF_NAME = "app_preference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User ID (make variable public to access from outside)
    public static final String KEY_USER_ID = "userID";

    // User ID (make variable public to access from outside)
    //public static final String KEY_NAME = "userName";

    // Email address (make variable public to access from outside)
    //public static final String KEY_EMAIL = "userEmail";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int userID){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing userID in pref
        editor.putInt(KEY_USER_ID, userID);

        // Storing email in pref
        //editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LogInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, Integer> getUserDetails(){
        HashMap<String, Integer> user = new HashMap<String, Integer>();

        // user id
        user.put(KEY_USER_ID, pref.getInt(KEY_USER_ID, 0));


        //user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email
        //user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        //Toast.makeText(SessionManager.this, "function call...: " , Toast.LENGTH_LONG).show();

        // After logout redirect user to Log In Activity
        Intent i = new Intent(_context, LogInActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

        //Intent intent = new Intent(getApplicationContext(), Second_activity.class);
        //startActivity(intent);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
