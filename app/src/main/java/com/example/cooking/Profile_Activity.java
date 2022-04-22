package com.example.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_Activity extends AppCompatActivity {

    public static final String TAG = "GoogleSignIn";
    TextView tvUserName;
    ImageView userImageView;
    Button bt_continue;
    Button bt_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUserName = findViewById(R.id.tv_name);
        userImageView = findViewById(R.id.iv_image);
        bt_continue = findViewById(R.id.bt_continue);
        bt_logout = findViewById(R.id.bt_logout);

        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userName = preferences.getString("username","");
        String userImageUrl = preferences.getString("userPhoto","");

        tvUserName.setText(userName);
        Glide.with(this).load(userImageUrl).into(userImageView);

        bt_continue.setOnClickListener(view -> {
            Intent intent = new Intent(Profile_Activity.this,
                    MainActivity.class);
            startActivity(intent);
        });

        bt_logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            goToMainActivity();
        });

    }

    private void goToMainActivity() {
        startActivity(new Intent(Profile_Activity.this, Login_Activity.class));
    }
}