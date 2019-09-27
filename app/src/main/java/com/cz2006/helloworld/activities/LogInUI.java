package com.cz2006.helloworld.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.helloworld.R;

public class LogInUI extends AppCompatActivity {

    private static final String TAG = "LogInUI";
    EditText inputEmail;
    EditText inputPassword;
    Button btnLogIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_ui);

        getSupportActionBar().hide();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDO: Log In Method

                //TESTING
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(LogInUI.this, MainUI.class),
                            ActivityOptions.makeSceneTransitionAnimation(LogInUI.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(LogInUI.this, MainUI.class));
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(LogInUI.this, SignUpUI.class),
                            ActivityOptions.makeSceneTransitionAnimation(LogInUI.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(LogInUI.this, SignUpUI.class));
                }

            }
        });

    }

}
