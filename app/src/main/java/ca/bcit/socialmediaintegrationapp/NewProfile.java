package ca.bcit.socialmediaintegrationapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.google.firebase.auth.FirebaseAuth;

public class NewProfile extends AppCompatActivity {

    TextView logout, name, email, phone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprofile);

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

        logout = (TextView) findViewById(R.id.logout);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        mAuth = FirebaseAuth.getInstance();

        name.setText("Kashish Bansal");
        email.setText("kashish.989392@gmail.com");
        phone.setText("9576654885");

        /** logging out a user */
        logout.setOnClickListener(v -> logoutConfirmation());
    }

    // logout dialog
    private void logoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewProfile.this);
        builder.setTitle("Confirmation PopUp!").
                setMessage("Going back will return you to the login page.\n\nAre you sure that you want to logout?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    mAuth.signOut();
                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(i);
                });
        builder.setNegativeButton("No",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    // when back key is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logoutConfirmation();
        }
        return true;
    }
}