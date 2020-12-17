package ca.bcit.socialmediaintegrationapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // Need this to check the state once the app starts up
    private FirebaseAuth mAuth;

    EditText name, email, phone, password;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    DatabaseReference databaseDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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


        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // When the user clicks the register button
        register.setOnClickListener(v -> registerUser());

        databaseDetails = FirebaseDatabase.getInstance().getReference("Users");

        login.setOnClickListener(v -> {
            // redirect to LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });


    }

    /*
    This is the method that changes the activity based on the result of whether a user successfully
    creates a account or logs in
     */

    // registerUser will take the validated information and use Firebase to create a new account
    private void registerUser() {
        // If the entered information is invalid, don't register the user and just return
        if (!validateRegisterInformation()) {
            return;
        }

        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        // Once a user sign-up is verified, we want to sign them up
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    final String TAG = "EmailPassword";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // updateUI is a helper function that deals with successful or
                            // unsuccessful log ins
                            // Temporary, we will handle the register attempt in registerUser
                            Toast.makeText(getApplicationContext(), "Successfully Registered",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public boolean validateRegisterInformation() {
        // We want to return false if an error has occurred.
        // Toast a message to the user saying what they've done incorrectly

        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You must enter a name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isNameValid = true;
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            // We can put both empty string and invalid email as one if statement
            Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isEmailValid = true;
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You must enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isPhoneValid = true;
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty() || password.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password must be 6 characters or longer", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isPasswordValid = true;
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            return true;
        }

        return false;
    }

    private void addUser() {
        // Takes the information from the form and adds it to the database
        // The user data is already validated from the validateRegisterInformation method
        String Name = name.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String id = databaseDetails.push().getKey();

        RegistrationDetails Details = new RegistrationDetails(id, Name, Email, Phone);

        databaseDetails.child(id).setValue(Details);

    }

    public void updateUI(FirebaseUser currentUser) {
        addUser();
        Intent intent = new Intent(RegisterActivity.this, HomeScreen.class);
        startActivity(intent);

    }
}