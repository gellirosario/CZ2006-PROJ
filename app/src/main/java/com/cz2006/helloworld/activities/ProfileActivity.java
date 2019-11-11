package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.models.User;

/**
 * Represents Profile Activity showing User's points, name and email
 *
 * @author Rosario Gelli Ann
 *
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView viewLeaderBoardTV, viewRecentActivityTV, nameTV, emailTV, pointTV;
    private Button btnEditProfile;
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
        nameTV = findViewById(R.id.nameTV);
        emailTV = findViewById(R.id.emailTV);
        pointTV = findViewById(R.id.pointTV);
        btnEditProfile = findViewById(R.id.btnEditProfile);

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

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this
                        , EditProfileActivity.class));
                finish();
            }
        });

        accountManager.open(); // Open Database Connection
        getProfileDetails();
    }

    public void getProfileDetails(){

        int userID = sessionManager.getUserDetails().get("userID");

        // Get user info from Database
        User currentUser = accountManager.getAccountWithID(String.valueOf(userID));
        nameTV.setText(currentUser.getUserName());
        emailTV.setText(currentUser.getUserEmail());
        pointTV.setText(String.valueOf(currentUser.getTotalPoints()));
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

        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
