package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HallOfFame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);
        final TextView fame = findViewById(R.id.fama);
        Intent intent = getIntent();
        fame.append("\n" + intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
    }
}