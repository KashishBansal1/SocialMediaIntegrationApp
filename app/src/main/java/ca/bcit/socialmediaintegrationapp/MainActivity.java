package ca.bcit.socialmediaintegrationapp;

import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.google.android.gms.common.SignInButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.Login_email);
        signInButton = findViewById(R.id.sign_in_button);

        login.setOnClickListener((v -> {
            // redirect to LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }));

        signInButton.setOnClickListener((v -> {
            // redirect to GoogleSigninActivity
            Intent intent = new Intent(getApplicationContext(), GoogleSigninActivity.class);
            startActivity(intent);
        }));
    }
}