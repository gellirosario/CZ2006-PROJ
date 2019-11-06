package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView viewLeaderBoardTV, viewRecentActivityTV;
    private AccountManager accountManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set Activity Title
        setTitle("Profile");

        sessionManager = new SessionManager(getApplicationContext());
        accountManager = new AccountManager(getApplicationContext());

        viewLeaderBoardTV = findViewById(R.id.viewLeaderBoardTV);
        viewRecentActivityTV = findViewById(R.id.viewRecentActivityTV);

        viewLeaderBoardTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this
                        , LeaderboardActivity.class));
            }
        });

        viewRecentActivityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this
                        , RecentActivityActivity.class));
            }
        });

        accountManager.open(); // Open Database Connection
    }

    public void getProfileDetails(){

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if (accountManager != null) {
            accountManager.close();
            accountManager = null;
        }
    }
}
