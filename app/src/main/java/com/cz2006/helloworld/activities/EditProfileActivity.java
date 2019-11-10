package com.cz2006.helloworld.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.models.User;

/**
 * Represents Edit Profile Activity showing User's points, name and email
 * This class can enable the user to edit their profile.
 * Current password is necessary when editing their profile.
 *
 * @author Rosario Gelli Ann
 *
 */
public class EditProfileActivity extends AppCompatActivity {

    private TextView nameTV, emailTV;
    private EditText nameET, currentPasswordET, newPasswordET;
    private Button btnEditProfile;
    private AccountManager accountManager;
    private SessionManager sessionManager;

    private User currentUser;
    private int userID;

    private String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"; // Minimum eight characters, at least one uppercase letter, one lowercase letter and one number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Set Activity Title
        setTitle("Profile");

        sessionManager = new SessionManager(getApplicationContext());
        accountManager = new AccountManager(getApplicationContext());

        nameTV = findViewById(R.id.namealltimeTV);
        emailTV = findViewById(R.id.emailTV);
        nameET = findViewById(R.id.nameET);
        currentPasswordET = findViewById(R.id.currentPasswordET);
        newPasswordET = findViewById(R.id.newPasswordET);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, email, currentPassword, newPassword;

                name = nameET.getText().toString();
                email = emailTV.getText().toString();
                currentPassword = currentPasswordET.getText().toString();
                newPassword = newPasswordET.getText().toString();

                if(checkUserInput(name, email, currentPassword, newPassword)){
                    updateProfileDetails(email, newPassword, name);
                }
            }
        });

        accountManager.open(); // Open Database Connection

        getProfileDetails();
    }

    public void getProfileDetails(){

        userID = sessionManager.getUserDetails().get("userID");

        // Get user info from Database
        currentUser = accountManager.getAccountWithID(String.valueOf(userID));
        nameTV.setText(currentUser.getUserName());
        emailTV.setText(currentUser.getUserEmail());
        nameET.setText(currentUser.getUserName());
    }

    private boolean checkUserInput(String name, String email, String currentPassword, String newPassword){

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email))
        {
            if(TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword)) // Update name and email
            {
                Toast.makeText(getApplicationContext(), "Please enter your current password", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(!TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(newPassword))
            {
                if(!currentPassword.equals(currentUser.getUserPassword())) // Update password
                {
                    Toast.makeText(getApplicationContext(), "Please make sure to enter correct password", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(!newPassword.matches(passwordPattern))
                {
                    Toast.makeText(getApplicationContext(), "Please enter at least a 8-char password that consist of uppercase and lowercase letter and a number", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(newPassword.equals(currentUser.getUserPassword()) || currentPassword.equals(newPassword))
                {
                    Toast.makeText(getApplicationContext(), "Please make sure that the current password isn't the old password", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            else if(!TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword))
            {
                if(!currentPassword.equals(currentUser.getUserPassword())) // Update password
                {
                    Toast.makeText(getApplicationContext(), "Please make sure to enter correct password", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else if(TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(newPassword))
            {
                Toast.makeText(getApplicationContext(), "Please make sure to enter current password", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please make fill in the name and email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateProfileDetails(String email, String password, String name){
        accountManager.updateAccount(userID, email, password, name, "");
        Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        this.finish();
        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
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
