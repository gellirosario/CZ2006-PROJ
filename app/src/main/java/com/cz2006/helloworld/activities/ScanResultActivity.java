package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.fragments.ScanFragment;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.PointManager;
import com.cz2006.helloworld.managers.SessionManager;

/**
 * Represents Scan Results Activity
 * where points is added for the user and
 * shows either successful or fail page
 *
 * @author Lexx Audrey
 *
 */

public class ScanResultActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private PointManager pointManager;
    private AccountManager accountManager;

    private int userID, points, curPoints, prevPoints;
    public String type = "QR Code";
    public String results = ScanFragment.results;


    private ImageView statusImg;
    private TextView statusTxt;
    private TextView msgTxt;
    private TextView prevTxt;
    private TextView addedTxt;
    private TextView currentTxt;
    private Button btnPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        sessionManager = new SessionManager(getApplicationContext());
        pointManager = new PointManager(getApplicationContext());
        accountManager = new AccountManager(getApplicationContext());

        userID = sessionManager.getUserDetails().get("userID");

        pointManager.open();
        accountManager.open();

        Log.d("results", results);

        if(results.matches("HelloWorldSUCCESS10") || results.matches("HelloWorldSUCCESS20"))
        {
            if(results.matches("HelloWorldSUCCESS10")) {
                points = 10;
            }else if(results.matches("HelloWorldSUCCESS20")) {
                points = 20;
            }
            Log.d("points", Integer.toString(points));
            addPoints();

        }else{
            setTitle("Failed");
        }
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

    public void setSuccessPage() {
        statusImg = (ImageView) findViewById(R.id.statusImg);
        statusTxt = findViewById(R.id.statusTxt);
        msgTxt = findViewById(R.id.msgTxt);
        prevTxt = findViewById(R.id.prevTxt);
        addedTxt = findViewById(R.id.addedTxt);
        currentTxt = findViewById(R.id.currentTxt);
        btnPage = findViewById(R.id.btnPage);

        statusImg.setBackgroundResource(R.drawable.ic_checkmark);
        statusTxt.setText("Congratulations!");
        msgTxt.setText("You have received " + points + " Green Points!");

        prevTxt.setText("Previous Balance : " + prevPoints + " points");
        prevTxt.setVisibility(View.VISIBLE);

        addedTxt.setVisibility(View.VISIBLE);
        addedTxt.setText("Added : " + points + " points");

        currentTxt.setText("Current Balance : " + curPoints + " points");
        currentTxt.setVisibility(View.VISIBLE);

        btnPage.setText("View Leaderboard");
    }

    public void addPoints()
    {
        Log.d("results", results);
        Log.d("points", Integer.toString(points));
        Log.d("type", type);

        prevPoints = pointManager.addPoints(points, type, userID);

        curPoints = prevPoints + points;

        int totalPoints = accountManager.getAccountWithID(String.valueOf(userID)).getTotalPoints();
        totalPoints += points;
        accountManager.updateAccount(userID,"","","",String.valueOf(totalPoints));

        setTitle("Success");
        setSuccessPage();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPage:
                if(results.matches("HelloWorldSUCCESS10") || results.matches("HelloWorldSUCCESS20"))
                {
                    startActivity(new Intent(ScanResultActivity.this, LeaderboardActivity.class));
                }else {
                    startActivity(new Intent(ScanResultActivity.this, LeaderboardActivity.class));
                }
                break;
            case R.id.btnHomePage:
                startActivity(new Intent(ScanResultActivity.this, BottomNavigationActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if (pointManager != null) {
            pointManager.close();
            pointManager = null;
        }

        if (accountManager != null) {
            accountManager.close();
            accountManager = null;
        }
    }
}