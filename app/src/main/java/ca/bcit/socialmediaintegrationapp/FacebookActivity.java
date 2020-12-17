package ca.bcit.socialmediaintegrationapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcoding.abhi.simple_login.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.util.Arrays;

public class FacebookActivity extends AppCompatActivity {
    private Button mButtonFacebook;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fblogin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        mButtonFacebook = findViewById(R.id.button_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();

        mButtonFacebook.setOnClickListener(v -> loginManager.logInWithReadPermissions(
                FacebookActivity.this,
                Arrays.asList(
                        "email",
                        "public_profile",
                        "user_birthday")));
    }

    public void facebookLogin() {
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), (object, response) -> {
                            if (object != null) {
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String fbUserID = object.getString("id");

                                    disconnectFromFacebook();

                                    Intent intent = new Intent(getApplicationContext(), NewProfile.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                } catch (JSONException | NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString(
                        "fields",
                        "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v("LoginScreen", "---onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                // here write code when get error
                Log.v("LoginScreen", "----onError: " + error.getMessage());
            }
        });
    }

    //disconnects user from facebook
    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, graphResponse -> LoginManager.getInstance().logOut())
                .executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
