package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
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
import android.widget.Toast;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.fragments.TrackFragment;
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
    public static List<Pair<Integer, Integer>> calList = new ArrayList<Pair<Integer, Integer>>();

    private UsageManager usageManager;

    // Variables
    private EditText inputAmount;
    private EditText inputPrice;
    private RadioButton rbElectricity;
    private RadioButton rbGas;
    private RadioButton rbWater;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);

        usageManager = new UsageManager(getApplicationContext());
        usageManager.open();

        setTitle("Add Usage");

        //Part I Spinners
        //ADD AVAILABLE MONTHS AND YEARS TO THE SELECTION SPINNER
        List<String> YearList = new ArrayList<String>();
        List<String> MonthList = new ArrayList<String>();
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1 - 9;
        YearList.add(String.valueOf(year));
        MonthList.add(String.valueOf(month));
        calList.add(new Pair<Integer, Integer>(year, month));
        for (int i = 0; i < 2; i++) {
            month = month - 1;
            if (month <= 0) {
                year = year - 1;
                month = month + 12;
                YearList.add(String.valueOf(year));
            }
            MonthList.add(String.valueOf(month));
            calList.add(new Pair<Integer, Integer>(year, month));
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
        rbElectricity =  findViewById(R.id.ElecCheckButton);
        rbElectricity.setOnClickListener(this);
        rbGas = findViewById(R.id.GasCheckButton);
        rbGas.setOnClickListener(this);
        rbWater = findViewById(R.id.WaterCheckButton);
        rbWater.setOnClickListener(this);

        //Part III To Submit!
        btnSubmit = findViewById(R.id.SubmitBtn);
        btnSubmit.setOnClickListener(this);

        inputAmount = (EditText) findViewById(R.id.UsageAmountInp);
        inputPrice = (EditText) findViewById(R.id.UsagePriceInp);
        btnSubmit = (Button) findViewById(R.id.SubmitBtn);

        inputAmount.setText("0");
        inputPrice.setText("0");
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
        rbElectricity.setChecked(false);
        rbGas.setChecked(false);
        rbWater.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ElecCheckButton:
                clearRadioChecked();
                rbElectricity.setChecked(true);
                Type = 'E';
                break;
            case R.id.GasCheckButton:
                clearRadioChecked();
                rbGas.setChecked(true);
                Type = 'G';
                break;
            case R.id.WaterCheckButton:
                clearRadioChecked();
                rbWater.setChecked(true);
                Type = 'W';
                break;
            case R.id.SubmitBtn:

                Amount = Float.parseFloat(inputAmount.getText().toString());
                Price = Float.parseFloat(inputPrice.getText().toString());
                boolean dateVal = false;
                int i;
                for (i = 0; i < 3; i++)
                    if ((Year == calList.get(i).first) && (Month == calList.get(i).second)) {
                        dateVal = true;
                        break;
                    }
                if (dateVal == true) {
                    usageManager.addUsage(Year, Month, Type, Amount, Price);
                    Toast.makeText(getApplicationContext(), "Added Usage!", Toast.LENGTH_SHORT).show();
                    Intent go = new Intent(AddUsageActivity.this, TrackFragment.class);
                    startActivity(go);
                } else {
                    Toast.makeText(getApplicationContext(), "Date must be within 2 months before Current Date!", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if (usageManager != null) {
            usageManager.close();
            usageManager = null;
        }
    }

}