package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.PointManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.managers.UsageManager;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents Add/Edit Usage Activity
 * where User can add or edit usage
 *
 * @author Harry
 *
 */
public class AddUsageActivity extends AppCompatActivity
implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    public static int Year = 0;
    public static int Month = 0;
    public static float Amount = 0;
    public static float Price = 0;
    public static char Type = 0;
    public static int userID = 0;
    public static String printType = "";
    public static List<Pair<Integer, Integer>> calList = new ArrayList<Pair<Integer, Integer>>();

    private UsageManager usageManager;
    private SessionManager addUsageSessionManager;
    private PointManager addUsagePointManager;
    private AccountManager accountManager;
    private Calendar date = Calendar.getInstance();


    // Layout Variables
    private EditText inputAmount;
    private EditText inputPrice;
    private RadioButton rbElectricity;
    private RadioButton rbGas;
    private RadioButton rbWater;
    private Button btnSubmit;
    private Dialog editUsageDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);
        Type = 0;

        usageManager = new UsageManager(getApplicationContext());
        usageManager.open();

        addUsageSessionManager = new SessionManager(getApplicationContext());
        userID = addUsageSessionManager.getUserDetails().get("userID");

        addUsagePointManager = new PointManager(getApplicationContext());
        addUsagePointManager.open();

        accountManager = new AccountManager(getApplicationContext());
        accountManager.open();

        editUsageDialog = new Dialog(AddUsageActivity.this);

        setTitle("Add Usage");

        //Part I Spinners
        //ADD AVAILABLE MONTHS AND YEARS TO THE SELECTION SPINNER
        List<String> YearList = new ArrayList<String>();
        List<String> MonthList = new ArrayList<String>();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
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
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        inputAmount = (EditText) findViewById(R.id.UsageAmountInp);
        inputPrice = (EditText) findViewById(R.id.UsagePriceInp);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

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
            case R.id.btnSubmit:

                Amount = Float.parseFloat(inputAmount.getText().toString());
                Price = Float.parseFloat(inputPrice.getText().toString());

                // See if null
                if(Amount == 0 || inputAmount.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter an Amount", Toast.LENGTH_LONG).show();
                    return;
                } else if(Price == 0 || inputPrice.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter a Price", Toast.LENGTH_LONG).show();
                    return;
                }

                //Verify Requirements
                boolean dateVal = false;
                int i;
                for (i = 0; i < 3; i++)
                    if ((Year == calList.get(i).first) && (Month == calList.get(i).second)) {
                        dateVal = true;
                        break;
                    }
                if (dateVal != true) {
                    Toast.makeText(getApplicationContext(), "Date must be within 2 months before Current Date!", Toast.LENGTH_SHORT).show();
                }
                else if (Type == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please select a Usage type", Toast.LENGTH_SHORT).show();
                }
                // CHECK REQUIREMENTS DONE , NOW EDIT USAGE (CHECK EXIST RECORD)
                else if (usageManager.findUsageRecord(userID, Year, Month, Type).getCount() > 0)
                {
                    if (Type == 'E') { printType = "Electricity";}
                    else if (Type == 'W') {printType = "Water";}
                    else if (Type == 'G') {printType = "Gas";}
                    //Toast.makeText(getApplicationContext(), "Count : " + String.valueOf(usageManager.findUsageRecord(userID, Year, Month, Type).getCount()), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Record already exist for the month!", Toast.LENGTH_SHORT).show();
                    editUsageShowPopUp();
                }
                else {
                    if (Type == 'E') { printType = "Electricity";}
                    else if (Type == 'W') {printType = "Water";}
                    else if (Type == 'G') {printType = "Gas";}
                    usageManager.addUsage(Year, Month, Type, Amount, Price);

                        addUsagePointManager.addPoints(10, String.valueOf(Year) + String.valueOf(Month) + String.valueOf(Type) + " Add Latest Usage", userID);

                        int totalPoints = accountManager.getAccountWithID(String.valueOf(userID)).getTotalPoints();
                        totalPoints += 10;
                        accountManager.updateAccount(userID,"","","",String.valueOf(totalPoints));
                        Toast.makeText(getApplicationContext(), "10 Points Rewarded! Add New Usage!", Toast.LENGTH_LONG).show();

                    finish();
                }
            default:
                break;
        }
    }

    public void editUsageShowPopUp(){
        TextView popUpTV;
        Button btnConfirm, btnClose;
        editUsageDialog.setContentView(R.layout.custom_popup);

        popUpTV =(TextView) editUsageDialog.findViewById(R.id.popUpTV);
        btnConfirm = (Button) editUsageDialog.findViewById(R.id.btnConfirm);
        btnClose = (Button) editUsageDialog.findViewById(R.id.btnClose);
        popUpTV.setTextSize(18);

        popUpTV.setText("Record found in the month! Confirm to edit usage record of " + String.valueOf(Year) + "-" + String.valueOf(Month) + " " + printType + "?");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsageDialog.dismiss();
                usageManager.updateUsage(userID, Year, Month, Amount, Price, Type);
                Toast.makeText(getApplicationContext(), "Usage has been updated!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), String.valueOf(Year) + "-" + String.valueOf(Month) + " " + printType + " Edited : Amount = " + String.valueOf(Amount) + " , Price = " + String.valueOf(Price), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsageDialog.dismiss();
            }
        });

        editUsageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editUsageDialog.show();
    }

    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if (usageManager != null) {
            usageManager.close();
            usageManager = null;
        }
        if (addUsagePointManager != null){
            addUsagePointManager.close();
            addUsagePointManager = null;
        }
    }

}