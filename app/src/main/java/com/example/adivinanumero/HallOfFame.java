package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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
    private static ArrayList<Result> arrayOfResults = new ArrayList<Result>();
    //private  Bitmap resultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);


        // Create the adapter to convert the array to views
        ResultAdapter adapter = new ResultAdapter(this, arrayOfResults);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.theList);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        Bitmap resultBitmap = (Bitmap) intent.getParcelableExtra(MainActivity.EXTRA_BITMAP);
        String intentMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] intentMessageSplit = intentMessage.split(",");
        // Add item to adapter
         Result newResult = new Result("Nombre: " + intentMessageSplit[0], "Intentos:  " + intentMessageSplit[1], "Segundos:    " +  intentMessageSplit[2], resultBitmap);// Integer.parseInt(intentMessageSplit[1]), Integer.parseInt(intentMessageSplit[2]));//, resultBitmap);
         arrayOfResults.add(newResult);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnMain();
            }
        });

    }

    public void returnMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}