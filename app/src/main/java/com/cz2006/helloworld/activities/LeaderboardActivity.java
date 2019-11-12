package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.adapters.LeaderboardAdapter;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.util.SQLiteDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents Leaderboard
 * where User can view top scorers
 *
 * @author Edmund
 *
 */
public class LeaderboardActivity extends AppCompatActivity  {


    private SQLiteDatabase LBdatabase;
    private TextView rankTV, nameTV,ptsTV;
    private LeaderboardAdapter mAdapter;



    private static final String DB_NAME = "HelloWorldDB.db";
    private int DB_VERSION = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Set Activity Title
        setTitle("Leaderboard");



        SQLiteDatabaseHelper LBdbHelper = new SQLiteDatabaseHelper(this, DB_NAME, null, DB_VERSION);
        LBdatabase = LBdbHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.LeaderboardRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LeaderboardAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);

        rankTV = findViewById(R.id.rankTV);
        nameTV = findViewById(R.id.nameTV);
        ptsTV = findViewById(R.id.ptsTV);




    }

    public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        getSupportActionBar().setIcon(R.drawable.ic_back_24dp);
    }



    private Cursor getAllItems(){
        return LBdatabase.query(

                AccountManager.TABLE_NAME_ACCOUNT,
                null,
                null,
                null,
                null,
                null,
                AccountManager.TABLE_ACCOUNT_COLUMN_POINTS + " DESC"

        );
    }



}
