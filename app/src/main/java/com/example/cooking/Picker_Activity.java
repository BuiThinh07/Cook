package com.example.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class Picker_Activity extends AppCompatActivity {
    TimePicker timepicker;
    int hour, minutes;
    Button GetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        timepicker = (TimePicker)findViewById(R.id.timePicker1);

        GetTime = (Button)findViewById(R.id.button1);

        timepicker.setIs24HourView(true);

        GetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                hour = timepicker.getCurrentHour();
                minutes = timepicker.getCurrentMinute();

                int timesecond = hour* 60 *60 *1000 + minutes *60 *1000;

                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences("MyPrefs2",MODE_PRIVATE)
                        .edit();
                editor.putString("time1", String.valueOf(timesecond));
                editor.apply();

                Intent intent = new Intent(Picker_Activity.this, ProfileUser_Activity.class);
                startActivity(intent);

            }
        });
    }
}