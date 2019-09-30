package com.cz2006.helloworld.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Represents Log In Activity
 * where User can log in to the application
 *
 * @author Rosario Gelli Ann
 *
 */
public class LogInActivity extends AppCompatActivity {

    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    Button btnLogIn;
    Button btnSignUp;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().hide();

        accountManager = new AccountManager(getApplicationContext());
        accountManager.open();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputEmail = findViewById(R.id.inputEmail);
                inputPassword = findViewById(R.id.inputPassword);

                if(inputEmail.getText().toString().isEmpty() || inputPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter log in details.", Toast.LENGTH_SHORT).show();
                }
                else if(!accountManager.checkExistingEmail(inputEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Email does not exist.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(accountManager.authenticate(inputEmail.getText().toString(),inputPassword.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                        // Check if we're running on Android 5.0 or higher
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // Apply activity transition
                            startActivity(new Intent(LogInActivity.this, MainActivity.class),
                                    ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());
                        } else {
                            // Swap without transition
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(LogInActivity.this, SignUpActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if(accountManager!=null)
        {
            accountManager.close();
            accountManager = null;
        }
    }
}
