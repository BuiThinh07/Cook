package com.example.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileUser_Activity extends AppCompatActivity {

    TextView counttimene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        counttimene = findViewById(R.id.counttimene);

        SharedPreferences preferences = this.getSharedPreferences("MyPrefs1", MODE_PRIVATE);
        String countTime = preferences.getString("time","");
        counttimene.setText(countTime);
    }
}