package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.FeedbackManager;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Represents Feedback Activity
 * where User give feedback
 *
 * @author Rosario Gelli Ann
 * @author Lexx Audrey
 *
 */
public class FeedbackActivity extends AppCompatActivity {

    private FeedbackManager feedbackManager;
    private Dialog myDialog;

    public String type;
    public String desc = null;
    public float rating;

    private Spinner typeSpinner;
    private EditText descTxt;
    private RatingBar ratingTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackManager = new FeedbackManager(getApplicationContext());
        feedbackManager.open();

        // Set Activity Title
        setTitle("Feedback");

        myDialog = new Dialog(this);
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

    //handle click event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //@Override
    public void sendFeedback(View v)
    {
        typeSpinner = (Spinner) findViewById(R.id.feedback_spinner);
        type = typeSpinner.getSelectedItem().toString();

        descTxt = findViewById(R.id.descriptionText);
        desc = descTxt.getText().toString();

        ratingTxt = findViewById(R.id.rating);
        rating = ratingTxt.getRating();

        Log.d("type", type);
        Log.d("desc", desc);
        Log.d("rating", Float.toString(rating));

        if(TextUtils.isEmpty(type) || TextUtils.isEmpty(desc)) {
            Toast.makeText(getApplicationContext(), "Please fill in all the details", Toast.LENGTH_SHORT).show();
        }else{
            feedbackManager.sendFeedback(type, desc, Float.toString(rating));
            showPopUp();
            Toast.makeText(getApplicationContext(), "Feedback is successfully sent!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showPopUp(){
        TextView popUpTV;
        Button btnConfirm, btnClose;
        myDialog.setContentView(R.layout.custom_popup);

        popUpTV =(TextView) myDialog.findViewById(R.id.popUpTV);
        btnConfirm = (Button) myDialog.findViewById(R.id.btnConfirm);
        btnClose = (Button) myDialog.findViewById(R.id.btnClose);

        popUpTV.setText("Do you want to send feedback through email?");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
                myDialog.dismiss();
                clearDetails();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                finish();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void sendEmail()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","gellirosario@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, type);
        emailIntent.putExtra(Intent.EXTRA_TEXT, desc + "\n Rating: " + Float.toString(rating) + ".");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void clearDetails(){
        descTxt.setText("");
        ratingTxt.setRating(0);
    }
}
