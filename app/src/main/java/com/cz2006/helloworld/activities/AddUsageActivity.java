package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.UsageManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddUsageActivity extends AppCompatActivity
implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static int Year = 0;
    public static int Month = 0;
    public static float Amount = 0;
    public static float Price = 0;
    public static char Type = 0;
    UsageManager AddUsageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);

        setTitle("Add Usage");

        //Part I Spinners
            //ADD AVAILABLE MONTHS AND YEARS TO THE SELECTION SPINNER
        List<String> YearList = new ArrayList<String>();
        List<String> MonthList = new ArrayList<String>();
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        YearList.add(String.valueOf(year));
        MonthList.add(String.valueOf(month));
        for (int i = 0 ; i < 2 ; i++)
        {
            month = month - 1;
            if (month <= 0){
                year = year - 1;
                month = month + 12;
                YearList.add(String.valueOf(year));
            }
            MonthList.add(String.valueOf(month));
        }

        //CHANGE LAYOUT REMEMBER WHEN CREATING SPINNER ITEM!!!
        Spinner YearSpinner = (Spinner) findViewById(R.id.UsageYearInp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, YearList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(adapter);
        YearSpinner.setOnItemSelectedListener(this);

        Spinner MonthSpinner = (Spinner) findViewById(R.id.UsageMonthInp);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, MonthList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(adapter2);
        MonthSpinner.setOnItemSelectedListener(this);

        //END OF PART I

        //PART II Radio Buttons
        RadioButton a = findViewById(R.id.ElecCheckButton);
        a.setOnClickListener(this);
        RadioButton b = findViewById(R.id.GasCheckButton);
        b.setOnClickListener(this);
        RadioButton c = findViewById(R.id.WaterCheckButton);
        c.setOnClickListener(this);

        //Part III To Submit!
        Button GO = findViewById(R.id.SubmitBtn);
        GO.setOnClickListener(this);

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

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        Spinner spin = (Spinner) parent;
        Spinner spin2 = (Spinner) parent;
        if (spin.getId() == R.id.UsageYearInp) {
            Year = Integer.parseInt(spin.getSelectedItem().toString());
        }
        if (spin2.getId() == R.id.UsageMonthInp) {
            Month = Integer.parseInt(spin2.getSelectedItem().toString());
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void clearRadioChecked() {
        RadioButton a = findViewById(R.id.ElecCheckButton);
        RadioButton b = findViewById(R.id.GasCheckButton);
        RadioButton c = findViewById(R.id.WaterCheckButton);
        a.setChecked(false);
        b.setChecked(false);
        c.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        RadioButton a = findViewById(R.id.ElecCheckButton);
        RadioButton b = findViewById(R.id.GasCheckButton);
        RadioButton c = findViewById(R.id.WaterCheckButton);
        Button Submit = (Button) findViewById(R.id.SubmitBtn);
        switch (v.getId()) {
            case R.id.ElecCheckButton:
                clearRadioChecked();
                a.setChecked(true);
                Type = 'E';
                break;
            case R.id.GasCheckButton:
                clearRadioChecked();
                b.setChecked(true);
                Type = 'G';
                break;
            case R.id.WaterCheckButton:
                clearRadioChecked();
                c.setChecked(true);
                Type = 'W';
                break;
            case R.id.SubmitBtn:
                EditText Amountinput;
                EditText Priceinput;
                Amountinput = (EditText) findViewById(R.id.UsageAmountInp);
                Priceinput = (EditText) findViewById(R.id.UsagePriceInp);
                Amount = Float.parseFloat(Amountinput.getText().toString());
                Price = Float.parseFloat(Priceinput.getText().toString());
                TextView testing = findViewById(R.id.Testing);
                testing.setText(String.valueOf(Year) + String.valueOf(Month) + Type + "\n Amount = " + Amount + "\n Price = " + Price);
            //DO NOT RUN THIS COMMAND UNTIL DATABASE FIXED //AddUsageManager.addUsage(Year,Month,Type,Amount,Price);
            default:
                break;
        }
    }
}