package com.example.cooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Activity extends AppCompatActivity {

    private EditText userName, password;
    Button btnRegister;
    Button loginPage;

    FirebaseAuth mAuth;
    public static final String TAG = "SignUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName =findViewById(R.id.singupEmail);
        password=findViewById(R.id.singupPass);
        btnRegister = findViewById(R.id.SignUpBtn);
        loginPage = findViewById(R.id.loginPage);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            registerNewUser();
        });

        loginPage.setOnClickListener(view ->{
            startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
        });

    }

    private void registerNewUser()
    {


        // Take the value of two edit texts in Strings
        String email, passwords;
        email = userName.getText().toString();
        passwords = password.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, passwords)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();


                            FirebaseUser account = mAuth.getCurrentUser();
                            String userName = account.getDisplayName();
                            String userEmail = account.getEmail();
                            String userPhoto = "https://th.bing.com/th/id/OIP.m5KS5IMS3UlUJPRzqQ2zlwHaFj?pid=ImgDet&rs=1";

                            SharedPreferences.Editor editor = getApplicationContext()
                                    .getSharedPreferences("MyPrefs",MODE_PRIVATE)
                                    .edit();
                            editor.putString("username", userName);
                            editor.putString("userEmail", userEmail);
                            editor.putString("userPhoto", userPhoto);
                            editor.apply();

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(SignUp_Activity.this,
                                    Profile_Activity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });
    }
}