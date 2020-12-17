package ca.bcit.socialmediaintegrationapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.androidcoding.abhi.simple_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends Activity {
    TextView logout, name, email, phone;
    private String Email, fullname, phoneno, userID;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, userRef;
    private static final String USERS = "Users";
    private final String TAG = this.getClass().getName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //receive data from login screen
        final Intent intent = getIntent();
        Email = intent.getStringExtra("email");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        logout = (TextView) findViewById(R.id.logout);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        mAuth = FirebaseAuth.getInstance();

        /** logging out a user */
        logout.setOnClickListener(v -> logoutConfirmation());
    }

    public void onStart() {
        super.onStart();
        getValue();
    }

    //gets the user profile from database
    public void getValue() {
        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (Objects.equals(keyId.child("email").getValue(), Email)) {

                        fullname = keyId.child("name").getValue(String.class);
                        phoneno = keyId.child("phoneNumber").getValue(String.class);
                        userID = keyId.child("userID").getValue(String.class);
                        break;
                    }
                }
                name.setText(fullname);
                email.setText(Email);
                phone.setText(phoneno);
                onResume();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // logout dialog
    private void logoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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