package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HallOfFame extends AppCompatActivity {

    private String[] intentMessageSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);
        final TextView fame = findViewById(R.id.fama);
        Intent intent = getIntent();
        String intentMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] intentMessageSplit = intentMessage.split(",");
        fame.append("\n" + intentMessageSplit[0] + "    " + intentMessageSplit[1] + "       " + intentMessageSplit[2]);
    }
}