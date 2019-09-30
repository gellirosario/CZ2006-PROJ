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
 * Represents Sign Up Activity
 * which User can sign up to use the application
 *
 * @author Rosario Gelli Ann
 *
 */
public class SignUpActivity extends AppCompatActivity {

    Button btnLogIn;
    Button btnSignUp;
    TextInputEditText inputName;
    TextInputEditText inputEmail;
    TextInputEditText inputPassword;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        accountManager = new AccountManager(getApplicationContext());
        accountManager.open();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputName.getText().toString().isEmpty() || inputEmail.getText().toString().isEmpty() || inputPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill in the form.", Toast.LENGTH_SHORT).show();
                }
                else if(accountManager.checkExistingEmail(inputEmail.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Email already exist.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    accountManager.createAccount(inputName.getText().toString(),inputEmail.getText().toString(),inputPassword.getText().toString());
                    Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
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
