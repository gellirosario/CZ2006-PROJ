package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;

/**
 * Represents FAQ Details 2
 * where User can view FAQ details
 *
 * @author Rosario Gelli Ann
 *
 */
public class FaqDetail2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail2);

        // Set Activity Title
        setTitle("FAQ");

        RelativeLayout rlFaq1 = (RelativeLayout) findViewById (R.id.rlFaq1);

        rlFaq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaqDetail2Activity.this
                        , FaqDetail1Activity.class));
                finish();
            }
        });

        RelativeLayout rlFaq2 = (RelativeLayout) findViewById (R.id.rlFaq2);

        rlFaq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaqDetail2Activity.this
                        , FaqDetail2Activity.class));
                finish();
            }
        });

        RelativeLayout rlFaq3 = (RelativeLayout) findViewById (R.id.rlFaq3);

        rlFaq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaqDetail2Activity.this
                        , FaqDetail3Activity.class));
                finish();
            }
        });

        TextView tvGoToFeedback = findViewById(R.id.tvGoToFeedback);

        tvGoToFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaqDetail2Activity.this
                        , FeedbackActivity.class));
                finish();
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
