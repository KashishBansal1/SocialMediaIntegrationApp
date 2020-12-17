package ca.bcit.socialmediaintegrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Need this to check the state once the app starts up
    private FirebaseAuth mAuth;

    EditText email, password;
    Button login;
    TextView register, forgotpwd;
    boolean isEmailValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forgotpwd = (TextView) findViewById(R.id.forgotPassword);

        login.setOnClickListener(v -> {
            if (!SetValidation()) {
                return;
            }
            signIn(email.getText().toString(), password.getText().toString());
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        forgotpwd.setOnClickListener((v -> {
            // redirect to ResetPasswordActivity
            Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(intent);
        }));

        register.setOnClickListener(v -> {
            // redirect to RegisterActivity
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Using the onStart to check if the user has already login in or not.
    // If the user is already logged in, then we want to send them to a different activity
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // The activity that we're sending our user to currently is HomeScreen
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    final String TAG = "EmailPassword";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        Intent profileIntent = new Intent(getApplicationContext(), Profile.class);
        profileIntent.putExtra("email", user.getEmail());
        Log.v("DATA", user.getUid());
        startActivity(profileIntent);
    }


    public boolean SetValidation() {

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

        // Check for a valid password.
        if (password.getText().toString().isEmpty() || password.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password must be 6 characters or longer", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isPasswordValid = true;
        }

        if (isEmailValid && isPasswordValid) {
            return true;
        }

        return false;
    }

}