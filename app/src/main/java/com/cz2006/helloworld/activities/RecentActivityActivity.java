package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.adapters.ActivityAdapter;
import com.cz2006.helloworld.managers.PointManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.models.Points;
import com.cz2006.helloworld.models.User;

import java.util.ArrayList;
import java.util.List;

public class RecentActivityActivity extends AppCompatActivity {

    private ListView activityList;
    private ArrayList<Points> userPoints = new ArrayList<Points>();

    private SessionManager sessionManager;
    private PointManager pointManager;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_activity);

        // Set Activity Title
        setTitle("Recent Activity");

        sessionManager = new SessionManager(getApplicationContext());
        userID = sessionManager.getUserDetails().get("userID");

        pointManager = new PointManager(getApplicationContext());
        pointManager.open();

        getAllUserPoints();
        setActivityList();

    }

    public void getAllUserPoints(){

        userPoints = pointManager.getAllPointsWithID(String.valueOf(userID));

    }

    public void setActivityList(){
        activityList = (ListView)findViewById(R.id.activityListView);
        ActivityAdapter adapter;

        if(userPoints.size() == 0)
        {
            userPoints.add(new Points());
        }
        adapter = new ActivityAdapter(this,userPoints);

        activityList.setAdapter(adapter);
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
        if (pointManager != null) {
            pointManager.close();
            pointManager = null;
        }
    }
}
