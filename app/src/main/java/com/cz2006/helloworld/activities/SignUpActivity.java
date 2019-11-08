package com.cz2006.helloworld.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Represents Sign Up Activity
 * which User can sign up to use the application
 *
 * @author Rosario Gelli Ann
 *
 */
public class SignUpActivity extends AppCompatActivity {

    private Button btnLogIn;
    private Button btnSignUp;
    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;

    private AccountManager accountManager;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"; // Minimum eight characters, at least one uppercase letter, one lowercase letter and one number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        init();

        accountManager.open(); // Open Database connection

        // Sign Up Button On Click Event
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password, name;
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                name = inputName.getText().toString();

                register(email,password,name);
            }
        });

        // Log In Button On Click Event
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

    public void register(String email, String password, String name){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) )
        {
            Toast.makeText(getApplicationContext(), "Please fill in all details", Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(), "Please enter a correct email address", Toast.LENGTH_SHORT).show();
        }
        else if(!password.matches(passwordPattern))
        {
            Toast.makeText(getApplicationContext(), "Please enter at least a 8-char password that consist of uppercase and lowercase letter and a number", Toast.LENGTH_SHORT).show();
        }
        else if(accountManager.checkExistingEmail(email))
        {
            Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
        }
        else
        {
            accountManager.createAccount(name,email,password);
            Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();

            // Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                // Apply activity transition
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

                ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this).toBundle();
            } else {
                // Swap without transition
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        }
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

    public void init(){
        // Initialize variables
        accountManager = new AccountManager(getApplicationContext());

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
    }
}
