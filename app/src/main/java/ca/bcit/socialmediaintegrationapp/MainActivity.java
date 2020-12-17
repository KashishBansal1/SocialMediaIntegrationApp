package ca.bcit.socialmediaintegrationapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.google.android.gms.common.SignInButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login, fb_btn;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#2a7ee6"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        login = (Button) findViewById(R.id.Login_email);
        fb_btn = (Button) findViewById(R.id.button_facebook);
        signInButton = findViewById(R.id.sign_in_button);

        login.setOnClickListener((v -> {
            // redirect to LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }));

        signInButton.setOnClickListener((v -> {
            // redirect to GoogleActivity
            Intent intent = new Intent(getApplicationContext(), GoogleActivity.class);
            startActivity(intent);
        }));

        fb_btn.setOnClickListener((v -> {
            // redirect to FacebookActivity
            Intent intent = new Intent(getApplicationContext(), FacebookActivity.class);
            startActivity(intent);
        }));
    }
}