package com.cz2006.helloworld.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.helloworld.R;

public class SignUpUI extends AppCompatActivity {

    Button btnLogIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_ui);

        getSupportActionBar().hide();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDO: Sign Up Method
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(SignUpUI.this, LogInUI.class),
                            ActivityOptions.makeSceneTransitionAnimation(SignUpUI.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(SignUpUI.this, LogInUI.class));
                }
            }
        });
    }
}
