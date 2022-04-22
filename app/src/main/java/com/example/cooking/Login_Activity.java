package com.example.cooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login_Activity extends AppCompatActivity {

    Button LoginClick ;
    Button switchSignInPage ;

    public static final String TAG = "GoogleSignIn";
    public static final int RC_SIGN_IN = 321;
    private SignInButton btnSignInWithGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginClick = (Button) findViewById(R.id.loginBtn);
        LoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

        switchSignInPage = (Button) findViewById(R.id.SwitchSignInPage);
        switchSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,
                        SignUp_Activity.class);
                startActivity(intent);
            }
        });

        btnSignInWithGoogle = findViewById(R.id.btnGoogleSignIn);
        mAuth = FirebaseAuth.getInstance();
        requestGoogleSignIn();

        btnSignInWithGoogle.setOnClickListener(view -> {
            signIn();
        });

    }

    private void requestGoogleSignIn(){
        // Configure sign-in to request the user’s basic profile like name and email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.i(TAG, "onActivityResult: Success");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating user with firebase using received token id
                firebaseAuthWithGoogle(account.getIdToken());

                //assigning user information to variables
                String userName = account.getDisplayName();
                String userEmail = account.getEmail();
                String userPhoto = account.getPhotoUrl().toString();
                userPhoto = userPhoto+"?type=large";

                //create sharedPreference to store user data when user signs in successfully
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences("MyPrefs",MODE_PRIVATE)
                        .edit();
                editor.putString("username", userName);
                editor.putString("useremail", userEmail);
                editor.putString("userPhoto", userPhoto);
                editor.apply();

                Log.i(TAG, "onActivityResult: Success");

            } catch (ApiException e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        //getting user credentials with the help of AuthCredential method and also passing user Token Id.
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        //trying to sign in user using signInWithCredential and passing above credentials of user.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");

                            // Sign in success, navigate user to Profile Activity
                            Intent intent = new Intent(Login_Activity.this, Profile_Activity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_Activity.this, "User authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}