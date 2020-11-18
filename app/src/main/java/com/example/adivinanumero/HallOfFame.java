package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HallOfFame extends AppCompatActivity {

    Button btnBack;

    private String[] intentMessageSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);
        ListView list = (ListView) findViewById(R.id.theList);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnMain();
            }
        });


        ArrayList<String> names = new ArrayList<>();
        Intent intent = getIntent();
        String intentMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] intentMessageSplit = intentMessage.split(",");

        names.add("\n               " + intentMessageSplit[0] + "          " + intentMessageSplit[1] + "           " + intentMessageSplit[2] +"     segundos");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
    }

    public void returnMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}