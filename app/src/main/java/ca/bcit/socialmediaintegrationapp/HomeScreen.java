package ca.bcit.socialmediaintegrationapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidcoding.abhi.simple_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends Activity {

    Button UserProfile;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        UserProfile = findViewById(R.id.Profilebtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        UserProfile.setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        });

    }

    public void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(HomeScreen.this, Profile.class);
        intent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(intent);
    }
}